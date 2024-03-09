package com.verygoodbank.tes.web.controller;


import com.verygoodbank.tes.exception.TradeEnrichJobProcessingException;
import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.model.TradeEnriched;
import com.verygoodbank.tes.service.simpleImpl.CSVWriterI;
import com.verygoodbank.tes.service.simpleImpl.trade.TradeEnricher;
import com.verygoodbank.tes.util.FileHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
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

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class TradeEnrichmentController {

     // Simple openscv solution related beans
     private final TradeEnricher tradeEnricher;
     private final CSVWriterI<TradeEnriched> csvWriter;

     // Spring Batch related Beans
     private final JobLauncher enrichTradeWithProductLauncher;
     private final FlatFileItemReader<Trade> reader;
     private final FlatFileItemWriter<TradeEnriched> writer;
     private final Job job;

     @PostMapping(value = "/enrich", consumes = "multipart/form-data", produces = "text/csv")
     public ResponseEntity<Resource> enrichTrades(@RequestParam MultipartFile data) {
          try {
               reader.setResource(data.getResource());
               File file = FileHelper.createTmpFile();
               writer.setResource(new FileSystemResource(file));
               JobExecution jobExecution = enrichTradeWithProductLauncher.run(job, new JobParameters());
               while (jobExecution.isRunning()) {}
               return ResponseEntity.ok()
                       .header("Content-Disposition", "attachment; filename=enrichedTrades.csv")
                       .contentLength(file.length())
                       .contentType(MediaType.parseMediaType("text/csv"))
                       .body(new FileSystemResource(file));
          } catch (JobExecutionException e) {
              throw new TradeEnrichJobProcessingException(e);
          }
     }

     @PostMapping(value = "/enrich/simple", consumes = "multipart/form-data", produces = "text/csv")
     public ResponseEntity<Resource> enrichTradesSimple(@RequestParam MultipartFile data) {
          Collection<TradeEnriched> trades = tradeEnricher.enrichTradeWithProductName(data);

          File enrichedFile = csvWriter.persistToFile(trades);

          return ResponseEntity.ok()
                  .header("Content-Disposition", "attachment; filename=enrichedTrades.csv")
                  .contentLength(enrichedFile.length())
                  .contentType(MediaType.parseMediaType("text/csv"))
                  .body(new FileSystemResource(enrichedFile));
     }

}


