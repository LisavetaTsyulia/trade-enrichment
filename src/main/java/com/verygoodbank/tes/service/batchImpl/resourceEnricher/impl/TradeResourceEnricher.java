package com.verygoodbank.tes.service.batchImpl.resourceEnricher.impl;

import com.verygoodbank.tes.exception.TradeEnrichJobProcessingException;
import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.model.TradeEnriched;
import com.verygoodbank.tes.service.batchImpl.resourceEnricher.ResourceEnricher;
import com.verygoodbank.tes.service.batchImpl.listener.JobCompletionListener;
import com.verygoodbank.tes.util.FileHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TradeResourceEnricher implements ResourceEnricher<File> {

    private final JobLauncher jobLauncher;
    private final Job job;
    private final JobCompletionListener jobCompletionListener;
    private final FlatFileItemReader<Trade> reader;
    private final FlatFileItemWriter<TradeEnriched> writer;

    @Override
    public CompletableFuture<File> processResource(final Resource resource) {
        try {
            reader.setResource(resource);
            File file = FileHelper.createTmpFile();
            writer.setResource(new FileSystemResource(file));
            jobLauncher.run(job, new JobParameters());
            return jobCompletionListener.getJobCompletionFuture()
                    .thenApply(jobExecution -> file);
        } catch (JobExecutionException e) {
            throw new TradeEnrichJobProcessingException(e);
        }
    }
}
