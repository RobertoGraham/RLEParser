package io.github.robertograham.rleparser.helper;

import io.github.robertograham.rleparser.domain.StatusRun;
import io.github.robertograham.rleparser.domain.enumeration.Status;
import io.github.robertograham.rleparser.domain.export.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusRunHelperTests {

    @DisplayName("read status run")
    @ParameterizedTest(name = "StatusRunHelper.readStatusRun(\"{0}\", c).status() = {1}, StatusRunHelper.readStatusRun(\"{0}\", c).length() = {2}")
    @CsvSource({
            "o, ALIVE, 1",
            "2o, ALIVE, 2",
            "50b, DEAD, 50",
            "b, DEAD, 1",
            "$, LINE_END, 1",
            "32$, LINE_END, 32"
    })
    void readStatusRun(String encodedStatusRun, Status status, int length) {
        StatusRun statusRun = StatusRunHelper.readStatusRun(encodedStatusRun, new Coordinate(0, 0));
        assertEquals(status, statusRun.status(), String.format("StatusRunHelper.readStatusRun(%s, c).status() should be %s", encodedStatusRun, status));
        assertEquals(length, statusRun.length(), String.format("StatusRunHelper.readStatusRun(%s, c).length() should be %s", encodedStatusRun, length));
    }

    @DisplayName("read status run throws illegal argument exception given illegally formatted encoded status run")
    @ParameterizedTest(name = "StatusRunHelper.readStatusRun(\"{0}\", c) throws IllegalArgumentException")
    @ValueSource(strings = {
            "",
            "1",
            "oo",
            "4bb",
            "4i",
            "e"
    })
    void readStatusRunWithIllegallyFormattedEncodedStatusRun(String encodedStatusRun) {
        assertThrows(IllegalArgumentException.class, () -> StatusRunHelper.readStatusRun(encodedStatusRun, new Coordinate(0, 0)));
    }
}
