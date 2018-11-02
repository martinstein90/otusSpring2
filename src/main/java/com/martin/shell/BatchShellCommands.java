package com.martin.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class BatchShellCommands {

    private JobLauncher jobLauncher;
    private Job jpaToMongoJob;

    public BatchShellCommands(JobLauncher jobLauncher, Job jpaToMongoJob) {
        this.jobLauncher = jobLauncher;
        this.jpaToMongoJob = jpaToMongoJob;
    }

    @ShellMethod("start-migration")
    public void startMigration() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(jpaToMongoJob, new JobParametersBuilder().toJobParameters());
    }
}
