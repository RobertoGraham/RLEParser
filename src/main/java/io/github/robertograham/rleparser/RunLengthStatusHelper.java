package io.github.robertograham.rleparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RunLengthStatusHelper {

    static RunLengthStatus fromEncoded(String encodedRunLengthStatus, int columnIndex, int rowIndex) {
        String cellStatusCode;
        Integer runLength;
        Matcher matcher = Pattern.compile("(\\d*)(\\D)").matcher(encodedRunLengthStatus);
        while (!matcher.find()) ;
        runLength = matcher.group(1).isEmpty() ? 1 : Integer.parseInt(matcher.group(1));
        cellStatusCode = matcher.group(2);

        return new RunLengthStatus(runLength, CellStatus.fromCode(cellStatusCode), columnIndex, rowIndex);
    }
}
