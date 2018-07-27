package io.montanus.shelves.ui.test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RejectBlankLinesTest {
    @Test
    public void rejectsEmptyLine() {
        final LineValidator validator = new BlankLineValidator();
        assertEquals(false, validator.isValid(""));
    }

    @Test
    public void acceptsOneNonBlankCharacterLine() {
        final LineValidator validator = new BlankLineValidator();
        assertEquals(true, validator.isValid("a"));
    }

    private static class BlankLineValidator implements LineValidator {
        @Override
        public boolean isValid(String line) {
            return !"".equals(line);
        }
    }
}
