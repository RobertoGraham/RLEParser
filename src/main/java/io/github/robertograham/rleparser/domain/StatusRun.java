package io.github.robertograham.rleparser.domain;

import io.github.robertograham.rleparser.domain.enumeration.Status;
import io.github.robertograham.rleparser.domain.export.Coordinate;

import java.util.Objects;

public final class StatusRun {

    private final int length;
    private final Status status;
    private final Coordinate origin;

    public StatusRun(int length, Status status, Coordinate origin) {
        this.length = length;
        this.status = status;
        this.origin = origin;
    }

    public int length() {
        return length;
    }

    public Status status() {
        return status;
    }

    public Coordinate origin() {
        return origin;
    }

    @Override
    public String toString() {
        return "StatusRun{" +
                "length=" + length +
                ", status=" + status +
                ", origin=" + origin +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof StatusRun))
            return false;

        var statusRun = (StatusRun) object;

        return length == statusRun.length &&
                status == statusRun.status &&
                Objects.equals(origin, statusRun.origin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, status, origin);
    }
}
