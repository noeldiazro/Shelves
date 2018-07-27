package io.montanus.shelves.ui.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RejectBlankLinesTest {

    private LineValidator validator;

    @Before
    public void setUp() {
        validator = new BlankLineValidator();
    }

    @Test
    public void rejectsEmptyLine() {
        assertEquals(false, validator.isValid(""));
    }

    @Test
    public void acceptsOneNonBlankCharacterLine() {
        assertEquals(true, validator.isValid("a"));
    }

    @Test
    public void rejectsAnSpace() {
        assertEquals(false, validator.isValid(" "));
    }

    @Test
    public void rejectsTab() {
        assertEquals(false, validator.isValid("\t"));
    }

    @Test
    public void rejectsMixOfSpaces() {
        assertEquals(false, validator.isValid(" \t\t    "));
    }

    @Test
    public void acceptsLineWithInterspededSpaces() {
        assertEquals(true, validator.isValid("   a\tb\t c   "));
    }

    private static class BlankLineValidator implements LineValidator {
        @Override
        public boolean isValid(String line) {
            return !"".equals(line.trim());
        }
    }
}
