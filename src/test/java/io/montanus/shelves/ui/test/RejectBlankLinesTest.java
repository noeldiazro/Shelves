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

    private static class BlankLineValidator implements LineValidator {
        @Override
        public boolean isValid(String line) {
            return !"".equals(line);
        }
    }
}
