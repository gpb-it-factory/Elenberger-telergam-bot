package com.gpbitfactory.bot.commands;

import com.gpbitfactory.bot.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Slf4j
public class RegisterCommand implements Command {
    private final String text;
    private final UserService userService;

    public RegisterCommand(@Value("/register") String text, @Autowired UserService userService) {
        this.text = text;
        this.userService = userService;
    }

    @Override
    public String execute(Message message) {
        log.info("Исполняю команду /register для пользователя: @" + message.getFrom().getUserName());
        try {
            userService.post(message);
            return "Пользователь " + message.getFrom().getUserName() + " успешно зарегистрирован";
        } catch (HttpClientErrorException e){
            if (e.getStatusCode().value() == 409) return "Вы уже зарегистрированы!";
            return "Непредвиденная ошибка сети";
        }
        catch (RuntimeException f) {
            return "Непредвиденная ошибка";
        }
    }

    @Override
    public String getText() {
        return text;
    }
}
