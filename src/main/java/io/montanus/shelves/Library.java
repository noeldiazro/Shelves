package io.montanus.shelves;

import io.montanus.shelves.ui.BlankLineValidator;
import io.montanus.shelves.ui.LibraryTextCommandInterpreter;
import io.montanus.shelves.ui.TextProcessor;
import io.montanus.shelves.ui.TextSanitizerImpl;

import java.io.InputStreamReader;
import java.util.HashMap;

public class Library {
    public static void main(String[] args) {
        final Catalog catalog = new InMemoryCatalog(new HashMap<String, Book>() {{
            put("12345", new Book("Clean Code"));
            put("23456", new Book("Effective Java"));
        }});
        final Display display = new EnglishConsoleDisplay();
        final LibraryController libraryController =
                new LibraryController(catalog, display);

        libraryController.onIsbn("12345");
        libraryController.onIsbn("23456");
        libraryController.onIsbn("99999");
        libraryController.onIsbn("");

        final TextProcessor textProcessor =
                new TextProcessor(
                        new TextSanitizerImpl(new BlankLineValidator()),
                        new LibraryTextCommandInterpreter(libraryController));

        textProcessor.process(new InputStreamReader(System.in));
    }
}
