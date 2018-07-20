package io.github.robertograham.rleparser;

import java.util.Objects;

public class StatusRun {

    private final int length;
    private final Status status;
    private final Coordinate coordinate;

    public StatusRun(int length, Status status, Coordinate coordinate) {
        this.length = length;
        this.status = status;
        this.coordinate = coordinate;
    }

    public int getLength() {
        return length;
    }

    public Status getStatus() {
        return status;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public String toString() {
        return "StatusRun{" +
                "length=" + length +
                ", status=" + status +
                ", coordinate=" + coordinate +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof StatusRun))
            return false;

        StatusRun statusRun = (StatusRun) object;

        return length == statusRun.length &&
                status == statusRun.status &&
                Objects.equals(coordinate, statusRun.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, status, coordinate);
    }
}
