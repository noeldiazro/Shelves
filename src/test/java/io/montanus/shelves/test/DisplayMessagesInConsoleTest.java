package io.montanus.shelves.test;

import io.montanus.shelves.Book;
import io.montanus.shelves.EnglishConsoleDisplay;
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
    private EnglishConsoleDisplay display;

    @Before
    public void hijackSystemOut() {
        productionSystemOut = System.out;
        canvas = new ByteArrayOutputStream();
        System.setOut(new PrintStream(canvas));
    }

    @Before
    public void setUp() {
        display = new EnglishConsoleDisplay();
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

}
