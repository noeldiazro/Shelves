package io.montanus.shelves;

import io.montanus.shelves.ui.BlankLineValidator;
import io.montanus.shelves.ui.LibraryTextCommandInterpreter;
import io.montanus.shelves.ui.TextProcessor;
import io.montanus.shelves.ui.TextSanitizerImpl;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;

public class Library {
    public static void main(String[] args) {
        final Catalog catalog = new InMemoryCatalog(new HashMap<String, Book>() {{
            put("12345", new Book("Clean Code"));
            put("23456", new Book("Effective Java"));
        }});
        final UdpPostOffice postOffice = new UdpPostOffice("localhost", 5358, Charset.defaultCharset());
        final Display display = new TextDisplay(postOffice, new EnglishDictionary());
        final LibraryController libraryController =
                new LibraryController(catalog, display);


        final TextProcessor textProcessor =
                new TextProcessor(
                        new TextSanitizerImpl(new BlankLineValidator()),
                        new LibraryTextCommandInterpreter(libraryController));

        textProcessor.process(new InputStreamReader(System.in));
    }
}
