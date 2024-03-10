package com.verygoodbank.tes.service.simpleImpl.parser;

import com.verygoodbank.tes.exception.TradeEnrichJobProcessingException;
import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.service.simpleImpl.trade.parser.TradeCSVParser;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class TradeCSVParserTest {

    @Test
    public void testTradeParsing() {
        // given resource and trade CSV parser
        Resource resource = new FileSystemResource("src/test/resources/trade.csv");
        TradeCSVParser tradeCSVParser = new TradeCSVParser();

        // when performing parsing for trades
        Collection<Trade> trades = tradeCSVParser.parse(resource);

        // then receiving expected trades collection
        assertNotNull(trades);
        assertEquals(5, trades.size());
    }

    @Test
    public void parseTradeFilenameNotFound() {
        // given wrong filename and trades parser
        TradeCSVParser tradeCSVParser = new TradeCSVParser();
        Resource resource = new FileSystemResource("/trade_wrong.csv");

        // when parsing provided file to receive trades
        // then expect TradeEnrichJobProcessingException
        assertThrows(TradeEnrichJobProcessingException.class, () -> tradeCSVParser.parse(resource));
    }
}
