package com.clariti.homework.fee.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeeRequest {
    private Department department;
    private Category category;
    private SubCategory subCategory;
    private Type type;
}
