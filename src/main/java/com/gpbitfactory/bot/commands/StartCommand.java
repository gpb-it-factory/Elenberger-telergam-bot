package com.gpbitfactory.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Slf4j
public class StartCommand implements Command {
    private final String text;

    public StartCommand(@Value("/start") String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String execute(Message message) {
        log.info("Исполняю команду /start для пользователя: @" + message.getFrom().getUserName());
        return "Готов к работе!";
    }
}
