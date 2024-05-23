package com.gpbitfactory.bot.configurationTest;

import com.gpbitfactory.bot.commands.Command;
import com.gpbitfactory.bot.configuration.BotConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class BotConfigTest {

    BotConfig botConfig = new BotConfig();

    @Test
    public void answersTest() {
        Map<String, Command> answers = botConfig.answers();
        Assertions.assertTrue(answers.containsKey("/start"));
        Assertions.assertTrue(answers.containsKey("/ping"));
        Assertions.assertTrue(answers.containsKey("/help"));
    }
}
