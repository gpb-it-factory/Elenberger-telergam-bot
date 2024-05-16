package com.gpbitfactory.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommandAnswerer {
    private Map<String, String> answers;

    @Autowired
    public CommandAnswerer(Map<String, String> answers) {
        this.answers = answers;
    }

    public String needAnAnswer(String command) {
        return answering(command);
    }
    private String answering(String command) {
        return answers.getOrDefault(command, "Команда не опознана, проверьте список команд отправив /help");
    }
}
