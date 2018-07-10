package io.montanus.shelves.test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class FindingBookInCatalogContract {
    @Test
    public void bookFound() {
        final Book book = new Book();
        final Catalog catalog = catalogWith("::isbn for existing book::", book);
        assertEquals(book, catalog.findBook("::isbn for existing book::"));
    }

    protected abstract Catalog catalogWith(String isbn, Book book);

    @Test
    public void bookNotFound() {
        final Catalog catalog = catalogWithout("::isbn for non existing book::");
        assertEquals(null, catalog.findBook("::isbn for non existing book::"));
    }

    protected abstract Catalog catalogWithout(String isbnToAvoid);
}
