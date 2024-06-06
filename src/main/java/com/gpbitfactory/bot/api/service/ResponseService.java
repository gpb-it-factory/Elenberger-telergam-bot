package com.gpbitfactory.bot.api.service;

import com.gpbitfactory.bot.logger.BotLogger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ResponseService {
    private final BotLogger botLogger;
    private final RestTemplate restTemplate;

    public ResponseService(BotLogger botLogger, RestTemplate restTemplate) {
        this.botLogger = botLogger;
        this.restTemplate = restTemplate;
    }


}
