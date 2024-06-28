package com.gpbitfactory.bot.commandsTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.gpbitfactory.bot.api.ApiConfig;
import com.gpbitfactory.bot.commands.RegisterCommand;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiConfig.class})
@WireMockTest
@ExtendWith(MockitoExtension.class)
public class RegisterCommandTest {
    private static WireMockServer wireMockServer;

    ApiConfig apiConfig = new ApiConfig();
    @Mock
    Message message;
    @Mock
    User user;

    @BeforeAll
    public static void setUp() {
        WireMockConfiguration configuration = wireMockConfig().port(9999);
        wireMockServer = new WireMockServer(configuration);
        wireMockServer.start();
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void executeSuccessTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(777L);
        when(user.getUserName()).thenReturn("ABOBA");
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/users")).willReturn(aResponse()
                .withStatus(204)
        ));
        RegisterCommand registerCommand = new RegisterCommand("/register",
                apiConfig.userService("http://localhost:" + wireMockServer.port()));
        String expectedMessageText = "Пользователь " + message.getFrom().getUserName() +
                " успешно зарегистрирован";

        String messageText = registerCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }

    @Test
    public void executeErrorTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(777L);
        when(user.getUserName()).thenReturn("ABOBA");
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/users")).willReturn(aResponse()
                .withStatus(404)
        ));
        RegisterCommand registerCommand = new RegisterCommand("/register",
                apiConfig.userService("http://localhost:" + wireMockServer.port()));
        String expectedMessageText = "Непредвиденная ошибка";

        String messageText = registerCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }
}
