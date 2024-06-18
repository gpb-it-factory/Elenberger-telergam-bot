package com.gpbitfactory.bot.telegrambot;

import com.gpbitfactory.bot.captor.CommandCaptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final String botName;
    private final String botToken;
    private final CommandCaptor commandCaptor;

    @Autowired
    public TelegramBot(@Value("${bot.Name}") String botName,
                       @Value("${bot.Token}") String botToken, CommandCaptor commandCaptor) {
        this.botName = botName;
        this.botToken = botToken;
        this.commandCaptor = commandCaptor;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message anotherMessage = update.getMessage();
        log.info("Апдейт от пользователя " + anotherMessage.getFrom().getUserName() +
                " c сообщением:" + anotherMessage.getText());
        sendMessage(anotherMessage.getChatId(), commandCaptor.answer(anotherMessage));
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onRegister() {
        System.out.println("Регистрация прошла!");
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        try {
            execute(message);
            log.info("Отправил ответ для " + chatId);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
