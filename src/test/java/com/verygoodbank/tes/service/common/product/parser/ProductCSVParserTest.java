package com.verygoodbank.tes.service.common.product.parser;

import com.verygoodbank.tes.exception.ProductsInitializationException;
import com.verygoodbank.tes.model.Product;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class ProductCSVParserTest {

    @Test
    public void parseProductFilename() {
        // given filename and products parser
        String filename = "/product.csv";
        ProductCSVParser csvParser = new ProductCSVParser();

        // when parsing provided file to receive products
        Collection<Product> products = csvParser.parse(filename);

        // received correct products
        assertNotNull(products);
        assertEquals(10, products.size());
    }

    @Test
    public void parseProductFilenameNotFound() {
        // given wrong filename and products parser
        String filename = "/product_wrong.csv";
        ProductCSVParser csvParser = new ProductCSVParser();

        // when parsing provided file to receive products
        // then expect ProductsInitializationException
        assertThrows(ProductsInitializationException.class, () -> csvParser.parse(filename));
    }
}
