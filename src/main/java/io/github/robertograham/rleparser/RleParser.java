package io.github.robertograham.rleparser;

import org.intellij.lang.annotations.Language;

import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.robertograham.rleparser.Predicates.not;

public class RleParser {

    private static final String RLE_HEADER_WIDTH_KEY = "x";
    private static final String RLE_HEADER_HEIGHT_KEY = "y";
    private static final String RLE_HEADER_RULE_KEY = "rule";
    private static final String RLE_CELL_DATA_TERMINATOR = "!";
    @Language("RegExp")
    private static final String RLE_CELL_DATA_LINE_SEPARATOR_REGEX = "\\$";
    @Language("RegExp")
    private static final String ENCODED_RUN_LENGTH_STATUS_REGEX = "\\d*\\D";
    private static final java.util.regex.Pattern ENCODED_RUN_LENGTH_STATUS_PATTERN = java.util.regex.Pattern.compile(ENCODED_RUN_LENGTH_STATUS_REGEX);

    public static Pattern parseRLEFile(URI rleFileUri) {
        List<String> rleLines = FileHelper.getFileAsStringList(rleFileUri);

        List<String> trimmedNonEmptyNonCommentRleLines = rleLines.stream()
                .map(String::trim)
                .filter(not(String::isEmpty))
                .filter(not(RleLinePredicates::isComment))
                .collect(Collectors.toList());

        if (trimmedNonEmptyNonCommentRleLines.size() == 0)
            throw new IllegalArgumentException("No non-comment, non-empty lines in RLE file");

        MetaData metaData = extractRleHeader(trimmedNonEmptyNonCommentRleLines.get(0));
        LiveCells liveCells = extractRleLiveCellCoordinates(
                metaData,
                trimmedNonEmptyNonCommentRleLines.stream()
                        .skip(1)
                        .collect(Collectors.joining())
        );

        return new Pattern(metaData, liveCells);
    }

    private static MetaData extractRleHeader(String rleLine) {
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

        return new MetaData(Integer.parseInt(width), Integer.parseInt(height), rule);
    }

    private static LiveCells extractRleLiveCellCoordinates(MetaData metaData, String rleCellData) {
        if (metaData.getWidth() == 0 && metaData.getHeight() == 0)
            if (rleCellData.length() > 0)
                throw new IllegalArgumentException("RLE header has width 0 and height 0 but there are lines after it");
            else
                return new LiveCells(new HashSet<>());
        else if (rleCellData.length() == 0)
            throw new IllegalArgumentException("RLE header has width > 0 and height > 0 but there are no lines after it");
        else if (!rleCellData.contains(RLE_CELL_DATA_TERMINATOR))
            throw new IllegalArgumentException("RLE pattern did negate contain terminating character '!'");
        else {
            String patternData = rleCellData.substring(0, rleCellData.indexOf(RLE_CELL_DATA_TERMINATOR));
            String[] cellDataLines = patternData.split(RLE_CELL_DATA_LINE_SEPARATOR_REGEX);

            Set<Coordinate> coordinates = IntStream.range(0, cellDataLines.length)
                    .boxed()
                    .collect(Collectors.toMap(Function.identity(), i -> cellDataLines[i]))
                    .entrySet()
                    .stream()
                    .map(yToCellRowEntry -> {
                        List<StatusRun> statusRuns = new ArrayList<>();
                        Matcher matcher = ENCODED_RUN_LENGTH_STATUS_PATTERN.matcher(yToCellRowEntry.getValue());
                        Coordinate coordinate = new Coordinate(0, yToCellRowEntry.getKey());

                        while (matcher.find()) {
                            statusRuns.add(StatusRunHelper.fromEncodedStatusRun(matcher.group(), coordinate));
                            coordinate = coordinate.plusToX(statusRuns.get(statusRuns.size() - 1).getLength());
                        }

                        return statusRuns;
                    })
                    .flatMap(List::stream)
                    .map(StatusRunHelper::getLiveCoordinates)
                    .reduce(new HashSet<>(), (liveCoordinateSetAccumulator, liveCoordinateSet) -> {
                        liveCoordinateSetAccumulator.addAll(liveCoordinateSet);
                        return liveCoordinateSetAccumulator;
                    });

            return new LiveCells(coordinates);
        }
    }
}
