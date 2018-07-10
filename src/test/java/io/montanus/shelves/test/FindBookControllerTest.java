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

            oneOf(display).displayBook(with(book));
        }});

        final LibraryController libraryController = new LibraryController(catalog, display);
        libraryController.onISBN("::isbn for existing book::");
    }

    private static class Book {
    }

    private interface Catalog {
        Book findBook(String isbn);
    }

    private interface Display {
        void displayBook(Book book);
    }

    private static class LibraryController {
        private final Catalog catalog;
        private final Display display;

        private LibraryController(Catalog catalog, Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        private void onISBN(String isbn) {
            display.displayBook(catalog.findBook(isbn));
        }
    }
}
