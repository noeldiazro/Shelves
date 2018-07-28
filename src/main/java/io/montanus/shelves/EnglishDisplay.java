package io.montanus.shelves;

public class EnglishDisplay implements Display {

    private final PostOffice postOffice;
    private final EnglishDictionary dictionary;

    public EnglishDisplay(PostOffice postOffice) {
        this.postOffice = postOffice;
        this.dictionary = new EnglishDictionary();
    }

    @Override
    public void displayEmptyIsbnMessage() {
        render(mergeTemplate(dictionary.getEmptyIsbnMessageFormat()));
    }

    @Override
    public void displayBookNotFoundMessage(String isbn) {
        render(mergeTemplate(dictionary.getBookNotFoundMessageFormat(), isbn));
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

    private static class EnglishDictionary {
        public String getEmptyIsbnMessageFormat() {
            return "ISBN Error: empty ISBN";
        }

        public String getBookNotFoundMessageFormat() {
            return "Book not found for %s";
        }

        public String getDisplayPriceMessageFormat() {
            return "Title: %s";
        }
    }
}
