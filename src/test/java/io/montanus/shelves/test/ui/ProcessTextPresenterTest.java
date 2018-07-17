package io.montanus.shelves.test.ui;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

public class ProcessTextPresenterTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void zeroLines() {
        final TextCommandInterpreter interpreter = context.mock(TextCommandInterpreter.class);
        final TextSanitizer sanitizer = context.mock(TextSanitizer.class);
        context.checking(new Expectations() {{
            never(interpreter);
        }});
        final TextProcessor processor = new TextProcessor(sanitizer, interpreter);
        processor.process(new StringReader(""));
    }

    private interface TextCommandInterpreter {}

    private interface TextSanitizer {}

    private static class TextProcessor {

        private TextProcessor(TextSanitizer sanitizer, TextCommandInterpreter interpreter) {

        }

        private void process(Reader reader) {

        }
    }
}
