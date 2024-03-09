package com.verygoodbank.tes.service.batchImpl.writer;

import com.verygoodbank.tes.constant.TradeEnrichConstant;
import com.verygoodbank.tes.model.TradeEnriched;
import com.verygoodbank.tes.util.StringHeaderWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.stereotype.Component;

@Component
public class TradeEnrichedWriter extends FlatFileItemWriter<TradeEnriched> {

    private static final String HEADER_STRING = "date,product_name,currency,price";
    private static final String[] TRADE_FIELDS = new String[]{"date", "product_name", "currency", "price"};

    public TradeEnrichedWriter() {

        super.setHeaderCallback(new StringHeaderWriter(HEADER_STRING));
        super.setLineAggregator(new DelimitedLineAggregator<>() {
            {
                setDelimiter(TradeEnrichConstant.CSV_FILE_DELIMITER);
                setFieldExtractor(new BeanWrapperFieldExtractor<>() {
                    {
                        setNames(TRADE_FIELDS);
                    }
                });
            }
        });
    }
}
