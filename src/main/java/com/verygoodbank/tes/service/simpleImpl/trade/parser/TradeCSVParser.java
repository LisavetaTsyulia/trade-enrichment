package com.verygoodbank.tes.service.simpleImpl.trade.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import com.verygoodbank.tes.exception.TradeEnrichJobProcessingException;
import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.service.common.csvparser.CSVParserResource;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Collection;

@Log4j2
@Component
public class TradeCSVParser implements CSVParserResource<Trade> {

    @Override
    public Collection<Trade> parse(final Resource resource) {
        try (Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"))) {
            return new CsvToBeanBuilder(reader)
                    .withType(Trade.class)
                    .withIgnoreEmptyLine(true)
                    .build()
                    .parse();
        } catch (IOException ex) {
            throw new TradeEnrichJobProcessingException(ex);
        }
    }
}
