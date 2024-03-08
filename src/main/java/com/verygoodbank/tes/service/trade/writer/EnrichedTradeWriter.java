package com.verygoodbank.tes.service.trade.writer;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.verygoodbank.tes.model.TradeEnriched;
import com.verygoodbank.tes.service.CSVWriterI;
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
        File tmpFile;
        try {
            tmpFile = File.createTempFile("prefix-", "-suffix");
            tmpFile.deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (Writer writer  = new FileWriter(tmpFile)) {

            StatefulBeanToCsv<TradeEnriched> sbc = new StatefulBeanToCsvBuilder<TradeEnriched>(writer)
                    .withQuotechar('\'')
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            sbc.write(data.stream());
        } catch (IOException e) {
            log.error("Error while parsing csv", e);
        } catch (CsvRequiredFieldEmptyException e) {
            log.error("Error while casting csv", e);
        } catch (CsvDataTypeMismatchException e) {
            log.error("Error csv type mismatch", e);
        }
        return tmpFile;
    }
}
