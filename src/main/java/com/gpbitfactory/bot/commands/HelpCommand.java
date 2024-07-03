package com.gpbitfactory.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Slf4j
public class HelpCommand implements Command {
    private final String text;

    public HelpCommand(@Value("/help") String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String execute(Message message) {
        log.info("Исполняю команду /help для пользователя: @" + message.getFrom().getUserName());
        return """
                Доступные команды:
                /help - список доступных команд
                /register - регистрация в мини-банке
                /createaccount - создать счет в мини-банке "/createaccount [название счета не более 20-ти символов]
                /ping - пасхалка""";

    }
}
