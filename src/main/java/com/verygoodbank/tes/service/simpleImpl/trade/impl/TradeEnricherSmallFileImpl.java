package com.verygoodbank.tes.service.simpleImpl.trade.impl;

import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.model.TradeEnriched;
import com.verygoodbank.tes.service.common.CSVParserMultipartFile;
import com.verygoodbank.tes.service.common.product.ProductMapper;
import com.verygoodbank.tes.service.simpleImpl.trade.TradeEnricher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeEnricherSmallFileImpl implements TradeEnricher {

    private final ProductMapper productMapper;
    private final CSVParserMultipartFile<Trade> tradeCSVParser;

    @Override
    public Collection<TradeEnriched> enrichTradeWithProductName(final MultipartFile file) {
        return tradeCSVParser.parse(file).stream().map(this::enrichTrade).collect(Collectors.toList());
    }

    private TradeEnriched enrichTrade(final Trade trade) {
        return TradeEnriched.builder()
                .product_name(productMapper.mapIdToName(trade.getProductId()))
                .date(trade.getDate())
                .price(trade.getPrice())
                .currency(trade.getCurrency())
                .build();
    }

}
