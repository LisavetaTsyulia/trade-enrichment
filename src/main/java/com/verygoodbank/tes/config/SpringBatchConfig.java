package com.verygoodbank.tes.config;

import com.verygoodbank.tes.exception.DateFormatValidationException;
import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.model.TradeEnriched;
import com.verygoodbank.tes.service.batchImpl.listener.JobCompletionListener;
import com.verygoodbank.tes.service.batchImpl.listener.TradeProcessorListener;
import com.verygoodbank.tes.service.batchImpl.processor.TradeProcessor;
import com.verygoodbank.tes.service.batchImpl.reader.TradeReader;
import com.verygoodbank.tes.service.batchImpl.writer.TradeEnrichedWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    private static final int THREAD_POOL_CORE_NUM = 4;
    private static final int THREAD_POOL_MAX_NUM = 10;
    private static final int STEP_CHUNK_SIZE = 1000;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadTaskExecutor = new ThreadPoolTaskExecutor();
        threadTaskExecutor.setCorePoolSize(THREAD_POOL_CORE_NUM);
        threadTaskExecutor.setMaxPoolSize(THREAD_POOL_MAX_NUM);
        return threadTaskExecutor;
    }

    @Bean
    public Step tradeEnrichStep(final TradeEnrichedWriter tradeEnrichedWriter,
                                final TradeReader tradeReader,
                                final TradeProcessor tradeProcessor,
                                final JobRepository jobRepository,
                                final PlatformTransactionManager transactionManager) {
        return new StepBuilder("tradeEnrichStep", jobRepository)
                .<Trade, TradeEnriched>chunk(STEP_CHUNK_SIZE, transactionManager) // The Java-specific name of the dependency that indicates that this is an item-based step and the number of items to be processed before the transaction is committed.
                .reader(tradeReader)
                .processor(tradeProcessor)
                .writer(tradeEnrichedWriter)
                .listener(new TradeProcessorListener())
                .faultTolerant() // don't fail job if records validation failed
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .skip(DateFormatValidationException.class)
                .taskExecutor(taskExecutor()) // Multi-threaded Step
                .allowStartIfComplete(true) // when state is complete it's allowed to start job again
                .build();
    }

    @Bean
    public Job tradeEnrichJob(final JobRepository jobRepository,
                              final Step step,
                              final JobCompletionListener jobCompletionListener) {
        return new JobBuilder("tradeEnrichJob", jobRepository)
                .start(step)
                .listener(jobCompletionListener)
                .build();
    }

}
