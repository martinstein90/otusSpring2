package com.martin.batch;

import com.martin.domain.JpaToMongoTransfer;
import com.martin.domain.jpa.JpaAuthor;
import com.martin.domain.mongo.MongoAuthor;
import com.martin.repository.jpa.JpaAuthorRepository;
import com.martin.repository.mongo.MongoAuthorRepository;
import javafx.beans.binding.StringBinding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@EnableBatchProcessing
@Configuration
@Slf4j
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jpaToMongoJob(Step jpaToMongoStep) {
        return jobBuilderFactory.get("jpaToMongoJob")
                .incrementer(new RunIdIncrementer())
                .flow(jpaToMongoStep)
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
                .listener(new ItemReadListener() {
                    public void beforeRead() { log.info("Начало чтения"); }
                    public void afterRead(Object object) { log.info("Конец чтения " + object); }
                    public void onReadError(Exception exception) { log.info("Ошибка чтения " + exception); }
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) { log.info("Начало записи " + list); }
                    public void afterWrite(List list) { log.info("Конец записи " + list); }
                    public void onWriteError(Exception exception, List list) { log.info("Ошибка записи " + list + " " + exception); }
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object object) {log.info("Начало обработки " + object);}
                    public void afterProcess(Object object1, Object object2) {log.info("Конец обработки " + object1 + " " + object2);}
                    public void onProcessError(Object object, Exception exception) {log.info("Ошибка обработки " + object + " " + exception);}
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {log.info("Начало пачки " + chunkContext);}
                    public void afterChunk(ChunkContext chunkContext) {log.info("Конец пачки " + chunkContext);}
                    public void afterChunkError(ChunkContext chunkContext) {log.info("Ошибка пачки " + chunkContext);}
                })
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

}
