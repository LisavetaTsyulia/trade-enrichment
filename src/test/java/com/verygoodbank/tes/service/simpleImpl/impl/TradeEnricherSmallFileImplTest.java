package com.verygoodbank.tes.service.simpleImpl.impl;

import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.model.TradeEnriched;
import com.verygoodbank.tes.service.common.csvparser.CSVParserResource;
import com.verygoodbank.tes.service.common.product.ProductMapper;
import com.verygoodbank.tes.service.simpleImpl.trade.TradeEnricher;
import com.verygoodbank.tes.service.simpleImpl.trade.impl.TradeEnricherSmallFileImpl;
import com.verygoodbank.tes.service.simpleImpl.trade.parser.TradeCSVParser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.Resource;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TradeEnricherSmallFileImplTest {

    @Test
    public void testEnrichingTrades() {
        // given configured trade Enrich service with mocked dependencies
        ProductMapper mapper = Mockito.mock(ProductMapper.class);
        CSVParserResource<Trade> parser = Mockito.mock(TradeCSVParser.class);
        TradeEnricher tradeEnricher = new TradeEnricherSmallFileImpl(mapper, parser);
        Resource resource = Mockito.mock(Resource.class);
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
        when(parser.parse(any())).thenReturn(Collections.singletonList(trade));
        when(mapper.mapIdToName(eq(productId))).thenReturn(mappedProductName);

        // when enriching trade
        Collection<TradeEnriched> enrichedTrades = tradeEnricher.enrichTradeWithProductName(resource);

        // then make sure expected trades are received
        assertNotNull(enrichedTrades);
        assertEquals(1, enrichedTrades.size());
        TradeEnriched enriched = enrichedTrades.stream().findFirst().get();
        assertEquals(mappedProductName, enriched.getProduct_name());
        assertEquals(date, enriched.getDate());
        assertEquals(price, enriched.getPrice());
        assertEquals(currency, enriched.getCurrency());
    }
}
