package com.gpbitfactory.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Slf4j
public class PingCommand implements Command {
    private final String text;

    public PingCommand(@Value("/ping") String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String execute(Message message) {
        log.info("Исполняю команду /ping для пользователя: @" + message.getFrom().getUserName());
        return "pong";
    }
}
