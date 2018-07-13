package io.montanus.shelves.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class FindBookControllerTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void bookFound() {
        final Book book = new Book();
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

    private interface Display {
        void displayTitle(Book book);
        void displayBookNotFoundMessage(String isbn);
        void displayEmptyIsbnMessage();
    }

    private static class LibraryController {
        private final Catalog catalog;
        private final Display display;

        private LibraryController(Catalog catalog, Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        private void onIsbn(String isbn) {
            if ("".equals(isbn)) {
                display.displayEmptyIsbnMessage();
                return;
            }
            final Book book = catalog.findBook(isbn);
            if (book == null)
                display.displayBookNotFoundMessage(isbn);
            else
                display.displayTitle(book);
        }
    }
}
