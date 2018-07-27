package io.montanus.shelves.ui;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.stream.Stream;

public class TextProcessor {

    private final TextSanitizer sanitizer;
    private final TextCommandInterpreter interpreter;

    public TextProcessor(TextSanitizer sanitizer, TextCommandInterpreter interpreter) {
        this.sanitizer = sanitizer;
        this.interpreter = interpreter;
    }

    public void process(Reader reader) {
        process(new BufferedReader(reader).lines());
    }

    public void process(Stream<String> lines) {
        sanitizer.sanitize(lines).forEach((line) -> interpreter.interpret(line));
    }
}
