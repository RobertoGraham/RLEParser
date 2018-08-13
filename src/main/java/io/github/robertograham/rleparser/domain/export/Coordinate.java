package io.github.robertograham.rleparser.domain.export;

import java.util.Objects;

public final class Coordinate {

    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public Coordinate withX(int x) {
        return new Coordinate(x, y);
    }

    public Coordinate withY(int y) {
        return new Coordinate(x, y);
    }

    public Coordinate plusToX(int amount) {
        return withX(x + amount);
    }

    public Coordinate plusToY(int amount) {
        return withY(y + amount);
    }


    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof Coordinate))
            return false;

        var coordinate = (Coordinate) object;

        return x == coordinate.x &&
                y == coordinate.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
