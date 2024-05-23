package com.gpbitfactory.bot.commandsTest;

import com.gpbitfactory.bot.commands.HelpCommand;
import com.gpbitfactory.bot.commands.StartCommand;
import com.gpbitfactory.bot.logger.BotLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StartCommandTest {
    @Test
    public void executeTest() {
        BotLogger botLogger = new BotLogger();
        StartCommand command = new StartCommand("text",botLogger);
        Assertions.assertEquals(command.execute(), "Готов к работе!");
    }
}
