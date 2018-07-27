package io.montanus.shelves.ui.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class SanitizeLinesTest {
    private LineValidator validator;
    private ValidatorBackedSanitizer sanitizer;

    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Before
    public void setUp() throws Exception {
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

    private interface LineValidator {
        boolean isValid(String line);
    }

    private static class ValidatorBackedSanitizer {
        private final LineValidator validator;

        private ValidatorBackedSanitizer(LineValidator validator) {
            this.validator = validator;
        }

        private Stream<String> sanitize(Stream<String> lines) {
            final Optional<String> maybeALine = lines.findFirst();
            if (!maybeALine.isPresent()) {
                return Stream.empty();
            } else {
                final String line = maybeALine.get();
                if (validator.isValid(line))
                    return Stream.of(line);
                else
                    return Stream.empty();
            }
        }
    }
}
