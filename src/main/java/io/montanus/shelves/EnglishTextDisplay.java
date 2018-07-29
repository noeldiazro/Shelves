package io.montanus.shelves;

public class EnglishTextDisplay implements Display {

    private final PostOffice postOffice;

    public EnglishTextDisplay(PostOffice postOffice) {
        this.postOffice = postOffice;
    }

    @Override
    public void displayEmptyIsbnMessage() {
        display(new EmptyIsbnEnglishTemplate());
    }

    @Override
    public void displayBookNotFoundMessage(String isbn) {
        display(new BookNotFoundEnglishTemplate(isbn));
    }

    @Override
    public void displayTitle(Book book) {
        display(new PriceEnglishTemplate(book));
    }

    private void display(Template template) {
        render(template.merge());
    }

    private void render(String text) {
        postOffice.sendMessage(text);
    }

    private class BookNotFoundEnglishTemplate implements Template {
        private static final String BOOK_NOT_FOUND_FOR = "Book not found for";
        private String isbn;

        private BookNotFoundEnglishTemplate(String isbn) {
            this.isbn = isbn;
        }

        @Override
        public String merge() {
            return String.format(BOOK_NOT_FOUND_FOR + " %s", isbn);
        }
    }

    private class EmptyIsbnEnglishTemplate implements Template {
        private static final String ISBN_ERROR_EMPTY_ISBN = "ISBN Error: empty ISBN";

        @Override
        public String merge() {
            return String.format(ISBN_ERROR_EMPTY_ISBN);
        }
    }

    private class PriceEnglishTemplate implements Template {
        private static final String TITLE = "Title";
        private Book book;

        private PriceEnglishTemplate(Book book) {
            this.book = book;
        }

        @Override
        public String merge() {
            return String.format(TITLE + ": %s", book.getTitle());
        }
    }
}
