package io.montanus.shelves.test;

import io.montanus.shelves.Book;
import io.montanus.shelves.ConsolePostOffice;
import io.montanus.shelves.EnglishDisplay;
import io.montanus.shelves.PostOffice;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DisplayEnglishMessagesTest {

    private PrintStream productionSystemOut;
    private ByteArrayOutputStream canvas;
    private EnglishDisplay display;

    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Before
    public void hijackSystemOut() {
        productionSystemOut = System.out;
        canvas = new ByteArrayOutputStream();
        System.setOut(new PrintStream(canvas));
    }

    @Before
    public void setUp() {
        display = new EnglishDisplay(new ConsolePostOffice());
    }

    @After
    public void tearDown() {
        System.setOut(productionSystemOut);
    }

    @Test
    public void emptyIsbn() {
        final PostOffice postOffice = context.mock(PostOffice.class);
        context.checking(new Expectations() {{
            oneOf(postOffice).sendMessage("ISBN Error: empty ISBN");
        }});

        new EnglishDisplay(postOffice).displayEmptyIsbnMessage();
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
