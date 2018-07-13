package io.montanus.shelves.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
    public void setUp() throws Exception {
        display = new ConsoleDisplay();
    }

    @After
    public void tearDown() throws Exception {
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
    @Ignore("Pending on adding title to book objects.")
    public void title() {
        display.displayTitle(new Book("::irrelevant::"));
        assertLines(Collections.singletonList("::book title::"), canvas.toString());
    }

    private void assertLines(List<String> expectedLines, String text) {
        assertEquals(expectedLines, lines(text));
    }

    private List<String> lines(String text) {
        return Arrays.asList(text.split(System.lineSeparator()));
    }

    private static class ConsoleDisplay {
        private void displayEmptyIsbnMessage() {
            System.out.println("ISBN Error: empty ISBN");
        }

        private void displayBookNotFoundMessage(String isbn) {
            System.out.println(String.format("Book not found for %s", isbn));
        }

        private void displayTitle(Book book) {

        }
    }
}
