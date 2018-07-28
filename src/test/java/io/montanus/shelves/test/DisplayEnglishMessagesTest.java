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
        final PostOffice postOffice = context.mock(PostOffice.class);
        context.checking(new Expectations() {{
            oneOf(postOffice).sendMessage("Book not found for ::isbn::");
        }});

        new EnglishDisplay(postOffice).displayBookNotFoundMessage("::isbn::");
    }

    @Test
    public void title() {
        final PostOffice postOffice = context.mock(PostOffice.class);
        context.checking(new Expectations(){{
            oneOf(postOffice).sendMessage("Title: ::book title::");
        }});

        new EnglishDisplay(postOffice).displayTitle(new Book("::book title::"));
    }

    private void assertLines(List<String> expectedLines, String text) {
        assertEquals(expectedLines, lines(text));
    }

    private List<String> lines(String text) {
        return Arrays.asList(text.split(System.lineSeparator()));
    }

}
