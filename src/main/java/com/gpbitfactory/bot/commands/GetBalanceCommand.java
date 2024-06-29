package com.gpbitfactory.bot.commands;

import com.gpbitfactory.bot.api.model.AccountInfoDTO;
import com.gpbitfactory.bot.api.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class GetBalanceCommand implements Command {

    private final String text;
    private final AccountService accountService;

    public GetBalanceCommand(@Value("/getbalance") String text, AccountService accountService) {
        this.text = text;
        this.accountService = accountService;
    }


    @Override
    public String execute(Message message) {
        log.info("Исполняю команду /getbalance для пользователя: @" + message.getFrom().getUserName());
        try {
            ResponseEntity<List<AccountInfoDTO>> response = accountService.get(message.getFrom().getId());
            return buildMessageText(Objects.requireNonNull(response.getBody()));
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().is4xxClientError()) return "Счет не найден!";
            return "Непредвиденная ошибка сети!";
        } catch (Exception f) {
            return "Непредвиденная ошибка!";
        }
    }

    @Override
    public String getText() {
        return text;
    }

    private String buildMessageText(List<AccountInfoDTO> list) {
        StringBuilder builder = new StringBuilder();
        builder.append("Баланс ваших счетов:\n");
        for (AccountInfoDTO accountInfoDTO : list) {
            builder.append(accountInfoDTO.accountName())
                    .append(": ")
                    .append(accountInfoDTO.amount())
                    .append(" руб.\n");
        }
        return builder.toString();
    }
}
