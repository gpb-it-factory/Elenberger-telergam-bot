package com.gpbitfactory.bot.commands;

import com.gpbitfactory.bot.logger.BotLogger;

public class StartCommand implements Command{
    private final BotLogger botLogger;

    public StartCommand(BotLogger botLogger) {
        this.botLogger = botLogger;
    }

    @Override
    public String execute() {
        botLogger.logMessage("Исполняю команду /help");
        return "Готов к работе!";
    }
}
