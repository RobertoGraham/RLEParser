package io.github.robertograham.rleparser;

import java.net.URISyntaxException;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        RLEData rleData = RLEParser.parseRLEFile(Main.class.getClassLoader().getResource("glider.rle").toURI());

        for (int y = 0; y < rleData.getRleHeader().getY(); y++) {
            for (int x = 0; x < rleData.getRleHeader().getX(); x++) {
                int finalX = x;
                int finalY = y;

                Coordinate coordinate = rleData.getRleLiveCellCoordinates().getCoordinates().stream()
                        .filter(c -> finalX == c.getX() && finalY == c.getY())
                        .findFirst()
                        .orElse(null);

                System.out.print(coordinate == null ? "." : "@");
            }
            System.out.println();
        }

        IntStream.range(0, 0).forEach(System.out::println);
    }
}
