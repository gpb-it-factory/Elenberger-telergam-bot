package com.gpbitfactory.bot.api.service;

import com.gpbitfactory.bot.api.model.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@Slf4j
public class UserService implements ApiService {
    private final RestClient restClient;

    @Autowired
    public UserService(@Qualifier("middleServiceRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void post(Message message) {
        log.info("Начал post-запрос для пользователя @" + message.getFrom().getUserName());
            restClient.post()
                    .uri("/api/v1/users")
                    .body(new UserInfoDto(message.getFrom().getId(), message.getFrom().getUserName()))
                    .retrieve().toEntity(String.class);
            log.info("Ответ получен");
    }

}
