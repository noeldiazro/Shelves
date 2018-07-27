package io.montanus.shelves.ui;

import java.util.stream.Stream;

public interface TextSanitizer {
    Stream<String> sanitize(Stream<String> lines);
}
