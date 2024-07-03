package com.gpbitfactory.bot.commands;

import com.gpbitfactory.bot.api.service.AccountService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.NoSuchElementException;

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
        } catch (NoSuchElementException e) {
            return "Укажите название для вашего счета!";
        } catch (IllegalArgumentException f) {
            return "Слишком длинное название счета. Лимит 20 символов";
        } catch (HttpStatusCodeException g) {
            return "Ошибка соединения, попробуйте позже!";
        } catch (RuntimeException h) {
            return "Непредвиденная ошибка";
        }
    }

    @Override
    public String getText() {
        return text;
    }
}
