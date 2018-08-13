package io.github.robertograham.rleparser.domain.export;

import java.util.Objects;
import java.util.Set;

public final class PatternData {

    private final MetaData metaData;
    private final Set<Coordinate> coordinates;

    public PatternData(MetaData metaData, Set<Coordinate> coordinates) {
        this.metaData = metaData;
        this.coordinates = coordinates;
    }

    public MetaData metaData() {
        return metaData;
    }

    public Set<Coordinate> coordinates() {
        return coordinates;
    }


    @Override
    public String toString() {
        return "PatternData{" +
                "metaData=" + metaData +
                ", coordinates=" + coordinates +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof PatternData))
            return false;

        var patternData = (PatternData) object;

        return Objects.equals(metaData, patternData.metaData) &&
                Objects.equals(coordinates, patternData.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metaData, coordinates);
    }
}
