package io.montanus.shelves.test;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class FindingBookInMemoryCatalogTest {
    @Test
    public void bookFound() {
        final Book book = new Book();
        final InMemoryCatalog catalog = new InMemoryCatalog(new HashMap<String, Book>() {{
            put("::isbn for existing book::", book);
        }});
        assertEquals(book, catalog.findBook("::isbn for existing book::"));
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
