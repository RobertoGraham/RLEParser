package io.github.robertograham.rleparser.domain.enumeration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusTests {

    @DisplayName("from code")
    @ParameterizedTest(name = "Status.fromCode({0}.getCode()) = {0}")
    @EnumSource(value = Status.class)
    void fromCode(Status status) {
        Status statusFromCode = Status.fromCode(status.getCode());

        assertEquals(statusFromCode, status, String.format("Status.fromCode(%s) = %s", status.getCode(), status));
    }

    @Test
    @DisplayName("from code thrown illegal argument exception given unknown code")
    void fromCodeWithUnknownCode() {
        assertThrows(IllegalArgumentException.class, () -> Status.fromCode(""));
    }
}
