package io.github.robertograham.rleparser;

import java.util.Objects;

public class RunLengthStatus {

    private final int horizontalRunLength;
    private final CellStatus status;
    private final int columnIndex;
    private final int rowIndex;

    public RunLengthStatus(int horizontalRunLength, CellStatus status, int columnIndex, int rowIndex) {
        this.horizontalRunLength = horizontalRunLength;
        this.status = status;
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
    }

    public int getHorizontalRunLength() {
        return horizontalRunLength;
    }

    public CellStatus getStatus() {
        return status;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    @Override
    public String toString() {
        return "RunLengthStatus{" +
                "horizontalRunLength=" + horizontalRunLength +
                ", status=" + status +
                ", columnIndex=" + columnIndex +
                ", rowIndex=" + rowIndex +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof RunLengthStatus))
            return false;

        RunLengthStatus runLengthStatus = (RunLengthStatus) object;

        return horizontalRunLength == runLengthStatus.horizontalRunLength &&
                columnIndex == runLengthStatus.columnIndex &&
                rowIndex == runLengthStatus.rowIndex &&
                status == runLengthStatus.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(horizontalRunLength, status, columnIndex, rowIndex);
    }
}
