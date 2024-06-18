package com.gpbitfactory.bot.commands;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandList {

    @Autowired
    private final List<Command> commands = new ArrayList<>();

    public List<Command> getCommands() {
        return commands;
    }
}
