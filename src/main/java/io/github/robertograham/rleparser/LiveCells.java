package io.github.robertograham.rleparser;

import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class LiveCells {

    private final Set<Coordinate> coordinates;

    public LiveCells(Set<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public Set<Coordinate> getCoordinates() {
        return coordinates;
    }

    public LiveCells filteredByX(int x) {
        return new LiveCells(getCoordinatesEqualToThemselvesModifiedByModifierGivenValue(x, Coordinate::withX));
    }

    public LiveCells filteredByY(int y) {
        return new LiveCells(getCoordinatesEqualToThemselvesModifiedByModifierGivenValue(y, Coordinate::withY));
    }

    @Override
    public String toString() {
        return "LiveCells{" +
                "coordinates=" + coordinates +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof LiveCells))
            return false;

        LiveCells liveCells = (LiveCells) object;

        return Objects.equals(coordinates, liveCells.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    private Set<Coordinate> getCoordinatesEqualToThemselvesModifiedByModifierGivenValue(int value, BiFunction<Coordinate, Integer, Coordinate> coordinateModifier) {
        return coordinates.stream()
                .filter(coordinate -> coordinate.equals(coordinateModifier.apply(coordinate, value)))
                .collect(Collectors.toSet());
    }
}
