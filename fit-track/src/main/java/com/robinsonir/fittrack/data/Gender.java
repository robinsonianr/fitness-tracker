package com.robinsonir.fittrack.data;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    FORBIDDEN("Forbidden");

    @JsonValue
    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
