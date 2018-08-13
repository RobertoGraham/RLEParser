package io.github.robertograham.rleparser.domain.export;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoordinateTests {

    @DisplayName("with x, with y")
    @ParameterizedTest(name = "C({0}, {1}).withX({2}).withY({3}) = C({2}, {3})")
    @CsvSource({
            "0,0,1,0",
            "1,0,1,0",
            "2,0,1,0",
            "0,4,1,4",
            "1,2,1,2",
            "0,5,0,5"
    })
    void withXWithY(int x, int y, int newX, int newY) {
        Coordinate c = new Coordinate(x, y);
        Coordinate cWithNewXWithNewY = c.withX(newX).withY(newY);

        assertEquals(newX, cWithNewXWithNewY.x(), String.format("C(%s, %s).withX(%s).withY(%s).x() should be %s", x, y, newX, newY, newX));
        assertEquals(newY, cWithNewXWithNewY.y(), String.format("C(%s, %s).withX(%s).withY(%s).y() should be %s", x, y, newX, newY, newY));
    }

    @DisplayName("plus to x, plus to y")
    @ParameterizedTest(name = "C({0}, {1}).plusToX({2}).plusToY({3}) = C({0} + {2}, {1} + {3})")
    @CsvSource({
            "0,0,1,0",
            "1,0,1,0",
            "2,0,1,0",
            "0,4,1,4",
            "1,2,-10,2",
            "0,5,0,-1"
    })
    void plusToXPlusToY(int x, int y, int amountX, int amountY) {
        Coordinate c = new Coordinate(x, y);
        Coordinate cPlusToXPlusToY = c.plusToX(amountX).plusToY(amountY);

        assertEquals(x + amountX, cPlusToXPlusToY.x(), String.format("C(%s, %s).plusToX(%s).plusToY(%s).x() should be %s", x, y, amountX, amountY, x + amountX));
        assertEquals(y + amountY, cPlusToXPlusToY.y(), String.format("C(%s, %s).plusToX(%s).plusToY(%s).y() should be %s", x, y, amountX, amountY, y + amountY));
    }
}
