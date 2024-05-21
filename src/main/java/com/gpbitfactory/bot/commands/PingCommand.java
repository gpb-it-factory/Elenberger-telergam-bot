package com.gpbitfactory.bot.commands;

import com.gpbitfactory.bot.logger.BotLogger;
import org.springframework.stereotype.Component;

@Component
public class PingCommand implements Command {
    private final BotLogger botLogger;

    public PingCommand(BotLogger botLogger) {
        this.botLogger = botLogger;
    }

    @Override
    public String execute() {
        botLogger.logMessage("Исполняю команду /ping");
        return "pong";
    }
}
