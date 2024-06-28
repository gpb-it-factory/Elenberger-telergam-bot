package com.gpbitfactory.bot.configuration;

import com.gpbitfactory.bot.captor.CommandCaptor;
import com.gpbitfactory.bot.commands.Command;
import com.gpbitfactory.bot.commands.CommandAnswerer;
import com.gpbitfactory.bot.commands.CommandList;
import com.gpbitfactory.bot.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Configuration
public class BotConfig {
    @Value("${bot.Name}")
    private String botName;
    @Value("${bot.Token}")
    private String botToken;

    @Bean
    public CommandList commandList() {
        return new CommandList();
    }

    @Bean
    public Map<String, Command> commandMap() {
        List<Command> list = commandList().getCommands();
        if (list.isEmpty()) {
            throw new NoSuchElementException();
        }
        Map<String, Command> answers = new HashMap<>();
        for (Command command : list) {
            answers.put(command.getText(), command);
        }
        return answers;
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        TelegramBot telegramBot = new TelegramBot(botName, botToken,
                new CommandCaptor(new CommandAnswerer(commandMap())));
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
            return botsApi;
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Ошибка при инициализации телеграм-бота", e);
        }

    }

}
