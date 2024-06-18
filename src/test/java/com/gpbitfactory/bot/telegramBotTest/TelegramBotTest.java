package com.gpbitfactory.bot.telegramBotTest;

import com.gpbitfactory.bot.captor.CommandCaptor;
import com.gpbitfactory.bot.telegrambot.TelegramBot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TelegramBotTest {

    @InjectMocks
    Update update;
    @Mock
    CommandCaptor commandCaptor;
    @Mock
    Message message;
    @Mock
    User user;

    @Test
     public void onUpdateReceivedTest() {
        TelegramBot bot = new TelegramBot("1","2",commandCaptor);
        when(message.getFrom()).thenReturn(user);
        when(user.getUserName()).thenReturn("ABOBA");
        when(commandCaptor.answer(message)).thenReturn("1");
        when(message.getChatId()).thenReturn(10L);
        bot.onUpdateReceived(update);
        verify(commandCaptor, times (1)).answer(any(Message.class));
     }
}
