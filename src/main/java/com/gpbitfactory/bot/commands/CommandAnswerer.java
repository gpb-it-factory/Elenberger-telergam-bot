package com.gpbitfactory.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class CommandAnswerer {
    private final CommandMapContainer commandMapContainer;

    @Autowired
    public CommandAnswerer(CommandMapContainer commandMapContainer) {
        this.commandMapContainer = commandMapContainer;
    }

    public String answering(Message message) {
        String[] messageText = message.getText().split(" ");
        if (message.isCommand() && commandMapContainer.getCommandMap().containsKey(messageText[0])) {
            return commandMapContainer.getCommand(messageText[0]).execute(message);
        }
        return "Команда не опознана, проверьте список команд отправив /help";
    }
}
