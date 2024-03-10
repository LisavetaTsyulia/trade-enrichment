package com.verygoodbank.tes.service.common.product.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import com.verygoodbank.tes.exception.ProductsInitializationException;
import com.verygoodbank.tes.model.Product;
import com.verygoodbank.tes.service.common.csvparser.CSVParserFile;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Collection;

@Log4j2
@Component
public class ProductCSVParser implements CSVParserFile<Product> {

    @Override
    public Collection<Product> parse(final String fileName) {
        try (InputStream in = getClass().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            return new CsvToBeanBuilder(reader)
                    .withType(Product.class)
                    .withIgnoreEmptyLine(true)
                    .build()
                    .parse();
        } catch (Exception ex) {
            throw new ProductsInitializationException(ex);
        }
    }
}
