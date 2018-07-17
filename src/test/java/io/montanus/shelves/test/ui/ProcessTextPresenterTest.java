package io.montanus.shelves.test.ui;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.stream.Stream;

public class ProcessTextPresenterTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void zeroLines() {
        final TextCommandInterpreter interpreter = context.mock(TextCommandInterpreter.class);
        final TextSanitizer sanitizer = context.mock(TextSanitizer.class);
        final Stream<String> lines = Stream.empty();

        context.checking(new Expectations() {{
            allowing(sanitizer).sanitize(lines);
            will(returnValue(Stream.empty()));

            never(interpreter);
        }});

        final TextProcessor processor = new TextProcessor(sanitizer, interpreter);
        processor.process(lines);
    }

    @Test
    public void oneLine() {
        final TextCommandInterpreter interpreter = context.mock(TextCommandInterpreter.class);
        final TextSanitizer sanitizer = context.mock(TextSanitizer.class);
        final Stream<String> lines = Stream.of("12345");

        context.checking(new Expectations() {{
            allowing(sanitizer).sanitize(lines);
            will(returnValue(Stream.of("12345")));

            oneOf(interpreter).interpret(with("12345"));
        }});

        final TextProcessor processor = new TextProcessor(sanitizer, interpreter);
        processor.process(lines);
    }

    @Test
    public void severalLinesAllSane() {
        final TextCommandInterpreter interpreter = context.mock(TextCommandInterpreter.class);
        final TextSanitizer sanitizer = context.mock(TextSanitizer.class);
        final Stream<String> lines = Stream.of("12345", "23456", "99999");

        context.checking(new Expectations() {{
            allowing(sanitizer).sanitize(lines);
            will(returnValue(Stream.of("12345", "23456", "99999")));

            oneOf(interpreter).interpret("12345");
            oneOf(interpreter).interpret("23456");
            oneOf(interpreter).interpret("99999");
        }});

        final TextProcessor processor = new TextProcessor(sanitizer, interpreter);
        processor.process(lines);
    }

    private interface TextCommandInterpreter {
        void interpret(String textCommand);
    }

    private interface TextSanitizer {
        Stream<String> sanitize(Stream<String> lines);
    }

    private static class TextProcessor {

        private final TextSanitizer sanitizer;
        private final TextCommandInterpreter interpreter;

        private TextProcessor(TextSanitizer sanitizer, TextCommandInterpreter interpreter) {

            this.sanitizer = sanitizer;
            this.interpreter = interpreter;
        }

        private void process(Reader reader) {
            process(new BufferedReader(reader).lines());
        }

        private void process(Stream<String> lines) {
            lines.forEach((line) -> interpreter.interpret(line));
        }
    }
}
