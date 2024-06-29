package com.gpbitfactory.bot.configuration;

import com.gpbitfactory.bot.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

    private final TelegramBot telegramBot;

    @Autowired
    public BotConfig(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
            return botsApi;
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Ошибка при инициализации телеграм-бота", e);
        }
    }

}
