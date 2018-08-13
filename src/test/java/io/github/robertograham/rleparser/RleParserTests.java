package io.github.robertograham.rleparser;

import io.github.robertograham.rleparser.domain.export.PatternData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RleParserTests {

    @ParameterizedTest(name = "{0} illegal argument exception thrown when less than two header properties")
    @ValueSource(strings = {
            "src/test/resources/rle/lessThan2HeaderProperties.rle"
    })
    void lessThan2HeaderProperties(File file) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> RleParser.readPatternData(file.toURI()));
        assertEquals("RLE file header has less than two properties", illegalArgumentException.getMessage());
    }

    @ParameterizedTest(name = "{0} illegal argument exception thrown when no non-empty non-comment lines")
    @ValueSource(strings = {
            "src/test/resources/rle/noNonEmptyNonCommentLines.rle"
    })
    void noNonEmptyNonCommentLines(File file) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> RleParser.readPatternData(file.toURI()));
        assertEquals("No non-comment, non-empty lines in RLE file", illegalArgumentException.getMessage());
    }

    @ParameterizedTest(name = "{0} illegal argument exception thrown when width 0 and height 0 with lines after header")
    @ValueSource(strings = {
            "src/test/resources/rle/zeroWidthAndHeightWithLinesAfter.rle"
    })
    void zeroWidthAndHeightWithLinesAfter(File file) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> RleParser.readPatternData(file.toURI()));
        assertEquals("RLE header has width 0 and height 0 but there are lines after it", illegalArgumentException.getMessage());
    }

    @ParameterizedTest(name = "{0} width 0 and height 0 and empty coordinates")
    @ValueSource(strings = {
            "src/test/resources/rle/validZeroWidthAndHeight.rle"
    })
    void validZeroWidthAndHeight(File file) {
        PatternData patternData = RleParser.readPatternData(file.toURI());

        assertEquals(0, patternData.metaData().width(), "meta data width should be 0");
        assertEquals(0, patternData.metaData().height(), "meta data height should be 0");
        assertEquals(0, patternData.coordinates().size(), "coordinate count should be 0");
    }
}
