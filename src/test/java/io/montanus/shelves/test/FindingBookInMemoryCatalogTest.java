package io.montanus.shelves.test;

import java.util.HashMap;

public class FindingBookInMemoryCatalogTest extends FindingBookInCatalogContract {

    @Override
    protected Catalog catalogWith(final String isbn, final Book book) {
        return new InMemoryCatalog(new HashMap<String, Book>() {{
            put("Definitely not " + isbn, new Book());
            put(isbn, book);
            put("Again, definitely not " + isbn, new Book());
        }});
    }

    @Override
    protected Catalog catalogWithout(final String isbnToAvoid) {
        return new InMemoryCatalog(new HashMap<String, Book>() {{
            put("Anything but " + isbnToAvoid, new Book());
        }});
    }

    private static class InMemoryCatalog implements Catalog {
        private final HashMap<String, Book> booksByIsbn;

        private InMemoryCatalog(HashMap<String, Book> booksByIsbn) {
            this.booksByIsbn = booksByIsbn;
        }

        public Book findBook(String isbn) {
            return booksByIsbn.get(isbn);
        }
    }
}
