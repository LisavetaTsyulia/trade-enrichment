package com.verygoodbank.tes.web.controller;


import com.verygoodbank.tes.exception.JobBusyException;
import com.verygoodbank.tes.model.TradeEnriched;
import com.verygoodbank.tes.service.batchImpl.resourceEnricher.ResourceEnricher;
import com.verygoodbank.tes.service.simpleImpl.CSVWriterI;
import com.verygoodbank.tes.service.simpleImpl.trade.TradeEnricher;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class TradeEnrichmentController {

    // Simple openscv solution related beans
    private final TradeEnricher tradeEnricher;
    private final CSVWriterI<TradeEnriched> csvWriter;

    // Spring Batch related Beans
    private final ResourceEnricher<File> resourceEnricher;

    @PostMapping(value = "/enrich", consumes = "multipart/form-data", produces = "text/csv")
    public ResponseEntity<FileSystemResource> enrichTrades(@RequestParam MultipartFile data) throws ExecutionException, InterruptedException {
        if (resourceEnricher.isAlreadyRunning())
            throw new JobBusyException();

        File file = resourceEnricher.processResource(data.getResource()).get();
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=enrichedTrades.csv")
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new FileSystemResource(file));
    }

    @PostMapping(value = "/enrich/simple", consumes = "multipart/form-data", produces = "text/csv")
    public ResponseEntity<Resource> enrichTradesSimple(@RequestParam MultipartFile data) {
        Collection<TradeEnriched> trades = tradeEnricher.enrichTradeWithProductName(data.getResource());

        File enrichedFile = csvWriter.persistToFile(trades);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=enrichedTrades.csv")
                .contentLength(enrichedFile.length())
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new FileSystemResource(enrichedFile));
    }

}


