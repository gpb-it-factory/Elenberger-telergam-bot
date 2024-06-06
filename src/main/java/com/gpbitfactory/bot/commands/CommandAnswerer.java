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
        if (message.isCommand() && answers.containsKey(message.getText())) {
            return answers.get(message.getText()).execute(message);
        }
        return "Команда не опознана, проверьте список команд отправив /help";
    }
}
