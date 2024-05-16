package com.gpbitfactory.bot.configuration;

import com.gpbitfactory.bot.captor.CommandCaptor;
import com.gpbitfactory.bot.commands.CommandAnswerer;
import com.gpbitfactory.bot.telegrambot.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BotConfig {
    @Bean
    public Map<String, String> answers() {
        Map<String,String> answers = new HashMap<>();
        answers.put("/start","Готов к работе!");
        answers.put("/ping", "pong");
        answers.put("/help", "Сейчас доступны только команды /help, но вы уже догадались её использовать и команда /ping");
        return answers;
    }
    @Bean
    public CommandAnswerer commandAnswerer() {
        return new CommandAnswerer(answers());
    }
    @Bean
    public CommandCaptor commandCaptor() {
        return new CommandCaptor(commandAnswerer());
    }
    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(commandCaptor());
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
