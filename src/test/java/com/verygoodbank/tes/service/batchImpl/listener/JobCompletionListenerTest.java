package com.verygoodbank.tes.service.batchImpl.listener;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.JobExecution;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JobCompletionListenerTest {

    @Test
    public void testJobCompletionListener() {
        // given JobCompletionListener and not completed future
        JobCompletionListener listener = new JobCompletionListener();
        CompletableFuture<JobExecution> future = listener.getJobCompletionFuture();
        assertFalse(future.isDone());
        JobExecution jobExecution = Mockito.mock(JobExecution.class);

        // when triggered afterJob for listener
        listener.afterJob(jobExecution);

        // then future was completed and reset
        assertTrue(future.isDone());
        assertFalse(listener.getJobCompletionFuture().isDone());
    }
}
