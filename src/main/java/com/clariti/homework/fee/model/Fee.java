package com.clariti.homework.fee.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Terry Deng
 */

@Data
@AllArgsConstructor
public class Fee {
    private String id;
    private String name;
    private String description;

    private Department department;
    private Category category;
    private SubCategory subCategory;
    private Type type;

    private Integer quantity;
    private BigDecimal unitPrice;

    public BigDecimal getSubTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
