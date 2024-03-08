package com.verygoodbank.tes.service.trade;

import com.verygoodbank.tes.model.TradeEnriched;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface TradeEnricher {

    Collection<TradeEnriched> enrichTradeWithProductName(MultipartFile file);
}
