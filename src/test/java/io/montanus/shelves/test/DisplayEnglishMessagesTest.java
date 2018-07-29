package io.montanus.shelves.test;

import io.montanus.shelves.*;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DisplayEnglishMessagesTest {

    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();
    private PostOffice postOffice;
    private Display display;

    @Before
    public void setUp() {
        postOffice = context.mock(PostOffice.class);
        display = new TextDisplay(postOffice, new EnglishDictionary());
    }

    @Test
    public void emptyIsbn() {
        context.checking(new Expectations() {{
            oneOf(postOffice).sendMessage("ISBN Error: empty ISBN");
        }});

        display.displayEmptyIsbnMessage();
    }

    @Test
    public void bookNotFound() {
        context.checking(new Expectations() {{
            oneOf(postOffice).sendMessage("Book not found for ::isbn::");
        }});

        display.displayBookNotFoundMessage("::isbn::");
    }

    @Test
    public void title() {
        context.checking(new Expectations(){{
            oneOf(postOffice).sendMessage("Title: ::book title::");
        }});

        display.displayTitle(new Book("::book title::"));
    }

}
