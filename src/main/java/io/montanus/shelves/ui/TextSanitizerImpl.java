package io.montanus.shelves.ui;

import java.util.stream.Stream;

public class TextSanitizerImpl implements TextSanitizer {
    private final LineValidator validator;

    public TextSanitizerImpl(LineValidator validator) {
        this.validator = validator;
    }

    @Override
    public Stream<String> sanitize(Stream<String> lines) {
        return lines.filter((line) -> validator.isValid(line));
    }
}
