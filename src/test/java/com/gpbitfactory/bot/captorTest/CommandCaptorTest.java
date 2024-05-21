package com.gpbitfactory.bot.captorTest;

import com.gpbitfactory.bot.captor.CommandCaptor;
import com.gpbitfactory.bot.commands.CommandAnswerer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandCaptorTest {
    @Mock
    Message message;
    @Mock
    CommandAnswerer commandAnswerer;
    @Test
    public void messageCaptionTest() {
        when(message.getText()).thenReturn("");
        when(commandAnswerer.answering(any(Message.class))).thenReturn("");
        CommandCaptor commandCaptor = new CommandCaptor(commandAnswerer);
        commandCaptor.answer(message);
        verify(commandAnswerer, times(1)).answering(any(Message.class));
        verify(message, times(1)).getText();
    }
}
