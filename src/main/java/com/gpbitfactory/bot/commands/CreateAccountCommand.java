package com.gpbitfactory.bot.commands;

import com.gpbitfactory.bot.api.service.AccountService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Slf4j
public class CreateAccountCommand implements Command {

    private final String text;
    private final AccountService accountService;

    public CreateAccountCommand(@Value("/createaccount") String text, AccountService accountService) {
        this.text = text;
        this.accountService = accountService;
    }
    @Override
    public String execute(@NonNull Message message) {
        log.info("Исполняю команду /createaccount для пользователя: @" + message.getFrom().getUserName());
        try {
            accountService.post(message);
            return "Пользователь " + message.getFrom().getUserName() + " успешно зарегистрирован";
        } catch (RuntimeException e) {
            return "Непредвиденная ошибка";
        }
    }

    @Override
    public String getText() {
        return text;
    }
}
