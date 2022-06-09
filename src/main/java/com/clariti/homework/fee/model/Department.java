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
public enum Department {
    DEVELOPMENT("Development", 1.2),
    MARKETING("Marketing", 1.1),
    OPERATIONS("Operations", 0.85),
    SALES("Sales", 1.15),
    SUPPORT("Support", 0.95);

    private String text;
    private Double surcharge;

    @JsonValue
    public String getText() {
        return text;
    }

    public Double getSurcharge() {
        return surcharge;
    }

    @JsonCreator
    public static Department fromText(String text) {
        Department department = Arrays.stream(Department.values())
                .filter(value -> value.getText().equals(text))
                .findFirst()
                .orElseThrow(() -> new FeeException("Unable to resolve text: " + text));

        return department;
    }
}
