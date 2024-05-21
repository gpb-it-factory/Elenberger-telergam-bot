package com.gpbitfactory.bot.commands;

import com.gpbitfactory.bot.logger.BotLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private final BotLogger botLogger;
    @Autowired
    public HelpCommand(BotLogger botLogger) {
        this.botLogger = botLogger;
    }

    @Override
    public String execute() {
        botLogger.logMessage("Исполняю команду /help");
        return "Сейчас доступны только команды /help, но вы уже догадались её использовать и команда /ping";
    }
}
