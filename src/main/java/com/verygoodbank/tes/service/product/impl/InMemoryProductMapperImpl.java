package com.verygoodbank.tes.service.product.impl;

import com.verygoodbank.tes.model.Product;
import com.verygoodbank.tes.service.product.ProductMapper;
import com.verygoodbank.tes.service.CSVParserFile;
import com.verygoodbank.tes.util.CSVReadHelper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryProductMapperImpl implements ProductMapper {

    private static final String MISSING_PRODUCT_NAME = "Missing Product Name";
    private static final String PRODUCTS_FILE_NAME = "product.csv";
    boolean isInitialized = false;
    Map<Integer, String> mapIdToName = new HashMap<>(); // we don't need concurrency handling here,
    // only write on init and read may be in multi threads, but they are not changing anything

    private final CSVParserFile<Product> productCSVParser;

    public InMemoryProductMapperImpl(final CSVParserFile<Product> productCSVParser) {
        this.productCSVParser = productCSVParser;
        init();
    }

    private void init(){
        // read from file and init it's state
        Collection<Product> products = productCSVParser.parse(CSVReadHelper.getCSVFile(PRODUCTS_FILE_NAME));
        products.forEach(p -> mapIdToName.put(p.getProductId(), p.getProductName()));
        isInitialized = true;
    }


    @Override
    public String mapIdToName(int id) {
        if (isInitialized) {
           return mapIdToName.getOrDefault(id, MISSING_PRODUCT_NAME);
        } // else wait until initialized?
        return "";
    }
}
