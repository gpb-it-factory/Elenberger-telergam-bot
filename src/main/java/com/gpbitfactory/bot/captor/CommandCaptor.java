package com.gpbitfactory.bot.captor;

import com.gpbitfactory.bot.commands.CommandAnswerer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class CommandCaptor {
    private final CommandAnswerer answerer;

    @Autowired
    public CommandCaptor(CommandAnswerer answerer) {
        this.answerer = answerer;
    }

    public String answer(Message message) {
        return answerer.answering(message);
    }
}
