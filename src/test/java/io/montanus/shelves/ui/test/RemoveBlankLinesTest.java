package io.montanus.shelves.ui.test;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class RemoveBlankLinesTest {
    @Test
    public void zeroLines() {
        final BlankLineSanitizer sanitizer = new BlankLineSanitizer();
        final Stream<String> lines = Stream.empty();
        final Stream<String> sanitizedLines = sanitizer.sanitize(lines);
        assertEquals(0, sanitizedLines.count());
    }

    private static class BlankLineSanitizer {
        private Stream<String> sanitize(Stream<String> lines) {
            return Stream.empty();
        }
    }
}
