package com.gpbitfactory.bot.commandsTest;

import com.gpbitfactory.bot.commands.Command;
import com.gpbitfactory.bot.commands.CommandAnswerer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandAnswererTest {
    @Mock
    Map<String, Command> answers;
    @Mock
    Command command;
    @Mock
    Message messageCommand;
    @Mock
    Message messageNotCommand;

    @Test
    public void answeringTest() {
        CommandAnswerer commandAnswerer = new CommandAnswerer(answers);
        when(answers.get(anyString())).thenReturn(command);
        when(command.execute()).thenReturn("1");
        when(messageCommand.isCommand()).thenReturn(true);
        when(messageNotCommand.isCommand()).thenReturn(false);
        when(answers.containsKey(anyString())).thenReturn(true);
        when(messageCommand.getText()).thenReturn("1");
        Assertions.assertEquals(commandAnswerer.answering(messageCommand),"1");
        Assertions.assertNotEquals(commandAnswerer.answering(messageNotCommand),"1");
    }

}
