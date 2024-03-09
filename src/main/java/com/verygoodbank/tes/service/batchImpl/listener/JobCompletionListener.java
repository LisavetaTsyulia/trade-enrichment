package com.verygoodbank.tes.service.batchImpl.listener;

import lombok.Getter;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Getter
@Component
public class JobCompletionListener implements JobExecutionListener {

    private final CompletableFuture<JobExecution> jobCompletionFuture = new CompletableFuture<>();

    @Override
    public void afterJob(JobExecution jobExecution) {
        jobCompletionFuture.complete(jobExecution);
    }

}