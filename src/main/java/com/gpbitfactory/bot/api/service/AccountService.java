package com.gpbitfactory.bot.api.service;

import com.gpbitfactory.bot.api.model.AccountInfoDTO;
import com.gpbitfactory.bot.api.model.AccountRegisterDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class AccountService implements ApiService {
    private final RestClient restClient;

    @Autowired
    public AccountService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void post(Message message) {
        AccountRegisterDto accountRegisterDTO = createDto(message);
        log.info("Начал post-запрос для пользователя @" + message.getFrom().getUserName());
        postToBack(accountRegisterDTO);
    }

    private AccountRegisterDto createDto(Message message) {
        long id = message.getFrom().getId();
        String[] messageText = message.getText().split(" ");
        checkNotNullName(messageText);
        StringBuilder name = new StringBuilder();
        for (int i = 1; i < messageText.length; i++) {
            name.append(messageText[i]).append(" ");
        }
        checkNameLength(name);
        return new AccountRegisterDto(id, name.toString());
    }

    private void checkNotNullName(String[] messageText) {
        if (messageText.length < 2) {
            throw new NoSuchElementException();
        }
    }

    private void checkNameLength(StringBuilder builder) {
        if (builder.length() > 20) {
            throw new IllegalArgumentException();
        }
    }

    private void postToBack(AccountRegisterDto accountRegisterDTO) {
        try {
            restClient.post()
                    .uri("/api/v1/users/accounts")
                    .body(accountRegisterDTO)
                    .retrieve().toEntity(String.class);
            log.info("Ответ получен");
        } catch (HttpStatusCodeException e) {
            log.info("Ответ не получен");
            throw e;
        }
    }

    public ResponseEntity<List<AccountInfoDTO>> get(Long id) {
        return restClient.get()
                .uri("/api/v1/users/" + id + "/accounts")
                .retrieve().toEntity(new ParameterizedTypeReference<>() {
                });
    }
}
