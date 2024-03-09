package com.verygoodbank.tes.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Trade {
    @CsvBindByName(column = "date")
    private String date;

    @CsvBindByName(column = "product_id")
    private int productId;

    @CsvBindByName(column = "currency")
    private String currency;

    @CsvBindByName(column = "price")
    private BigDecimal price;
}
