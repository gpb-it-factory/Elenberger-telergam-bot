package com.gpbitfactory.bot.commandsTest;

import com.gpbitfactory.bot.commands.CommandAnswerer;
import com.gpbitfactory.bot.commands.CommandMapContainer;
import com.gpbitfactory.bot.commands.HelpCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CommandMapContainer.class, HelpCommand.class} )
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class CommandAnswererTest {
    @Mock
    Message message;
    @Mock
    User user;
    @Autowired
    private  CommandMapContainer commandMapContainer;
    @Autowired
    private HelpCommand helpCommand;
    @Test
    public void answeringTestIsCommandInMap() {
        CommandAnswerer commandAnswerer = new CommandAnswerer(commandMapContainer);
        when(message.getFrom()).thenReturn(user);
        when(user.getUserName()).thenReturn("ABOBA");
        when(message.isCommand()).thenReturn(true);
        when(message.getText()).thenReturn("/help");
        Assertions.assertEquals(helpCommand.execute(message), commandAnswerer.answering(message));
    }

    @Test
    public void answeringTestNotCommand() {
        CommandAnswerer commandAnswerer = new CommandAnswerer(commandMapContainer);
        when(message.isCommand()).thenReturn(false);
        Assertions.assertEquals(commandAnswerer.answering(message), "Команда не опознана, проверьте список команд отправив /help");
    }

    @Test
    public void answeringTestIsCommandNotInMap() {
        CommandAnswerer commandAnswerer = new CommandAnswerer(commandMapContainer);
        when(message.isCommand()).thenReturn(true);
        when(message.getText()).thenReturn("help");
        Assertions.assertEquals(commandAnswerer.answering(message), "Команда не опознана, проверьте список команд отправив /help");
    }
}
