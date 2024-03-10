package com.verygoodbank.tes.service.batchImpl.reader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TradeReaderTest {

    @Test
    public void tradeReaderTest() {
        TradeReader tradeReader = new TradeReader();

        assertNotNull(tradeReader);
    }
}
