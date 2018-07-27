package io.montanus.shelves.ui;

import io.montanus.shelves.IsbnListener;

public class LibraryTextCommandInterpreter implements TextCommandInterpreter {
    private final IsbnListener listener;

    public LibraryTextCommandInterpreter(IsbnListener listener) {
        this.listener = listener;
    }

    @Override
    public void interpret(String textCommand) {
        listener.onIsbn(textCommand);
    }
}
