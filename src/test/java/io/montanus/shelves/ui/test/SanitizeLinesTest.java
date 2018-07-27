package io.montanus.shelves.ui.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Iterator;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class SanitizeLinesTest {
    private LineValidator validator;
    private ValidatorBackedSanitizer sanitizer;

    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Before
    public void setUp() {
        validator = context.mock(LineValidator.class);
        sanitizer = new ValidatorBackedSanitizer(validator);
    }

    @Test
    public void zeroLines() {
        context.checking(new Expectations() {{
            never(validator);
        }});

        Stream<String> sanitizedLines = sanitizer.sanitize(Stream.empty());
        assertEquals(0, sanitizedLines.count());
    }

    @Test
    public void oneValidLine() {
        context.checking(new Expectations() {{
            oneOf(validator).isValid("::valid line::");
            will(returnValue(true));
        }});

        Stream<String> sanitizedLines = sanitizer.sanitize(Stream.of("::valid line::"));
        assertEquals("::valid line::", sanitizedLines.findFirst().orElse(""));
    }

    @Test
    public void oneInvalidLine() {
        context.checking(new Expectations() {{
            oneOf(validator).isValid("::invalid line::");
            will(returnValue(false));
        }});

        Stream<String> sanitizedLines = sanitizer.sanitize(Stream.of("::invalid line::"));
        assertEquals(0, sanitizedLines.count());
    }

    @Test
    public void severalLines() {
        context.checking(new Expectations() {{
            oneOf(validator).isValid("::valid line 1::");
            will(returnValue(true));
            oneOf(validator).isValid("::invalid line 1::");
            will(returnValue(false));
            oneOf(validator).isValid("::invalid line 2::");
            will(returnValue(false));
            oneOf(validator).isValid("::valid line 2::");
            will(returnValue(true));
            oneOf(validator).isValid("::valid line 3::");
            will(returnValue(true));
            oneOf(validator).isValid("::invalid line 3::");
            will(returnValue(false));
        }});

        Stream<String> sanitizedLines =
                sanitizer.sanitize(Stream.of(
                        "::valid line 1::",
                        "::invalid line 1::",
                        "::invalid line 2::",
                        "::valid line 2::",
                        "::valid line 3::",
                        "::invalid line 3::"));

        assertStreamEquals(Stream.of("::valid line 1::", "::valid line 2::", "::valid line 3::"), sanitizedLines);
    }

    private void assertStreamEquals(Stream<?> s1, Stream<?> s2) {
        Iterator<?> iter1 = s1.iterator(), iter2 = s2.iterator();
        while (iter1.hasNext() && iter2.hasNext())
            assertEquals(iter1.next(), iter2.next());
        assert !iter1.hasNext() && !iter2.hasNext();
    }

    private interface LineValidator {
        boolean isValid(String line);
    }

    private static class ValidatorBackedSanitizer {
        private final LineValidator validator;

        private ValidatorBackedSanitizer(LineValidator validator) {
            this.validator = validator;
        }

        private Stream<String> sanitize(Stream<String> lines) {
            return lines.filter((line) -> validator.isValid(line));
        }
    }
}
