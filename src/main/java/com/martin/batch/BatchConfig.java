package com.martin.batch;

import com.martin.domain.JpaToMongoTransfer;
import com.martin.domain.jpa.JpaAuthor;
import com.martin.domain.mongo.MongoAuthor;
import com.martin.repository.jpa.JpaAuthorRepository;
import com.martin.repository.mongo.MongoAuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

@EnableBatchProcessing
@Configuration
@Slf4j
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jpaToMongoJob(Step jpaToMongoStep, Step jpaRemoveStep) {
        return jobBuilderFactory.get("jpaToMongoJob")
                .incrementer(new RunIdIncrementer())
                .flow(jpaToMongoStep)
                .next(jpaRemoveStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.info("Начало job");
                    }
                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step jpaToMongoStep( RepositoryItemReader<JpaAuthor> jpaAuthorItemReader,
                                ItemProcessor jpaToMongoAuthorItemProcessor,
                                RepositoryItemWriter mongoAuthorItemWriter) {
        return stepBuilderFactory.get("jpaToMongoStep")
                .chunk(5)
                .reader(jpaAuthorItemReader)
                .processor(jpaToMongoAuthorItemProcessor)
                .writer(mongoAuthorItemWriter)
                .build();
    }

    @Bean
    public RepositoryItemReader<JpaAuthor> jpaAuthorItemReader(JpaAuthorRepository jpaAuthorRepository) {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);

        return new RepositoryItemReaderBuilder<JpaAuthor>()
                .repository(jpaAuthorRepository)
                .sorts(sorts )
                .methodName("findAll")
                .saveState(false)
                .build();
    }

    @Bean
    public ItemProcessor jpaToMongoAuthorItemProcessor() {
        return new ItemProcessor<JpaAuthor, MongoAuthor>() {
            @Override
            public MongoAuthor process(JpaAuthor item) throws Exception {
                return JpaToMongoTransfer.JpaToMongoAuthor(item);
            }

        };
    }

    @Bean
    public RepositoryItemWriter<MongoAuthor> mongoAuthorItemWriter(MongoAuthorRepository mongoAuthorRepository) {
        return new RepositoryItemWriterBuilder<MongoAuthor>()
                .repository(mongoAuthorRepository)
                .methodName("save")
                .build();
    }


    @Bean
    public Step jpaRemoveStep( RepositoryItemReader<JpaAuthor> jpaAuthorItemReader,
                                ItemProcessor jpaAuthorRemoveProcessor ) {
        return stepBuilderFactory.get("jpaRemoveStep")
                .chunk(5)
                .reader(jpaAuthorItemReader)
                .processor(jpaAuthorRemoveProcessor)
                .build();
    }


    @Bean
    public ItemProcessor jpaAuthorRemoveProcessor(JpaAuthorRepository jpaAuthorRepository) {
        return new ItemProcessor<JpaAuthor, Object>() {
            @Override
            public Object process(JpaAuthor item) throws Exception {
                jpaAuthorRepository.delete(item);
                return null;
            }

        };
    }
}
