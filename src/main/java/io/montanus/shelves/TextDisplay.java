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
        render(mergeTemplate(dictionary.getEmptyIsbnMessageFormat()));
    }

    @Override
    public void displayBookNotFoundMessage(String isbn) {
        render(new BookNotFoundTemplate(isbn).merge());
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

    private class BookNotFoundTemplate implements Template {
        private String isbn;

        private BookNotFoundTemplate(String isbn) {
            this.isbn = isbn;
        }

        @Override
        public String merge() {
            return String.format(dictionary.getBookNotFoundMessageFormat(), isbn);
        }
    }
}
