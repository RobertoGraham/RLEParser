package io.github.robertograham.rleparser;

import io.github.robertograham.rleparser.domain.StatusRun;
import io.github.robertograham.rleparser.domain.enumeration.Status;
import io.github.robertograham.rleparser.domain.export.Coordinate;
import io.github.robertograham.rleparser.domain.export.MetaData;
import io.github.robertograham.rleparser.domain.export.PatternData;
import io.github.robertograham.rleparser.helper.FileHelper;
import io.github.robertograham.rleparser.helper.RleFileHelper;
import io.github.robertograham.rleparser.helper.StatusRunHelper;

import java.net.URI;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RleParser {

    private static final String META_DATA_WIDTH_KEY = "x";
    private static final String META_DATA_HEIGHT_KEY = "y";
    private static final String META_DATA_RULE_KEY = "rule";
    private static final String ENCODED_CELL_DATA_TERMINATOR = "!";
    private static final Pattern ENCODED_STATUS_RUN_PATTERN = Pattern.compile(String.format("\\d*[%s]", StatusRunHelper.STATUS_CODES));

    public static PatternData readPatternData(URI rleFileUri) {
        var lines = FileHelper.getFileAsStringList(rleFileUri);

        var trimmedNonEmptyNonCommentLines = lines.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(string -> !string.isEmpty())
                .filter(string -> !RleFileHelper.isComment(string))
                .collect(Collectors.toList());

        if (trimmedNonEmptyNonCommentLines.size() == 0)
            throw new IllegalArgumentException("No non-comment, non-empty lines in RLE file");

        var metaData = readMetaData(trimmedNonEmptyNonCommentLines.get(0));
        var coordinates = readCoordinates(
                metaData,
                trimmedNonEmptyNonCommentLines.stream()
                        .skip(1)
                        .collect(Collectors.joining())
        );

        return new PatternData(metaData, coordinates);
    }

    private static MetaData readMetaData(String line) {
        var lineWithoutWhiteSpace = line.replaceAll("\\s", "");
        var metaDataProperties = lineWithoutWhiteSpace.split(",");

        if (metaDataProperties.length < 2)
            throw new IllegalArgumentException("RLE file header has less than two properties");

        var metaDataPropertyKeyValueMap = Arrays.stream(metaDataProperties)
                .map(metaDataProperty -> metaDataProperty.split("="))
                .map(metaDataPropertyKeyValueArray -> {
                    if (metaDataPropertyKeyValueArray.length < 2)
                        return new String[]{metaDataPropertyKeyValueArray[0], null};
                    else
                        return metaDataPropertyKeyValueArray;
                })
                .collect(Collectors.toMap(metaDataPropertyKeyValueArray -> metaDataPropertyKeyValueArray[0], metaDataPropertyKeyValueArray -> metaDataPropertyKeyValueArray[1]));

        var width = metaDataPropertyKeyValueMap.get(META_DATA_WIDTH_KEY);
        var height = metaDataPropertyKeyValueMap.get(META_DATA_HEIGHT_KEY);
        var rule = metaDataPropertyKeyValueMap.get(META_DATA_RULE_KEY);

        return new MetaData(Integer.parseInt(width), Integer.parseInt(height), rule);
    }

    private static Set<Coordinate> readCoordinates(MetaData metaData, String rleCellData) {
        if (metaData.width() == 0 && metaData.height() == 0)
            if (rleCellData.length() > 0)
                throw new IllegalArgumentException("RLE header has width 0 and height 0 but there are lines after it");
            else
                return new HashSet<>();
        else if (rleCellData.length() == 0)
            throw new IllegalArgumentException("RLE header has width > 0 and height > 0 but there are no lines after it");
        else if (!rleCellData.contains(ENCODED_CELL_DATA_TERMINATOR))
            throw new IllegalArgumentException("RLE pattern did not contain terminating character '!'");
        else {
            var encodedCellData = rleCellData.substring(0, rleCellData.indexOf(ENCODED_CELL_DATA_TERMINATOR));
            var matcher = ENCODED_STATUS_RUN_PATTERN.matcher(encodedCellData);
            var statusRuns = new ArrayList<StatusRun>();
            var coordinate = new Coordinate(0, 0);

            while (matcher.find()) {
                var statusRun = StatusRunHelper.readStatusRun(matcher.group(), coordinate);

                if (Status.LINE_END.equals(statusRun.status()))
                    coordinate = new Coordinate(0, coordinate.y() + statusRun.length());
                else {
                    coordinate = coordinate.plusToX(statusRun.length());

                    if (Status.ALIVE.equals(statusRun.status()))
                        statusRuns.add(statusRun);
                }
            }

            return statusRuns.stream()
                    .map(StatusRunHelper::readCoordinates)
                    .reduce(new HashSet<>(), (coordinateAccumulator, coordinateSet) -> {
                        coordinateAccumulator.addAll(coordinateSet);
                        return coordinateAccumulator;
                    });
        }
    }
}
