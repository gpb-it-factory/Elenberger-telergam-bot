package com.gpbitfactory.bot.commands;

import com.gpbitfactory.bot.logger.BotLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command{
    private final String text;
    private final BotLogger botLogger;

    public StartCommand(@Value("/start") String text, BotLogger botLogger) {
        this.text = text;
        this.botLogger = botLogger;
    }
    public String getText() {
        return this.text;
    }

    @Override
    public String execute() {
        botLogger.logMessage("Исполняю команду /help");
        return "Готов к работе!";
    }
}
