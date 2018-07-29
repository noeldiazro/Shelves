package io.montanus.shelves;

public class TextDisplay implements Display {

    private final PostOffice postOffice;
    private final Dictionary dictionary;

    public TextDisplay(PostOffice postOffice, Dictionary dictionary) {
        this.postOffice = postOffice;
        this.dictionary = dictionary;
    }

    @Override
    public void displayEmptyIsbnMessage() {
        display(new EmptyIsbnEnglishTemplate());
    }

    @Override
    public void displayBookNotFoundMessage(String isbn) {
        display(new BookNotFoundEnglishTemplate(isbn));
    }

    private void display(Template template) {
        render(template.merge());
    }

    @Override
    public void displayTitle(Book book) {
        render(mergeTemplate(dictionary.getDisplayPriceMessageFormat(), book.getTitle()));
    }


    private void render(String text) {
        postOffice.sendMessage(text);
    }

    private String mergeTemplate(String template, Object... placeholderValues) {
        return String.format(template, placeholderValues);
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
}
