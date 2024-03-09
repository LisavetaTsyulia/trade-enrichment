package com.verygoodbank.tes.service.simpleImpl.trade.writer;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import com.verygoodbank.tes.model.TradeEnriched;
import com.verygoodbank.tes.service.simpleImpl.CSVWriterI;
import com.verygoodbank.tes.util.FileHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

@Log4j2
@Service
public class EnrichedTradeWriter implements CSVWriterI<TradeEnriched> {
    @Override
    public File persistToFile(final Collection<TradeEnriched> data) {
        File tmpFile = FileHelper.createTmpFile();
        try (Writer writer  = new FileWriter(tmpFile)) {
            StatefulBeanToCsv<TradeEnriched> sbc = new StatefulBeanToCsvBuilder<TradeEnriched>(writer)
                    .withQuotechar('\'')
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            sbc.write(data.stream());
        } catch (IOException | CsvFieldAssignmentException e) {
            log.error("Error while parsing csv", e);
        }
        return tmpFile;
    }
}
