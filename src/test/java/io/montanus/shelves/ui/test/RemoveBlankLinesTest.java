package io.montanus.shelves.ui.test;

import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class RemoveBlankLinesTest {

    private BlankLineSanitizer sanitizer;

    @Before
    public void setUp() throws Exception {
        sanitizer = new BlankLineSanitizer();
    }

    @Test
    public void zeroLines() {
        final Stream<String> sanitizedLines = sanitizer.sanitize(Stream.empty());
        assertEquals(0, sanitizedLines.count());
    }

    @Test
    public void oneNonBlankLine() {
        final Stream<String> sanitizedLines = sanitizer.sanitize(Stream.of("::line1::"));
        assertEquals("::line1::", sanitizedLines.findFirst().orElse(""));
    }

    private static class BlankLineSanitizer {
        private Stream<String> sanitize(Stream<String> lines) {
            return lines;
        }
    }
}
