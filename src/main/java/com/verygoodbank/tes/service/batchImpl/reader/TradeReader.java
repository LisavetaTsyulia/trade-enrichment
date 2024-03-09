package com.verygoodbank.tes.service.batchImpl.reader;

import com.verygoodbank.tes.model.Trade;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Component;

@Component
public class TradeReader extends FlatFileItemReader<Trade> {

    private static final String[] TRADE_NAMES = new String[]{"date","product_id","currency","price"};
    private static final int CSV_HEADER_SKIP_COUNT = 1;

    public TradeReader() {
        super.setLinesToSkip(CSV_HEADER_SKIP_COUNT);
        super.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(TRADE_NAMES);
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                setTargetType(Trade.class);
            }});
        }});
    }
}
