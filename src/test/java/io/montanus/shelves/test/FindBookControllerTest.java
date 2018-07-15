package io.montanus.shelves.test;

import io.montanus.shelves.Book;
import io.montanus.shelves.Catalog;
import io.montanus.shelves.Display;
import io.montanus.shelves.LibraryController;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class FindBookControllerTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void bookFound() {
        final Book book = new Book("::irrelevant::");
        final Catalog catalog = context.mock(Catalog.class);
        final Display display = context.mock(Display.class);

        context.checking(new Expectations() {{
            allowing(catalog).findBook(with("::isbn for existing book::"));
            will(returnValue(book));

            oneOf(display).displayTitle(with(book));
        }});

        final LibraryController libraryController = new LibraryController(catalog, display);
        libraryController.onIsbn("::isbn for existing book::");
    }

    @Test
    public void bookNotFound() {
        final Catalog catalog = context.mock(Catalog.class);
        final Display display = context.mock(Display.class);

        context.checking(new Expectations() {{
            allowing(catalog).findBook(with("::isbn for non existing book::"));
            will(returnValue(null));

            oneOf(display).displayBookNotFoundMessage(with("::isbn for non existing book::"));
        }});

        final LibraryController libraryController = new LibraryController(catalog, display);
        libraryController.onIsbn("::isbn for non existing book::");
    }

    @Test
    public void emptyIsbn() {
        final Display display = context.mock(Display.class);

        context.checking(new Expectations() {{
            oneOf(display).displayEmptyIsbnMessage();
        }});

        final LibraryController libraryController = new LibraryController(null, display);
        libraryController.onIsbn("");
    }

}
