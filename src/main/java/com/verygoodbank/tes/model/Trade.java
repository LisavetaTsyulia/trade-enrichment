package com.verygoodbank.tes.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Trade {
    @CsvDate(value = "yyyyMMdd")
    @CsvBindByName(column = "date")
    private Date date;
    @CsvBindByName(column = "product_id")
    private int productId;
    @CsvBindByName(column = "currency")
    private String currency;
    @CsvBindByName(column = "price")
    private BigDecimal price;
}
