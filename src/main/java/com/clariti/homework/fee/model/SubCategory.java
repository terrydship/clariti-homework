package com.clariti.homework.fee.model;

import com.clariti.homework.fee.FeeException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * @author Terry Deng
 */

@AllArgsConstructor
public enum SubCategory {
    CAT_1("Cat1"),
    CAT_2("Cat2"),
    CAT_3("Cat3");

    private String text;

    @JsonValue
    public String getText() {
        return text;
    }

    @JsonCreator
    public static SubCategory fromText(String text) {
        SubCategory subCategory = Arrays.stream(SubCategory.values())
                .filter(value -> value.getText().equals(text))
                .findFirst()
                .orElseThrow(() -> new FeeException("Unable to resolve text: " + text));

        return subCategory;
    }
}
