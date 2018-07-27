package io.montanus.shelves.ui.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

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

    private interface LineValidator {
    }

    private static class ValidatorBackedSanitizer {
        private ValidatorBackedSanitizer(LineValidator validator) {

        }

        private Stream<String> sanitize(Stream<String> lines) {
            return Stream.empty();
        }
    }
}
