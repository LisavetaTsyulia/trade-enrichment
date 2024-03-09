package com.verygoodbank.tes.service.batchImpl.processor;

import com.verygoodbank.tes.constant.TradeEnrichConstant;
import com.verygoodbank.tes.exception.DateFormatValidationException;
import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.model.TradeEnriched;
import com.verygoodbank.tes.service.common.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.validator.GenericValidator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class TradeProcessor implements ItemProcessor<Trade, TradeEnriched> {

    private final ProductMapper productMapper;

    @Override
    public TradeEnriched process(final Trade trade) {
        if (!GenericValidator.isDate(trade.getDate(), TradeEnrichConstant.DATE_VALID_FORMAT, true))
            throw new DateFormatValidationException();
        return TradeEnriched.builder()
                .product_name(productMapper.mapIdToName(trade.getProductId()))
                .date(trade.getDate())
                .price(trade.getPrice())
                .currency(trade.getCurrency())
                .build();
    }

}
