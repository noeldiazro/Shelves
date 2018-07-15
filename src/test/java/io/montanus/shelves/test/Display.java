package io.montanus.shelves.test;

public interface Display {
    void displayTitle(Book book);
    void displayBookNotFoundMessage(String isbn);
    void displayEmptyIsbnMessage();
}
