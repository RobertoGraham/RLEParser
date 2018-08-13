[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.robertograham/rle-parser-module/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.robertograham/rle-parser-module)
# RLEParser
Reads Game of Life .rle files (JDK 10 Required)

## Install
```xml
<dependency>
    <groupId>io.github.robertograham</groupId>
    <artifactId>rle-parser-module</artifactId>
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
import io.github.robertograham.rleparser.domain.export.PatternData;

import java.net.URISyntaxException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        PatternData patternData = RleParser.readPatternData(Main.class.getResource("glider.rle").toURI());

        System.out.print(IntStream.range(0, patternData.metaData().height())
                .mapToObj(y ->
                        patternData.coordinates().stream()
                                .filter(coordinate -> y == coordinate.y())
                                .collect(Collectors.toSet())
                )
                .map(coordinatesFilteredByY ->
                        IntStream.range(0, patternData.metaData().width())
                                .mapToObj(x ->
                                        coordinatesFilteredByY.stream()
                                                .filter(coordinate -> x == coordinate.x())
                                                .collect(Collectors.toSet())
                                )
                                .map(coordinatesFilteredByYAndX -> coordinatesFilteredByYAndX.size() == 0 ? "." : "@")
                                .collect(Collectors.joining())
                )
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
