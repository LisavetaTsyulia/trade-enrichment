package com.verygoodbank.tes.service.batchImpl.processor;

import com.verygoodbank.tes.exception.DateFormatValidationException;
import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.model.TradeEnriched;
import com.verygoodbank.tes.service.common.product.ProductMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class TradeProcessorTest {

    @Test
    public void testTradeEnrich() {
        // given tradeProcessor with mocked product mapper
        ProductMapper mapper = Mockito.mock(ProductMapper.class);
        TradeProcessor tradeProcessor = new TradeProcessor(mapper);
        // expected trade values
        int productId = 1;
        String currency = "BTC";
        String date = "19991010";
        BigDecimal price = BigDecimal.ONE;
        String mappedProductName = "NEW";
        // and trade
        Trade trade = new Trade();
        trade.setDate(date);
        trade.setCurrency(currency);
        trade.setPrice(price);
        trade.setProductId(productId);
        when(mapper.mapIdToName(eq(productId))).thenReturn(mappedProductName);

        // when processing Trade
        TradeEnriched enriched = tradeProcessor.process(trade);

        // then expected enrich trade is returned
        assertEquals(mappedProductName, enriched.getProduct_name());
        assertEquals(date, enriched.getDate());
        assertEquals(price, enriched.getPrice());
        assertEquals(currency, enriched.getCurrency());
    }
    @Test
    public void testTradeEnrichDateValidationFails() {
        // given tradeProcessor with mocked product mapper
        ProductMapper mapper = Mockito.mock(ProductMapper.class);
        TradeProcessor tradeProcessor = new TradeProcessor(mapper);
        // wrong date
        String wrongDate = "99999999";
        Trade trade = new Trade();
        trade.setDate(wrongDate);

        // when processing Trade
        // then expect exception to be thrown
        assertThrows(DateFormatValidationException.class, () -> tradeProcessor.process(trade));
    }
}
