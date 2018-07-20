package io.github.robertograham.rleparser;

import java.util.Objects;

public class Pattern {

    private final MetaData metaData;
    private final LiveCells liveCells;

    public Pattern(MetaData metaData, LiveCells liveCells) {
        this.metaData = metaData;
        this.liveCells = liveCells;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public LiveCells getLiveCells() {
        return liveCells;
    }

    @Override
    public String toString() {
        return "Pattern{" +
                "metaData=" + metaData +
                ", liveCells=" + liveCells +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof Pattern))
            return false;

        Pattern pattern = (Pattern) object;

        return Objects.equals(metaData, pattern.metaData) &&
                Objects.equals(liveCells, pattern.liveCells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metaData, liveCells);
    }
}
