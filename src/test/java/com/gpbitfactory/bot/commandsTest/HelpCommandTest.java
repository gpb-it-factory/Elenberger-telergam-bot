package com.gpbitfactory.bot.commandsTest;

import com.gpbitfactory.bot.commands.HelpCommand;
import com.gpbitfactory.bot.logger.BotLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelpCommandTest {


    @Test
    public void executeTest() {
        BotLogger botLogger = new BotLogger();
        HelpCommand command = new HelpCommand("text", botLogger);
        Assertions.assertEquals(command.execute(), "Сейчас доступны только команды /help, но вы уже догадались её использовать и команда /ping");
    }
}
