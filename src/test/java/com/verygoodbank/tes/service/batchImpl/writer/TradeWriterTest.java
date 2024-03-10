package com.verygoodbank.tes.service.batchImpl.writer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TradeWriterTest {

    @Test
    public void tradeWriterTest() {
        TradeEnrichedWriter tradeEnrichedWriter = new TradeEnrichedWriter();

        assertNotNull(tradeEnrichedWriter);
    }
}
