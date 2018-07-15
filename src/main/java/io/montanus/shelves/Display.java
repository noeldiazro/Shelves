package io.montanus.shelves;

public interface Display {
    void displayTitle(Book book);
    void displayBookNotFoundMessage(String isbn);
    void displayEmptyIsbnMessage();
}
