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

    @Before
    public void hijackSystemOut() {
        productionSystemOut = System.out;
        canvas = new ByteArrayOutputStream();
        System.setOut(new PrintStream(canvas));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(productionSystemOut);
    }

    @Test
    public void emptyIsbn() {
        final ConsoleDisplay display = new ConsoleDisplay();
        display.displayEmptyIsbnMessage();
        assertEquals(Collections.singletonList("ISBN Error: empty ISBN"), lines(canvas.toString()));
    }

    @Test
    public void bookNotFound() {
        final ConsoleDisplay display = new ConsoleDisplay();

        display.displayBookNotFoundMessage("12345");

        assertEquals(Collections.singletonList("Book not found for 12345"), lines(canvas.toString()));
    }

    private List<String> lines(String text) {
        return Arrays.asList(text.split(System.lineSeparator()));
    }

    private static class ConsoleDisplay {
        private void displayEmptyIsbnMessage() {
            System.out.println("ISBN Error: empty ISBN");
        }

        private void displayBookNotFoundMessage(String isbn) {
            System.out.println("Book not found for 12345");
        }
    }
}
