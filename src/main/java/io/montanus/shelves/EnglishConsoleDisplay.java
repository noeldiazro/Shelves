package io.montanus.shelves;

public class EnglishConsoleDisplay implements Display {

    private static final String EMPTY_ISBN_MESSAGE_FORMAT = "ISBN Error: empty ISBN";
    private static final String BOOK_NOT_FOUND_MESSAGE_FORMAT = "Book not found for %s";
    private static final String DISPLAY_PRICE_MESSAGE_FORMAT = "Title: %s";
    private final PostOffice postOffice;

    public EnglishConsoleDisplay() {
        postOffice = new ConsolePostOffice();
    }

    @Override
    public void displayEmptyIsbnMessage() {
        render(mergeTemplate(EMPTY_ISBN_MESSAGE_FORMAT));
    }

    @Override
    public void displayBookNotFoundMessage(String isbn) {
        render(mergeTemplate(BOOK_NOT_FOUND_MESSAGE_FORMAT, isbn));
    }

    @Override
    public void displayTitle(Book book) {
        render(mergeTemplate(DISPLAY_PRICE_MESSAGE_FORMAT, book.getTitle()));
    }

    private void render(String text) {
        postOffice.sendMessage(text);
    }

    private String mergeTemplate(String template, Object... placeholderValues) {
        return String.format(template, placeholderValues);
    }

    private static class ConsolePostOffice implements PostOffice {
        @Override
        public void sendMessage(String text) {
            System.out.println(text);
        }
    }
}
