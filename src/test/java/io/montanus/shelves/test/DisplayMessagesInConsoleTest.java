package io.montanus.shelves.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DisplayMessagesInConsoleTest {

    private PrintStream productionSystemOut;
    private ByteArrayOutputStream canvas;
    private ConsoleDisplay display;

    @Before
    public void hijackSystemOut() {
        productionSystemOut = System.out;
        canvas = new ByteArrayOutputStream();
        System.setOut(new PrintStream(canvas));
    }

    @Before
    public void setUp() {
        display = new ConsoleDisplay();
    }

    @After
    public void tearDown() {
        System.setOut(productionSystemOut);
    }

    @Test
    public void emptyIsbn() {
        display.displayEmptyIsbnMessage();

        assertLines(Collections.singletonList("ISBN Error: empty ISBN"), canvas.toString());
    }

    @Test
    public void bookNotFound() {
        display.displayBookNotFoundMessage("12345");

        assertLines(Collections.singletonList("Book not found for 12345"), canvas.toString());
    }

    @Test
    public void title() {
        display.displayTitle(new Book("::book title::"));
        assertLines(Collections.singletonList("Title: ::book title::"), canvas.toString());
    }

    private void assertLines(List<String> expectedLines, String text) {
        assertEquals(expectedLines, lines(text));
    }

    private List<String> lines(String text) {
        return Arrays.asList(text.split(System.lineSeparator()));
    }

    private static class ConsoleDisplay implements Display {

        private static final String EMPTY_ISBN_MESSAGE_FORMAT = "ISBN Error: empty ISBN";
        private static final String BOOK_NOT_FOUND_MESSAGE_FORMAT = "Book not found for %s";
        private static final String DISPLAY_PRICE_MESSAGE_FORMAT = "Title: %s";

        @Override
        public void displayEmptyIsbnMessage() {
            render(mergeTemplate(EMPTY_ISBN_MESSAGE_FORMAT));
        }

        @Override
        public void displayBookNotFoundMessage(String isbn) {
            render(mergeTemplate(BOOK_NOT_FOUND_MESSAGE_FORMAT, isbn));
        }

        @Override
        public void displayTitle(Book book) {
            render(mergeTemplate(DISPLAY_PRICE_MESSAGE_FORMAT, book.getTitle()));
        }

        private void render(String text) {
            System.out.println(text);
        }

        private String mergeTemplate(String template, Object... placeholderValues) {
            return String.format(template, placeholderValues);
        }
    }
}
