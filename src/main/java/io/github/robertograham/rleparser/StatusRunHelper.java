package io.github.robertograham.rleparser;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.robertograham.rleparser.Status.ALIVE;

class StatusRunHelper {

    static StatusRun fromEncodedStatusRun(String encodedStatusRun, Coordinate coordinate) {
        String statusCode;
        Integer length;

        Matcher matcher = Pattern.compile("(\\d*)(\\D)").matcher(encodedStatusRun);

        if (matcher.find()) {
            length = matcher.group(1).isEmpty() ? 1 : Integer.parseInt(matcher.group(1));
            statusCode = matcher.group(2);

            return new StatusRun(length, Status.fromCode(statusCode), coordinate);
        }

        throw new IllegalArgumentException("Encoded run length status did not match (\\d*)(\\D)");
    }

    static Set<Coordinate> getLiveCoordinates(StatusRun statusRun) {
        return ALIVE == statusRun.getStatus() ?
                IntStream.range(statusRun.getCoordinate().getX(), statusRun.getCoordinate().getX() + statusRun.getLength())
                        .mapToObj(statusRun.getCoordinate()::withX)
                        .collect(Collectors.toSet())
                : new HashSet<>();
    }
}
