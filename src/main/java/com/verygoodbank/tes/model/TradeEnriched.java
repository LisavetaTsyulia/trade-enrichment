package com.verygoodbank.tes.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class TradeEnriched {
    private Date date;
    private String productName;
    private String currency;
    private BigDecimal price;
}
