package com.gpbitfactory.bot.commandsTest;

import com.gpbitfactory.bot.commands.Command;
import com.gpbitfactory.bot.commands.CommandAnswerer;
import com.gpbitfactory.bot.commands.HelpCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommandAnswererTest {
    @Mock
    Message message;
    @Mock
    User user;

    Map<String, Command> map = new HashMap<>();
    HelpCommand helpCommand = new HelpCommand("/help");

    @BeforeEach
    public void setUp() {
        map.put(helpCommand.getText(), helpCommand);
    }

    @Test
    public void answeringTestIsCommandInMap() {
        CommandAnswerer commandAnswerer = new CommandAnswerer(map);
        when(message.getFrom()).thenReturn(user);
        when(user.getUserName()).thenReturn("ABOBA");
        when(message.isCommand()).thenReturn(true);
        when(message.getText()).thenReturn("/help");
        Assertions.assertEquals(helpCommand.execute(message), commandAnswerer.answering(message));
    }

    @Test
    public void answeringTestNotCommand() {
        CommandAnswerer commandAnswerer = new CommandAnswerer(map);
        when(message.isCommand()).thenReturn(false);
        Assertions.assertEquals(commandAnswerer.answering(message), "Команда не опознана, проверьте список команд отправив /help");
    }

    @Test
    public void answeringTestIsCommandNotInMap() {
        CommandAnswerer commandAnswerer = new CommandAnswerer(map);
        when(message.isCommand()).thenReturn(true);
        when(message.getText()).thenReturn("help");
        Assertions.assertEquals(commandAnswerer.answering(message), "Команда не опознана, проверьте список команд отправив /help");
    }
}
