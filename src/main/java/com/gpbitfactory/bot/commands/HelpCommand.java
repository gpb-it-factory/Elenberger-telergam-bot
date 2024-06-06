package com.gpbitfactory.bot.commands;

import com.gpbitfactory.bot.logger.BotLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class HelpCommand implements Command {
    private final String text;
    private final BotLogger botLogger;

    public HelpCommand(@Value("/help") String text, @Autowired BotLogger botLogger) {
        this.text = text;
        this.botLogger = botLogger;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String execute(Message message) {
        botLogger.logMessage("Исполняю команду /help");
        return "Сейчас доступны только команды /help, но вы уже догадались её использовать и команда /ping";
    }
}
