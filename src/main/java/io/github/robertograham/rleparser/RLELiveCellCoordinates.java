package io.github.robertograham.rleparser;

import java.util.Objects;
import java.util.Set;

public class RLELiveCellCoordinates {

    private final Set<Coordinate> coordinates;

    public RLELiveCellCoordinates(Set<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public Set<Coordinate> getCoordinates() {
        return coordinates;
    }

    @Override
    public String toString() {
        return "RLELiveCellCoordinates{" +
                "coordinates=" + coordinates +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof RLELiveCellCoordinates))
            return false;

        RLELiveCellCoordinates rleLiveCellCoordinates = (RLELiveCellCoordinates) object;

        return Objects.equals(coordinates, rleLiveCellCoordinates.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}
