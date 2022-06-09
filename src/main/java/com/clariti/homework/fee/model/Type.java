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
public enum Type {
    TYPE_A("TypeA"),
    TYPE_B("TypeB"),
    TYPE_C("TypeC");

    private String text;

    @JsonValue
    public String getText() {
        return text;
    }

    @JsonCreator
    public static Type fromText(String text) {
        Type type = Arrays.stream(Type.values())
                .filter(value -> value.getText().equals(text))
                .findFirst()
                .orElseThrow(() -> new FeeException("Unable to resolve text: " + text));

        return type;
    }
}
