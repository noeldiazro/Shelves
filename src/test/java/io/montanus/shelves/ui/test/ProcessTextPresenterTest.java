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
        final Stream<String> lines = Stream.of("::isbn1::");

        context.checking(new Expectations() {{
            allowing(sanitizer).sanitize(lines);
            will(returnValue(Stream.of("::isbn1::")));

            oneOf(interpreter).interpret(with("::isbn1::"));
        }});

        processor.process(lines);
    }

    @Test
    public void severalLinesAllSane() {
        final Stream<String> lines = Stream.of("::isbn1::", "::isbn2::", "::isbn3::");

        context.checking(new Expectations() {{
            allowing(sanitizer).sanitize(lines);
            will(returnValue(Stream.of("::isbn1::", "::isbn2::", "::isbn3::")));

            oneOf(interpreter).interpret("::isbn1::");
            oneOf(interpreter).interpret("::isbn2::");
            oneOf(interpreter).interpret("::isbn3::");
        }});

        processor.process(lines);
    }

    @Test
    public void severalLinesSomeInsane() {
        final Stream<String> lines = Stream.of("", "::isbn1::", "\t   ", "", "::isbn2::", " ", "::isbn3::", "\t\t");

        context.checking(new Expectations() {{
            allowing(sanitizer).sanitize(lines);
            will(returnValue(Stream.of("::isbn1::", "::isbn2::", "::isbn3::")));

            oneOf(interpreter).interpret("::isbn1::");
            oneOf(interpreter).interpret("::isbn2::");
            oneOf(interpreter).interpret("::isbn3::");
        }});

        processor.process(lines);
    }

    private interface TextCommandInterpreter {
        void interpret(String textCommand);
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
