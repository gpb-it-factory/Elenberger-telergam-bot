package com.gpbitfactory.bot.api.service;

import com.gpbitfactory.bot.api.model.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@Slf4j
public class UserService implements ApiService {
    private final RestTemplate restTemplate;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void post(Message message) {
        log.info("Начал post-запрос для пользователя @" + message.getFrom().getUserName());
        try {
            restTemplate.postForEntity("/api/v1/users",
                    new UserInfoDto(message.getFrom().getId(), message.getFrom().getUserName()), String.class);
            log.info("Ответ получен");
        } catch (HttpStatusCodeException e) {
            log.info("Ответ не получен");
            throw new RuntimeException("Ответ не получен");
        }
    }

}
