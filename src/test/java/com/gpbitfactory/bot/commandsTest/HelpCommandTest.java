package com.gpbitfactory.bot.commandsTest;

import com.gpbitfactory.bot.commands.HelpCommand;
import com.gpbitfactory.bot.logger.BotLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;

@ExtendWith(MockitoExtension.class)
public class HelpCommandTest {

    @Mock
    Message message;
    @Test
    public void executeTest() {
        BotLogger botLogger = new BotLogger();
        HelpCommand command = new HelpCommand("text", botLogger);
        Assertions.assertEquals(command.execute(message), "Сейчас доступны только команды /help, но вы уже догадались её использовать и команда /ping");
    }
}
