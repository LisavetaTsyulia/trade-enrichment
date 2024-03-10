package com.verygoodbank.tes.service.batchImpl.listener;

import com.verygoodbank.tes.exception.DateFormatValidationException;
import com.verygoodbank.tes.model.Trade;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class TradeProcessorListenerTest {

    @Test
    public void testProcessorListener() {
        // given trade processor listener
        TradeProcessorListener listener = new TradeProcessorListener();
        Trade trade = Mockito.mock(Trade.class);
        when(trade.getDate()).thenReturn("111");

        // when onProcessError is triggered
        listener.onProcessError(trade, new DateFormatValidationException());

        // then listener gets trade date field to log error
        verify(trade, times(1)).getDate();
    }
}
