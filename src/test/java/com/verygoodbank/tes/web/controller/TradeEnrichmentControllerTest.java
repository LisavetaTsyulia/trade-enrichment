package com.verygoodbank.tes.web.controller;

import com.verygoodbank.tes.model.TradeEnriched;
import com.verygoodbank.tes.service.batchImpl.resourceEnricher.ResourceEnricher;
import com.verygoodbank.tes.service.simpleImpl.CSVWriterI;
import com.verygoodbank.tes.service.simpleImpl.trade.TradeEnricher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TradeEnrichmentController.class)
public class TradeEnrichmentControllerTest {

    private final MockMultipartFile mockMultipartFile =
            new MockMultipartFile(
                    "data",
                    "trades.csv",
                    "text/csv",
                    "data".getBytes(StandardCharsets.UTF_8));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResourceEnricher<File> resourceEnricherService;
    @MockBean
    private TradeEnricher tradeEnricher;
    @MockBean
    private CSVWriterI<TradeEnriched> csvWriter;

    @Test
    void enrichTradesSuccess() throws Exception {
        // given mocked mulrtipart file and response tmp file
        File tmpFile = File.createTempFile("tmp", ".csv");
        // and mocked services to return tmp file
        when(resourceEnricherService.isAlreadyRunning()).thenReturn(false);
        CompletableFuture<File> completableFuture = new CompletableFuture<>();
        when(resourceEnricherService.processResource(any())).thenReturn(completableFuture);
        completableFuture.complete(tmpFile);

        // when making multipart request then receive 200 response
        this.mockMvc.perform(multipart("/api/v1/enrich").file(mockMultipartFile).accept("text/csv")).andExpect(status().isOk());
        tmpFile.delete();
    }

    @Test
    void enrichTradesSimpleSuccess() throws Exception {
        // given mocked mulrtipart file and response tmp file
        File tmpFile = File.createTempFile("tmp", ".csv");
        // and mocked services to return tmp file
        when(tradeEnricher.enrichTradeWithProductName(any())).thenReturn(Collections.emptyList());
        when(csvWriter.persistToFile(any())).thenReturn(tmpFile);

        // when making multipart request then receive 200 response
        this.mockMvc.perform(multipart("/api/v1/enrich/simple").file(mockMultipartFile).accept("text/csv")).andExpect(status().isOk());
        tmpFile.delete();
    }

    @Test
    void enrichTradesServiceBusy() throws Exception {
        // given mocked multipart file and job is running at the moment
        // and mocked services to return tmp file
        when(resourceEnricherService.isAlreadyRunning()).thenReturn(true);

        // when making multipart request then receive conflict response
        this.mockMvc.perform(multipart("/api/v1/enrich").file(mockMultipartFile).accept("text/csv")).andExpect(status().isConflict());
    }

    @Test
    void enrichTradesJobError() throws Exception {
        // given mocked multipart file and job is running at the moment
        // and mocked services to return tmp file
        when(resourceEnricherService.isAlreadyRunning()).thenReturn(true);

        // when making multipart request then receive conflict response
        this.mockMvc.perform(multipart("/api/v1/enrich").file(mockMultipartFile).accept("text/csv")).andExpect(status().isConflict());
    }
}
