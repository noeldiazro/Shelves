package io.montanus.shelves.ui;

public class BlankLineValidator implements LineValidator {
    @Override
    public boolean isValid(String line) {
        return !"".equals(line.trim());
    }
}
