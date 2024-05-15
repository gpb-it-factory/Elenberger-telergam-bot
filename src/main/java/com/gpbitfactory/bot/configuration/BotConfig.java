package com.gpbitfactory.bot.configuration;

import com.gpbitfactory.bot.telegrambot.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {
    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot();
    }
    @Bean
    public TelegramBotsApi telegramBotsApi() {
        TelegramBotsApi botsApi;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot());
            return botsApi;
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Ошибка при инициализации телеграм-бота", e);
        }

    }
}
