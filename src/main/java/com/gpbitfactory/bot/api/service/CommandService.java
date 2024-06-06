package com.gpbitfactory.bot.api.service;

import com.gpbitfactory.bot.api.model.RegisterRequestDto;
import com.gpbitfactory.bot.logger.BotLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class CommandService {
    private final RestTemplate restTemplate;
    private final BotLogger botLogger;

    @Autowired
    public CommandService(RestTemplate restTemplate, BotLogger botLogger) {
        this.restTemplate = restTemplate;
        this.botLogger = botLogger;
    }

    public ResponseEntity<String> registerExecute(Message message) {
        botLogger.logMessage("Начал регистрацию");
        RegisterRequestDto requestDto = new RegisterRequestDto(message.getFrom().getId(), message.getFrom().getUserName());
        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/id", requestDto, String.class);
        botLogger.logMessage("Ответ получен");
        return response;
    }

}
