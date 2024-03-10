package com.verygoodbank.tes.service.common.product.impl;

import com.verygoodbank.tes.constant.TradeEnrichConstant;
import com.verygoodbank.tes.model.Product;
import com.verygoodbank.tes.service.common.product.ProductMapper;
import com.verygoodbank.tes.service.common.csvparser.CSVParserFile;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class InMemoryProductMapperImpl implements ProductMapper {
    boolean isInitialized = false;
    Map<Integer, String> mapIdToName = new HashMap<>();

    private final CSVParserFile<Product> productCSVParser;

    public InMemoryProductMapperImpl(final CSVParserFile<Product> productCSVParser) {
        this.productCSVParser = productCSVParser;
        init();
    }

    private void init() {
        // read from file and init it's state
        Instant startedInitProducts = Instant.now();
        Collection<Product> products = productCSVParser.parse(TradeEnrichConstant.PRODUCTS_FILE_NAME);
        products.forEach(p -> mapIdToName.put(p.getProductId(), p.getProductName()));
        isInitialized = true;
        Instant finishedInitProducts = Instant.now();
        log.info("Finished Initializing products that took {} milliseconds. Map size is: {}",
                finishedInitProducts.toEpochMilli() - startedInitProducts.toEpochMilli(), mapIdToName.size());
    }


    @Override
    public String mapIdToName(int id) {
        if (isInitialized) {
            return mapIdToName.getOrDefault(id, TradeEnrichConstant.MISSING_PRODUCT_NAME);
        }
        return "";
    }
}
