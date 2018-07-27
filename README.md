[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.robertograham/rle-parser/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.robertograham/rle-parser)
# RLEParser
Reads Game of Life .rle files

## Install
```xml
<dependency>
    <groupId>io.github.robertograham</groupId>
    <artifactId>rle-parser</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage
glider.rle
```
#C This is a glider.
x = 3, y = 3
bo$2bo$3o!
```
```java
import io.github.robertograham.rleparser.RleParser;
import io.github.robertograham.rleparser.domain.LiveCells;
import io.github.robertograham.rleparser.domain.PatternData;

import java.net.URISyntaxException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        PatternData patternData = RleParser.readPatternData(Main.class.getClassLoader().getResource("glider.rle").toURI());

        System.out.println(IntStream.range(0, patternData.getMetaData().getHeight())
                .mapToObj(patternData.getLiveCells()::filteredByY)
                .map(filteredByY -> IntStream.range(0, patternData.getMetaData().getWidth())
                        .mapToObj(filteredByY::filteredByX)
                        .map(LiveCells::getCoordinates)
                        .map(coordinates -> coordinates.size() == 0 ? "." : "@")
                        .collect(Collectors.joining()))
                .collect(Collectors.joining("\n")));
    }
}
```
Prints:
```
.@.
..@
@@@
```