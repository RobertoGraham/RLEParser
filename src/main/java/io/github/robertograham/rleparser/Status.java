package io.github.robertograham.rleparser;

import java.util.EnumSet;
import java.util.stream.Collectors;

public enum Status {

    ALIVE("o"),
    DEAD("b");

    private final String code;

    Status(String code) {
        this.code = code;
    }

    public static Status fromCode(String statusCode) {
        return EnumSet.allOf(Status.class).stream()
                .filter(status -> status.code.equals(statusCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status code '" + statusCode + "' not found in [" + EnumSet.allOf(Status.class).stream().map(Status::getCode).collect(Collectors.joining(",")) + "]"));
    }

    public String getCode() {
        return code;
    }
}
