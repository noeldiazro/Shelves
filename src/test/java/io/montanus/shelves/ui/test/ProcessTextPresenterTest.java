package io.montanus.shelves.ui.test;

import io.montanus.shelves.ui.TextCommandInterpreter;
import io.montanus.shelves.ui.TextProcessor;
import io.montanus.shelves.ui.TextSanitizer;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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

}
