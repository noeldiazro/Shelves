package io.montanus.shelves;

import java.util.HashMap;

public class InMemoryCatalog implements Catalog {
    private final HashMap<String, Book> booksByIsbn;

    public InMemoryCatalog(HashMap<String, Book> booksByIsbn) {
        this.booksByIsbn = booksByIsbn;
    }

    public Book findBook(String isbn) {
        return booksByIsbn.get(isbn);
    }
}
