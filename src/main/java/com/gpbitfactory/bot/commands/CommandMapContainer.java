package com.gpbitfactory.bot.commands;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CommandMapContainer {

    private final Map<String, Command> commandMap;

    @Autowired
    public CommandMapContainer(List<Command> commands) {
        this.commandMap = commands.stream()
                .collect(Collectors.toMap(Command::getText, command -> command));
    }

    @PostConstruct
    public void init() {
        System.out.println("Доступные команды:");
        commandMap.forEach((text, command) -> {
            System.out.println("Доступна команда " + text);
        });
    }
    public Map<String, Command> getCommandMap() {
        return commandMap;
    }
    public Command getCommand(String text) {
        return commandMap.get(text);
    }
}
