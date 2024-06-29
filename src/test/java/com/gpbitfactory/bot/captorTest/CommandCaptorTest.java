package com.gpbitfactory.bot.captorTest;

import com.gpbitfactory.bot.captor.CommandCaptor;
import com.gpbitfactory.bot.commands.CommandAnswerer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommandCaptorTest {
    @Mock
    CommandAnswerer answerer;

    @Test
    public void answerTest() {
        CommandCaptor captor = new CommandCaptor(answerer);
        when(answerer.answering(any(Message.class))).thenReturn("1");
        Assertions.assertEquals(captor.answer(new Message()), "1");
    }
}
