package com.gpbitfactory.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

@Service
public class CommandAnswerer {
    private final Map<String, Command> answers;

    @Autowired
    public CommandAnswerer(Map<String, Command> answers) {
        this.answers = answers;
    }

    public String answering(Message message) {
        String[] messageText = message.getText().split(" ");
        if (message.isCommand() && answers.containsKey(messageText[0])) {
            return answers.get(messageText[0]).execute(message);
        }
        return "Команда не опознана, проверьте список команд отправив /help";
    }
}
