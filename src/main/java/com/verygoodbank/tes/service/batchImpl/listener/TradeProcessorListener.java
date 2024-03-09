package com.verygoodbank.tes.service.batchImpl.listener;

import com.verygoodbank.tes.exception.DateFormatValidationException;
import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.model.TradeEnriched;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ItemProcessListener;

@Log4j2
public class TradeProcessorListener implements ItemProcessListener<Trade, TradeEnriched> {

    @Override
    public void onProcessError(Trade trade, Exception ex) {
        if (ex instanceof DateFormatValidationException)
            log.error("Trade date is not valid, expected yyyyMMdd, received: {}", trade.getDate());
    }

}
