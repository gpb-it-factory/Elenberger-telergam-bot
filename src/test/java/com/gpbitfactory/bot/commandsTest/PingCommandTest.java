package com.gpbitfactory.bot.commandsTest;

import com.gpbitfactory.bot.commands.PingCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PingCommandTest {

    @Mock
    Message message;
    @Mock
    User user;

    @Test
    public void executeTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getUserName()).thenReturn("ABOBA");
        PingCommand command = new PingCommand("text");
        String expectedMessageText = "pong";

        String messageText = command.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }
}
