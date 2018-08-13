package io.github.robertograham.rleparser.domain.export;

import java.util.Objects;

public final class MetaData {

    private final int width;
    private final int height;
    private final String rule;

    public MetaData(int width, int height, String rule) {
        this.width = width;
        this.height = height;
        this.rule = rule;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public String rule() {
        return rule;
    }

    @Override
    public String toString() {
        return "MetaData{" +
                "x=" + width +
                ", y=" + height +
                ", rule='" + rule + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof MetaData))
            return false;

        var metaData = (MetaData) object;

        return width == metaData.width &&
                height == metaData.height &&
                Objects.equals(rule, metaData.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, rule);
    }
}
