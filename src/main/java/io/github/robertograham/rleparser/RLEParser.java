package io.github.robertograham.rleparser;

import org.intellij.lang.annotations.Language;

import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RLEParser {

    private static final String RLE_HEADER_WIDTH_KEY = "x";
    private static final String RLE_HEADER_HEIGHT_KEY = "y";
    private static final String RLE_HEADER_RULE_KEY = "rule";
    private static final String RLE_CELL_DATA_TERMINATOR = "!";
    @Language("RegExp")
    private static final String RLE_CELL_DATA_LINE_SEPARATOR_REGEX = "\\$";
    @Language("RegExp")
    private static final String ENCODED_RUN_LENGTH_STATUS_REGEX = "\\d*\\D";
    private static final Pattern ENCODED_RUN_LENGTH_STATUS_PATTERN = Pattern.compile(ENCODED_RUN_LENGTH_STATUS_REGEX);

    public static RLEData parseRLEFile(URI rleFileUri) {
        List<String> rleLines = FileHelper.getFileAsStringList(rleFileUri);

        List<String> trimmedNonEmptyNonCommentRleLines = rleLines.stream()
                .map(String::trim)
                .filter(RLEParser::isRleLineNotEmpty)
                .filter(RLEParser::isRleLineNotAComment)
                .collect(Collectors.toList());

        if (trimmedNonEmptyNonCommentRleLines.size() == 0)
            throw new IllegalArgumentException("No non-comment, non-empty lines in RLE file");

        RLEHeader rleHeader = extractRleHeader(trimmedNonEmptyNonCommentRleLines.get(0));
        RLELiveCellCoordinates rleLiveCellCoordinates = extractRleLiveCellCoordinates(
                rleHeader,
                trimmedNonEmptyNonCommentRleLines.stream()
                        .skip(1)
                        .collect(Collectors.joining())
        );

        return new RLEData(rleHeader, rleLiveCellCoordinates);
    }

    private static boolean isRleLineNotAComment(String rleFileLine) {
        return !rleFileLine.startsWith("#");
    }

    private static boolean isRleLineNotEmpty(String rleFileLine) {
        return !rleFileLine.isEmpty();
    }

    private static boolean isRunLengthStatusAlive(RunLengthStatus runLengthStatus) {
        return CellStatus.ALIVE.equals(runLengthStatus.getStatus());
    }

    private static RLEHeader extractRleHeader(String rleLine) {
        String rleFileWithNoWhiteSpace = rleLine.replaceAll("\\s", "");
        String[] rleFileHeaderProperties = rleFileWithNoWhiteSpace.split(",");

        if (rleFileHeaderProperties.length < 2)
            throw new IllegalArgumentException("RLE file header has less than two properties");

        Map<String, String> rleFileHeaderPropertyKeyToPropertyValueMap = Arrays.stream(rleFileHeaderProperties)
                .map(rleFileHeaderProperty -> rleFileHeaderProperty.split("="))
                .map(rleFileHeaderPropertyComponents -> {
                    if (rleFileHeaderPropertyComponents.length < 2)
                        return new String[]{rleFileHeaderPropertyComponents[0], null};
                    else
                        return rleFileHeaderPropertyComponents;
                })
                .collect(Collectors.toMap(rleFileHeaderPropertyComponents -> rleFileHeaderPropertyComponents[0], rleFileHeaderPropertyComponents -> rleFileHeaderPropertyComponents[1]));

        String width = rleFileHeaderPropertyKeyToPropertyValueMap.get(RLE_HEADER_WIDTH_KEY);
        String height = rleFileHeaderPropertyKeyToPropertyValueMap.get(RLE_HEADER_HEIGHT_KEY);
        String rule = rleFileHeaderPropertyKeyToPropertyValueMap.get(RLE_HEADER_RULE_KEY);

        return new RLEHeader(Integer.parseInt(width), Integer.parseInt(height), rule);
    }

    private static RLELiveCellCoordinates extractRleLiveCellCoordinates(RLEHeader rleHeader, String rleCellData) {
        if (rleHeader.getX() == 0 && rleHeader.getY() == 0)
            if (rleCellData.length() > 0)
                throw new IllegalArgumentException("RLE header has width 0 and height 0 but there are lines after it");
            else
                return new RLELiveCellCoordinates(new HashSet<>());
        else if (rleCellData.length() == 0)
            throw new IllegalArgumentException("RLE header has width > 0 and height > 0 but there are no lines after it");
        else if (!rleCellData.contains(RLE_CELL_DATA_TERMINATOR))
            throw new IllegalArgumentException("RLE pattern did not contain terminating character '!'");
        else {
            String patternData = rleCellData.substring(0, rleCellData.indexOf(RLE_CELL_DATA_TERMINATOR));
            String[] cellDataLines = patternData.split(RLE_CELL_DATA_LINE_SEPARATOR_REGEX);

            Set<Coordinate> coordinates = IntStream.range(0, cellDataLines.length)
                    .boxed()
                    .collect(Collectors.toMap(Function.identity(), i -> cellDataLines[i]))
                    .entrySet()
                    .stream()
                    .map(cellRowIndexToCellRowEntry -> {
                        List<RunLengthStatus> runLengthStatuses = new ArrayList<>();
                        Matcher matcher = ENCODED_RUN_LENGTH_STATUS_PATTERN.matcher(cellRowIndexToCellRowEntry.getValue());

                        int columnIndex = 0;

                        while (matcher.find()) {
                            runLengthStatuses.add(RunLengthStatusHelper.fromEncoded(matcher.group(), columnIndex, cellRowIndexToCellRowEntry.getKey()));
                            columnIndex += runLengthStatuses.get(runLengthStatuses.size() - 1).getHorizontalRunLength();
                        }

                        return runLengthStatuses;
                    })
                    .flatMap(List::stream)
                    .filter(RLEParser::isRunLengthStatusAlive)
                    .map(runLengthStatus -> IntStream.range(0, runLengthStatus.getHorizontalRunLength())
                            .mapToObj(xMinusColumnIndex -> new Coordinate(xMinusColumnIndex + runLengthStatus.getColumnIndex(), runLengthStatus.getRowIndex()))
                            .collect(Collectors.toList()))
                    .flatMap(List::stream)
                    .collect(Collectors.toSet());

            return new RLELiveCellCoordinates(coordinates);
        }
    }
}
