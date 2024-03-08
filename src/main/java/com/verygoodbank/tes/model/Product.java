package com.verygoodbank.tes.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Product {

    @CsvBindByName(column = "product_id")
    private int productId;

    @CsvBindByName(column = "product_name")
    private String productName;
}
