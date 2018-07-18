package io.github.robertograham.rleparser;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        RLEData rleData = RLEParser.parseRLEFile(Main.class.getClassLoader().getResource("period14glidergun.rle").toURI());

        System.err.println("Finished");

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
    }
}
