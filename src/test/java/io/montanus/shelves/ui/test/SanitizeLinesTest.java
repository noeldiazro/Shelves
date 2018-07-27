package io.montanus.shelves.ui.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class SanitizeLinesTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void zeroLines() {
        LineValidator validator = context.mock(LineValidator.class);

        context.checking(new Expectations() {{
            never(validator);
        }});

        ValidatorBackedSanitizer sanitizer = new ValidatorBackedSanitizer(validator);
        Stream<String> sanitizedLines = sanitizer.sanitize(Stream.empty());
        assertEquals(0, sanitizedLines.count());
    }

    @Test
    public void oneValidLine() {
        Stream<String> lines = Stream.of("::valid line::");
        LineValidator validator = context.mock(LineValidator.class);

        context.checking(new Expectations() {{
            oneOf(validator).isValid("::valid line::");
            will(returnValue(true));
        }});

        ValidatorBackedSanitizer sanitizer = new ValidatorBackedSanitizer(validator);
        Stream<String> sanitizedLines = sanitizer.sanitize(lines);
        assertEquals("::valid line::", sanitizedLines.findFirst().orElse(""));
    }

    @Test
    public void oneInvalidLine() {
        Stream<String> lines = Stream.of("::invalid line::");
        LineValidator validator = context.mock(LineValidator.class);

        context.checking(new Expectations() {{
            oneOf(validator).isValid("::invalid line::");
            will(returnValue(false));
        }});

        ValidatorBackedSanitizer sanitizer = new ValidatorBackedSanitizer(validator);
        Stream<String> sanitizedLines = sanitizer.sanitize(lines);
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
