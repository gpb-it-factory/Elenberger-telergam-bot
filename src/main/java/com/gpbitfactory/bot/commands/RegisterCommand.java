package com.gpbitfactory.bot.commands;

import com.gpbitfactory.bot.api.service.CommandService;
import com.gpbitfactory.bot.logger.BotLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class RegisterCommand implements Command {
    private final String text;
    private final BotLogger botLogger;
    private final CommandService commandService;



    public RegisterCommand(@Value("/register") String text, @Autowired BotLogger botLogger, @Autowired CommandService commandService) {
        this.text = text;
        this.botLogger = botLogger;
        this.commandService = commandService;
    }

    @Override
    public String execute(Message message) {
        botLogger.logMessage("Исполняю команду /register");
        return commandService.registerExecute(message).getBody();
    }

    @Override
    public String getText() {
        return text;
    }
}
