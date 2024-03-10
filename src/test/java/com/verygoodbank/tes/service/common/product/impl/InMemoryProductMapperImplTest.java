package com.verygoodbank.tes.service.common.product.impl;

import com.verygoodbank.tes.constant.TradeEnrichConstant;
import com.verygoodbank.tes.model.Product;
import com.verygoodbank.tes.service.common.csvparser.CSVParserFile;
import com.verygoodbank.tes.service.common.product.ProductMapper;
import com.verygoodbank.tes.service.common.product.parser.ProductCSVParser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class InMemoryProductMapperImplTest {

    @Test
    public void testInitializationMap() {
        // given mocked parser and predefined response after parsing products file
        CSVParserFile<Product> parser = Mockito.mock(ProductCSVParser.class);
        int productId = 1;
        String productName = "name";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        when(parser.parse(TradeEnrichConstant.PRODUCTS_FILE_NAME)).thenReturn(Collections.singleton(product));

        // when create service inMemoryMapper
        ProductMapper inMemoryMapper = new InMemoryProductMapperImpl(parser);

        // then initialization is called and map is filled
        // then we receive value if there is mapping and default missing value if nothing is there
        int notExistingProductId = 10;
        assertEquals(productName, inMemoryMapper.mapIdToName(productId));
        assertEquals(TradeEnrichConstant.MISSING_PRODUCT_NAME, inMemoryMapper.mapIdToName(notExistingProductId));
    }
}
