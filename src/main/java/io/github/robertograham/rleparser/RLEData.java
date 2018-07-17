package io.github.robertograham.rleparser;

import java.util.Objects;

public class RLEData {

    private final RLEHeader rleHeader;
    private final RLELiveCellCoordinates rleLiveCellCoordinates;

    public RLEData(RLEHeader rleHeader, RLELiveCellCoordinates rleLiveCellCoordinates) {
        this.rleHeader = rleHeader;
        this.rleLiveCellCoordinates = rleLiveCellCoordinates;
    }

    public RLEHeader getRleHeader() {
        return rleHeader;
    }

    public RLELiveCellCoordinates getRleLiveCellCoordinates() {
        return rleLiveCellCoordinates;
    }

    @Override
    public String toString() {
        return "RLEData{" +
                "rleHeader=" + rleHeader +
                ", rleLiveCellCoordinates=" + rleLiveCellCoordinates +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof RLEData))
            return false;

        RLEData rleData = (RLEData) object;

        return Objects.equals(rleHeader, rleData.rleHeader) &&
                Objects.equals(rleLiveCellCoordinates, rleData.rleLiveCellCoordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rleHeader, rleLiveCellCoordinates);
    }
}
