package io.montanus.shelves.test;

import io.montanus.shelves.Book;
import io.montanus.shelves.Catalog;
import io.montanus.shelves.InMemoryCatalog;

import java.util.HashMap;

public class FindingBookInMemoryCatalogTest extends FindingBookInCatalogContract {

    @Override
    protected Catalog catalogWith(final String isbn, final Book book) {
        return new InMemoryCatalog(new HashMap<String, Book>() {{
            put("Definitely not " + isbn, new Book("::irrelevant::"));
            put(isbn, book);
            put("Again, definitely not " + isbn, new Book("::irrelevant::"));
        }});
    }

    @Override
    protected Catalog catalogWithout(final String isbnToAvoid) {
        return new InMemoryCatalog(new HashMap<String, Book>() {{
            put("Anything but " + isbnToAvoid, new Book("::irrelevant::"));
        }});
    }

}
