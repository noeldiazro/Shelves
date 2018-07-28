package io.montanus.shelves;

public class EnglishDictionary implements Dictionary {
    @Override
    public String getEmptyIsbnMessageFormat() {
        return "ISBN Error: empty ISBN";
    }

    @Override
    public String getBookNotFoundMessageFormat() {
        return "Book not found for %s";
    }

    @Override
    public String getDisplayPriceMessageFormat() {
        return "Title: %s";
    }
}
