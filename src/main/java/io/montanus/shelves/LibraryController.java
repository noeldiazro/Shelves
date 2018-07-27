package io.montanus.shelves;

public final class LibraryController implements IsbnListener {
    private final Catalog catalog;
    private final Display display;

    public LibraryController(Catalog catalog, Display display) {
        this.catalog = catalog;
        this.display = display;
    }

    @Override
    public void onIsbn(String isbn) {
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
