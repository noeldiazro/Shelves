package io.montanus.shelves.ui.test;

import io.montanus.shelves.IsbnListener;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class InterpretTextCommandsTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void interpretAsIsbn() {
        final IsbnListener listener = context.mock(IsbnListener.class);

        context.checking(new Expectations() {{
            oneOf(listener).onIsbn("::isbn::");
        }});

        final TextCommandInterpreter interpreter = new LibraryTextCommandInterpreter(listener);
        interpreter.interpret("::isbn::");
    }

    private static class LibraryTextCommandInterpreter implements TextCommandInterpreter {
        private final IsbnListener listener;

        public LibraryTextCommandInterpreter(IsbnListener listener) {
            this.listener = listener;
        }

        @Override
        public void interpret(String textCommand) {
            listener.onIsbn(textCommand);
        }
    }
}
