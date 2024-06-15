package com.gpbitfactory.bot.configuration;

import com.gpbitfactory.bot.captor.CommandCaptor;
import com.gpbitfactory.bot.commands.Command;
import com.gpbitfactory.bot.commands.CommandAnswerer;
import com.gpbitfactory.bot.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class BotConfig {
    @Value("${bot.Name}")
    String botName;
    @Value("${bot.Token}")
    String botToken;

    @Autowired
    private List<Command> commandList;

    @Bean
    public Map<String, Command> commandMap() {
        List<Command> list = commandList;
        Map<String, Command> answers = new HashMap<>();
        for (Command command : list) {
            answers.put(command.getText(), command);
        }
        return answers;
    }

    @Bean
    public CommandAnswerer commandAnswerer() {
        return new CommandAnswerer(commandMap());
    }

    @Bean
    public CommandCaptor commandCaptor() {
        return new CommandCaptor(commandAnswerer());
    }

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(botName, botToken, commandCaptor());
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        TelegramBotsApi botsApi;
        TelegramBot telegramBot = telegramBot();
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
            return botsApi;
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Ошибка при инициализации телеграм-бота", e);
        }

    }

}
