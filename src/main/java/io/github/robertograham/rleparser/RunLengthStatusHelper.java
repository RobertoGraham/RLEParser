package io.github.robertograham.rleparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RunLengthStatusHelper {

    public static RunLengthStatus fromEncoded(String encodedRunLengthStatus, int columnIndex, int rowIndex) {
        Matcher matcher = Pattern.compile("\\d+|\\D").matcher(encodedRunLengthStatus);

        Integer runLength = null;
        CellStatus cellStatus = null;

        int i = 0;

        while (matcher.find()) {
            switch (i++) {
                case 0:
                    String match = matcher.group();
                    try {
                        runLength = Integer.parseInt(match);
                    } catch (NumberFormatException nfe) {
                        cellStatus = CellStatus.fromCode(match);
                    }
                    break;
                case 1:
                    if (runLength != null) {
                        cellStatus = CellStatus.fromCode(matcher.group());
                        break;
                    } else
                        throw new IllegalArgumentException("Too many matches");
                default:
                    throw new IllegalArgumentException("Encoded run length status did not match \\d+\\D");
            }
        }

        if (cellStatus == null)
            throw new IllegalArgumentException("Cell status code was not " + CellStatus.ALIVE.getCode() + " or " + CellStatus.DEAD.getCode());

        return new RunLengthStatus(runLength == null ? 1 : runLength, cellStatus, columnIndex, rowIndex);
    }
}
