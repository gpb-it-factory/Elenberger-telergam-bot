package com.gpbitfactory.bot.command;

import org.telegram.telegrambots.meta.api.objects.Message;

public class CommandCaptor {
    public String answer(Message message) {
        return messageCaption(message);
    }

    private String messageCaption(Message message) {
        if (message.hasText() && message.isCommand()) {
            Long id = message.getChatId();
            switch (message.getText()) {
                case "/ping" -> {
                    return pong();
                }
                case "/help" -> {
                    return helpMe();
                }
                case "/start" -> {
                    return started();
                }
                default -> {
                    return iDontKnow();
                }
            }
        } else {
            return "Не похоже на команду";
        }
    }
    private String started(){return "Готов к работе!";}
    private String pong() {
        return "pong";
    }

    private String iDontKnow() {
        return "Пока я не знаю что ответить на такой запрос, но скоро научусь!";
    }

    private String helpMe() {
        return "Сейчас доступны только команды /help, но вы уже догадались её использовать и команда /ping";
    }
}
