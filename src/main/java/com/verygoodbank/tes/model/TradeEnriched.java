package com.verygoodbank.tes.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class TradeEnriched {
    private String date;
    private String product_name;
    private String currency;
    private BigDecimal price;
}
