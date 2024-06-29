package com.gpbitfactory.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

@Service
public class CommandAnswerer {
    private final CommandMapContainer commandMapContainer;

    @Autowired
    public CommandAnswerer(CommandMapContainer commandMapContainer) {
        this.commandMapContainer = commandMapContainer;
    }

    public String answering(Message message) {
        if (message.isCommand() && commandMapContainer.getCommandMap().containsKey(message.getText())) {
            return commandMapContainer.getCommand(message.getText()).execute(message);
        }
        return "Команда не опознана, проверьте список команд отправив /help";
    }
}
