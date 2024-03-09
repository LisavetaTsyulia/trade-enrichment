package com.verygoodbank.tes.service.simpleImpl.trade.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.service.common.CSVParserMultipartFile;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Log4j2
@Component
public class TradeCSVParser implements CSVParserMultipartFile<Trade> {

    @Override
    public Collection<Trade> parse(final MultipartFile file) {

        List<Trade> trades = Collections.emptyList();
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"))) {
            trades = new CsvToBeanBuilder(reader)
                    .withType(Trade.class)
                    .withIgnoreEmptyLine(true)
                    .build()
                    .parse();
        } catch (IOException ex) {
            log.error("Error parsing csv: ", ex);
        }
        return trades;
    }
}
