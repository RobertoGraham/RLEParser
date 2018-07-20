package io.github.robertograham.rleparser;

import java.net.URISyntaxException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        Pattern pattern = RleParser.parseRLEFile(Main.class.getClassLoader().getResource("gosperglidergun.rle").toURI());

        System.out.println(IntStream.range(0, pattern.getMetaData().getHeight())
                .mapToObj(pattern.getLiveCells()::filteredByY)
                .map(filteredByY -> IntStream.range(0, pattern.getMetaData().getWidth())
                        .mapToObj(filteredByY::filteredByX)
                        .map(LiveCells::getCoordinates)
                        .map(coordinates -> coordinates.size() == 0 ? "." : "@")
                        .collect(Collectors.joining()))
                .collect(Collectors.joining("\n")));
    }
}
