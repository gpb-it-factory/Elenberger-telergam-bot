package com.gpbitfactory.bot.commands;

import com.gpbitfactory.bot.api.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
@Slf4j
public class TransferCommand implements Command {

    private final String text;
    private final TransferService transferService;


    public TransferCommand(@Value(("/transfer")) String text, TransferService transferService) {
        this.text = text;
        this.transferService = transferService;
    }

    @Override
    public String execute(Message message) {
        try {
            userIsNull(message.getFrom());
            log.info("Исполняю команду /transfer для пользователя: @" + message.getFrom().getUserName());
            transferService.post(message);
            return "Перевод прошел успешно";
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return e.getMessage();
        } catch (HttpStatusCodeException f) {
            log.error(f.getMessage() +" "+ f.getStatusText());
            return "Непредвиденная ошибка сети!";
        } catch (Exception g) {
            log.error("Непредвиденная ошибка: " +g.getMessage());
            return "Непредвиденная ошибка!";
        }
    }

    @Override
    public String getText() {
        return text;
    }

    private void userIsNull(User user) {
        if (user == null) throw new RuntimeException("User is Null");
    }
}
