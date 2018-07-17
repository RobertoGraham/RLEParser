package io.github.robertograham.rleparser;

import java.util.Objects;

public class RLEHeader {

    private final int x;
    private final int y;
    private final String rule;

    public RLEHeader(int x, int y, String rule) {
        this.x = x;
        this.y = y;
        this.rule = rule;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getRule() {
        return rule;
    }

    @Override
    public String toString() {
        return "RLEHeader{" +
                "x=" + x +
                ", y=" + y +
                ", rule='" + rule + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof RLEHeader))
            return false;

        RLEHeader rleHeader = (RLEHeader) object;

        return x == rleHeader.x &&
                y == rleHeader.y &&
                Objects.equals(rule, rleHeader.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, rule);
    }
}
