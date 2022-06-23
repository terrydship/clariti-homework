package com.clariti.homework.fee.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TotalFee {
    private BigDecimal feeWithSurcharge;
}
