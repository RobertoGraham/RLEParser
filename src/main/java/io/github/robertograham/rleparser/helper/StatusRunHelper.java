package io.github.robertograham.rleparser.helper;

import io.github.robertograham.rleparser.domain.StatusRun;
import io.github.robertograham.rleparser.domain.enumeration.Status;
import io.github.robertograham.rleparser.domain.export.Coordinate;

import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StatusRunHelper {

    public static final String STATUS_CODES = EnumSet.allOf(Status.class).stream()
            .map(Status::getCode)
            .collect(Collectors.joining());

    private static final Pattern STATUS_RUN_PATTERN = Pattern.compile(String.format("^(\\d*)([%s])$", STATUS_CODES));

    public static StatusRun readStatusRun(String encodedStatusRun, Coordinate origin) {
        var matcher = STATUS_RUN_PATTERN.matcher(encodedStatusRun);

        if (matcher.find())
            return new StatusRun(
                    matcher.group(1).isEmpty() ?
                            1
                            : Integer.parseInt(matcher.group(1)),
                    Status.fromCode(matcher.group(2)),
                    origin
            );

        throw new IllegalArgumentException("Encoded run length status did not match " + STATUS_RUN_PATTERN.pattern());
    }

    public static Set<Coordinate> readCoordinates(StatusRun statusRun) {
        return IntStream.range(0, statusRun.length())
                .mapToObj(statusRun.origin()::plusToX)
                .collect(Collectors.toSet());
    }
}
