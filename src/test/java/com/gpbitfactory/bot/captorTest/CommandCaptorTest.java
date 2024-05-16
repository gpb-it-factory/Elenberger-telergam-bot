package com.gpbitfactory.bot.captorTest;

import com.gpbitfactory.bot.captor.CommandCaptor;
import com.gpbitfactory.bot.commands.CommandAnswerer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.anyString;
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
        when(commandAnswerer.needAnAnswer(anyString())).thenReturn("");
        CommandCaptor commandCaptor = new CommandCaptor(commandAnswerer);
        commandCaptor.answer(message);
        verify(commandAnswerer, times(1)).needAnAnswer(anyString());
        verify(message, times(1)).getText();
    }
}
