package io.github.robertograham.rleparser;

import java.util.EnumSet;
import java.util.stream.Collectors;

public enum CellStatus {

    ALIVE("o"),
    DEAD("b");

    private final String code;

    CellStatus(String code) {
        this.code = code;
    }

    public static CellStatus fromCode(String code) {
        return EnumSet.allOf(CellStatus.class).stream()
                .filter(cellStatus -> cellStatus.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Code '" + code + "' not found in [" + EnumSet.allOf(CellStatus.class).stream().map(CellStatus::getCode).collect(Collectors.joining(",")) + "]"));
    }

    public String getCode() {
        return code;
    }
}
