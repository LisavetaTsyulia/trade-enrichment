package com.verygoodbank.tes.service.product.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import com.verygoodbank.tes.model.Product;
import com.verygoodbank.tes.service.CSVParserFile;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Log4j2
@Component
public class ProductCSVParser implements CSVParserFile<Product> {

    @Override
    public Collection<Product> parse(final File file) {
        List<Product> products = Collections.emptyList();
        try {
            products = new CsvToBeanBuilder(new FileReader(file))
                    .withType(Product.class)
                    .withIgnoreEmptyLine(true)
                    .build()
                    .parse();
        } catch (FileNotFoundException ex) {
            log.error("File not found: ", ex);
        }
        return products;
    }
}
