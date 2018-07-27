package io.montanus.shelves.ui.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.stream.Stream;

public class ProcessTextPresenterTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();
    private TextCommandInterpreter interpreter;
    private TextSanitizer sanitizer;
    private TextProcessor processor;

    @Before
    public void setUp() {
        interpreter = context.mock(TextCommandInterpreter.class);
        sanitizer = context.mock(TextSanitizer.class);
        processor = new TextProcessor(sanitizer, interpreter);
    }

    @Test
    public void zeroLines() {
        final Stream<String> lines = Stream.empty();

        context.checking(new Expectations() {{
            allowing(sanitizer).sanitize(lines);
            will(returnValue(Stream.empty()));

            never(interpreter);
        }});

        processor.process(lines);
    }

    @Test
    public void oneLine() {
        final Stream<String> lines = Stream.of("12345");

        context.checking(new Expectations() {{
            allowing(sanitizer).sanitize(lines);
            will(returnValue(Stream.of("12345")));

            oneOf(interpreter).interpret(with("12345"));
        }});

        processor.process(lines);
    }

    @Test
    public void severalLinesAllSane() {
        final Stream<String> lines = Stream.of("12345", "23456", "99999");

        context.checking(new Expectations() {{
            allowing(sanitizer).sanitize(lines);
            will(returnValue(Stream.of("12345", "23456", "99999")));

            oneOf(interpreter).interpret("12345");
            oneOf(interpreter).interpret("23456");
            oneOf(interpreter).interpret("99999");
        }});

        processor.process(lines);
    }

    @Test
    public void severalLinesSomeInsane() {
        final Stream<String> lines = Stream.of("", "12345", "\t   ", "", "23456", " ", "99999", "\t\t");

        context.checking(new Expectations() {{
            allowing(sanitizer).sanitize(lines);
            will(returnValue(Stream.of("12345", "23456", "99999")));

            oneOf(interpreter).interpret("12345");
            oneOf(interpreter).interpret("23456");
            oneOf(interpreter).interpret("99999");
        }});

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
            sanitizer.sanitize(lines).forEach((line) -> interpreter.interpret(line));
        }
    }
}
