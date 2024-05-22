package com.gpbitfactory.bot.commandsTest;

import com.gpbitfactory.bot.commands.HelpCommand;
import com.gpbitfactory.bot.commands.PingCommand;
import com.gpbitfactory.bot.logger.BotLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PingCommandTest {
    @Test
    public void executeTest() {
        BotLogger botLogger = new BotLogger();
        PingCommand command = new PingCommand(botLogger);
        Assertions.assertEquals(command.execute(), "pong");
    }
}
