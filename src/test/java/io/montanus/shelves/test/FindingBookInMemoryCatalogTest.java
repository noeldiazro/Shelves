package io.montanus.shelves.test;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class FindingBookInMemoryCatalogTest {
    @Test
    public void bookFound() {
        final Book book = new Book();
        final InMemoryCatalog catalog = catalogWith("::isbn for existing book::", book);
        assertEquals(book, catalog.findBook("::isbn for existing book::"));
    }

    private InMemoryCatalog catalogWith(final String isbn, final Book book) {
        return new InMemoryCatalog(new HashMap<String, Book>() {{
            put("Definitely not " + isbn, new Book());
            put(isbn, book);
            put("Again, definitely not " + isbn, new Book());
        }});
    }

    @Test
    public void bookNotFound() {
        final InMemoryCatalog catalog = catalogWithout("::isbn for non existing book::");
        assertEquals(null, catalog.findBook("::isbn for non existing book::"));
    }

    private InMemoryCatalog catalogWithout(final String isbnToAvoid) {
        return new InMemoryCatalog(new HashMap<String, Book>() {{
            put("Anything but " + isbnToAvoid, new Book());
        }});
    }

    private static class InMemoryCatalog {
        private final HashMap<String, Book> booksByIsbn;

        private InMemoryCatalog(HashMap<String, Book> booksByIsbn) {
            this.booksByIsbn = booksByIsbn;
        }

        private Book findBook(String isbn) {
            return booksByIsbn.get(isbn);
        }
    }
}
