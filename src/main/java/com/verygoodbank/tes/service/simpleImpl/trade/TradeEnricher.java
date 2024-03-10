package com.verygoodbank.tes.service.simpleImpl.trade;

import com.verygoodbank.tes.model.TradeEnriched;
import org.springframework.core.io.Resource;

import java.util.Collection;

public interface TradeEnricher {

    Collection<TradeEnriched> enrichTradeWithProductName(Resource file);
}
