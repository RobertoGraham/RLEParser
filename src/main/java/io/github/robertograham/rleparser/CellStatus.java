package io.github.robertograham.rleparser;

import java.util.EnumSet;

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
                .orElse(null);
    }

    public String getCode() {
        return code;
    }
}
