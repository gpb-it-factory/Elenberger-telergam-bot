package com.gpbitfactory.bot.configuration;

import com.gpbitfactory.bot.captor.CommandCaptor;
import com.gpbitfactory.bot.commands.*;
import com.gpbitfactory.bot.logger.BotLogger;
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
    public Map<String, Command> answers() {
        Map<String, Command> answers = new HashMap<>();
        BotLogger botLogger = new BotLogger();
        StartCommand startCommand = new StartCommand(botLogger);
        PingCommand pingCommand = new PingCommand(botLogger);
        HelpCommand helpCommand = new HelpCommand(botLogger);
        answers.put("/start", startCommand);
        answers.put("/ping", pingCommand);
        answers.put("/help", helpCommand);
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
