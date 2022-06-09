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
public enum Category {
    ABM("ABM"),
    CODING("Coding"),
    HUMAN_RESOURCES("Human Resources"),
    PERFORMANCE_MANAGEMENT("Performance Management"),
    PRE_SALES("Pre Sales"),
    QUALITY_ASSURANCE("Quality Assurance"),
    SALES_ENGINEERING("Sales Engineering"),
    TIER_1("Tier 1"),
    TIER_2("Tier 2"),
    TIER_3("Tier 3");

    private String text;

    @JsonValue
    public String getText() {
        return text;
    }

    @JsonCreator
    public static Category fromText(String text) {
        Category category = Arrays.stream(Category.values())
                .filter(value -> value.getText().equals(text))
                .findFirst()
                .orElseThrow(() -> new FeeException("Unable to resolve text: " + text));

        return category;
    }
}
