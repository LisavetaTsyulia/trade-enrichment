package com.verygoodbank.tes.web.controller;


import com.verygoodbank.tes.model.TradeEnriched;
import com.verygoodbank.tes.service.CSVWriterI;
import com.verygoodbank.tes.service.trade.TradeEnricher;
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

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class TradeEnrichmentController {

     private final TradeEnricher tradeEnricher;
     private final CSVWriterI<TradeEnriched> csvWriter;

     @PostMapping(value = "/enrich", consumes = "multipart/form-data", produces = "text/csv")
     public ResponseEntity<Resource> enrichTrades(@RequestParam MultipartFile data) {

          Collection<TradeEnriched> trades = tradeEnricher.enrichTradeWithProductName(data);

          File enrichedFile = csvWriter.persistToFile(trades);

          return ResponseEntity.ok()
                  .header("Content-Disposition", "attachment; filename=enrichedTrades.csv")
                  .contentLength(enrichedFile.length())
                  .contentType(MediaType.parseMediaType("text/csv"))
                  .body(new FileSystemResource(enrichedFile));
     }

}


