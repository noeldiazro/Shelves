package io.montanus.shelves.ui.test;

import java.util.stream.Stream;

public interface TextSanitizer {
    Stream<String> sanitize(Stream<String> lines);
}
