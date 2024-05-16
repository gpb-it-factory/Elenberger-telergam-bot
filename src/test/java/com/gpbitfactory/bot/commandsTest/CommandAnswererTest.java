package com.gpbitfactory.bot.commandsTest;

import com.gpbitfactory.bot.commands.CommandAnswerer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandAnswererTest {
    @Mock
    Map<String, String> answers;
    @Test
    public void needAnAnswerTest() {
        CommandAnswerer commandAnswerer = new CommandAnswerer(answers);
        when(answers.getOrDefault(anyString(),anyString())).thenReturn("1");
        commandAnswerer.needAnAnswer("");
        verify(answers, times(1)).getOrDefault(anyString(),anyString());
    }

}
