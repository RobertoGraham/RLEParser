package io.github.robertograham.rleparser.domain.enumeration;

import java.util.EnumSet;
import java.util.stream.Collectors;

public enum Status {

    ALIVE("o"),
    DEAD("b"),
    LINE_END("$");

    private static final String values = EnumSet.allOf(Status.class).stream()
            .map(Status::getCode)
            .collect(Collectors.joining(","));

    private final String code;

    Status(String code) {
        this.code = code;
    }

    public static Status fromCode(String statusCode) {
        return EnumSet.allOf(Status.class).stream()
                .filter(status -> status.code.equals(statusCode))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Code: '" + statusCode + "' not found in [" + values + "]")
                );
    }

    public String getCode() {
        return code;
    }
}
