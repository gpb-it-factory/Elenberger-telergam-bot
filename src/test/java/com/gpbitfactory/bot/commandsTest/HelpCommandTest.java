package com.gpbitfactory.bot.commandsTest;

import com.gpbitfactory.bot.commands.HelpCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HelpCommandTest {

    @Mock
    Message message;
    @Mock
    User user;

    @Test
    public void executeTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getUserName()).thenReturn("ABOBA");
        HelpCommand command = new HelpCommand("text");

        String actualAnswer = command.execute(message);

        Assertions.assertEquals(actualAnswer, """
                Доступные команды:
                /help - список доступных команд
                /register - регистрация в мини-банке
                /createaccount - создать счет в мини-банке "/createaccount [название счета не более 20-ти символов]
                /balance - запрос баланса ваших счетов
                /ping - пасхалка""");
    }
}
