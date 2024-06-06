package com.gpbitfactory.bot.configuration;

import com.gpbitfactory.bot.api.service.CommandService;
import com.gpbitfactory.bot.captor.CommandCaptor;
import com.gpbitfactory.bot.commands.*;
import com.gpbitfactory.bot.logger.BotLogger;
import com.gpbitfactory.bot.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class BotConfig {
    @Value("${bot.Name}")
    String botName;
    @Value("${bot.Token}")
    String botToken;


    @Bean
    public List<Command> commandList() {
        BotLogger botLogger = new BotLogger();
        ArrayList<Command> list = new ArrayList<>();
        //Начало списка активных команд
        list.add(new StartCommand("/start", botLogger));
        list.add(new PingCommand("/ping", botLogger));
        list.add(new HelpCommand("/help", botLogger));
        list.add(new RegisterCommand("/register", botLogger, commandService()));
        //Сюда добавлять новые команды
        return list;
    }

    @Bean
    public Map<String, Command> answers() {
        List<Command> list = commandList();
        Map<String, Command> answers = new HashMap<>();
        for (Command command : list) {
            answers.put(command.getText(), command);
        }
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

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder,@Value("${middleService.url}") String backUri) {
        return builder
                .rootUri(backUri)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Bean
    public CommandService commandService() {
        BotLogger botLogger = new BotLogger();
        return new CommandService(restTemplate(restTemplateBuilder(), null), botLogger);
    }

}
