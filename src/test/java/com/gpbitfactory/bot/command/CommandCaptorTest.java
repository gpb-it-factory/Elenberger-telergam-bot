package com.gpbitfactory.bot.command;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.stream.Stream;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandCaptorTest {
    @Mock
    Message message;

    @Test
    public void answerPingTest() {
        CommandCaptor captor = new CommandCaptor();
        boolean isCommandAndHasText = true;
        String text = "";
        when(message.hasText()).thenReturn(isCommandAndHasText);
        when(message.isCommand()).thenReturn(isCommandAndHasText);
        when(message.getChatId()).thenReturn(10L);
        when(message.getText()).thenReturn(text);
        Assertions.assertEquals(captor.answer(message), "Пока я не знаю что ответить на такой запрос, но скоро научусь!");
    }

    @ParameterizedTest
    @MethodSource("addDataProvider")
    void testAnswer_withMultipleInputs(boolean isCommand, String text, String expected) {
        CommandCaptor captor = new CommandCaptor();
        if (isCommand) {
            when(message.hasText()).thenReturn(isCommand);
            when(message.isCommand()).thenReturn(isCommand);
            when(message.getChatId()).thenReturn(10L);
            when(message.getText()).thenReturn(text);
        }
        Assertions.assertEquals(captor.answer(message), expected);
    }

    static Stream<Arguments> addDataProvider() {
        return Stream.of(
                Arguments.of(true, "/ping", "pong"),
                Arguments.of(true, "/help", "Сейчас доступны только команды /help, но вы уже догадались её использовать и команда /ping"),
                Arguments.of(true, "/", "Пока я не знаю что ответить на такой запрос, но скоро научусь!"),
                Arguments.of(false, "pong", "Не похоже на команду"));
    }
}
