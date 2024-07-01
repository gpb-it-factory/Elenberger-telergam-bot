package com.gpbitfactory.bot.commandsTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.gpbitfactory.bot.api.ApiConfig;
import com.gpbitfactory.bot.commands.TransferCommand;
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
public class TransferCommandTest {
    private static WireMockServer wireMockServer;

    ApiConfig apiConfig = new ApiConfig();
    @Mock
    Message message;
    @Mock
    User user;

    TransferCommand transferCommand = new TransferCommand("/transfer",
            apiConfig.transferService("http://localhost:" + wireMockServer.port()));

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
    public void executeOkTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getUserName()).thenReturn("ABOBA");
        when(message.getText()).thenReturn("/transfer name 200.2");
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/transfers"))
                .willReturn(aResponse().withStatus(200)));
        String expectedMessageText = "Перевод прошел успешно";

        String messageText = transferCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }

    @Test
    public void executeNotEnoughWordsTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getUserName()).thenReturn("ABOBA");
        when(message.getText()).thenReturn("/transfer name");
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/transfers"))
                .willReturn(aResponse().withStatus(200)));
        String expectedMessageText = "Недостаточно данных. Проверьте имя отправителя и сумму перевода";

        String messageText = transferCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }

    @Test
    public void executeTooMuchWordsTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getUserName()).thenReturn("ABOBA");
        when(message.getText()).thenReturn("/transfer name 200.2 лишние слова в сообщении");
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/transfers"))
                .willReturn(aResponse().withStatus(200)));
        String expectedMessageText = "Некорректный ввод";

        String messageText = transferCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }

    @Test
    public void executeNoToUserNameTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getUserName()).thenReturn("ABOBA");
        when(message.getText()).thenReturn("/transfer  200.2");
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/transfers"))
                .willReturn(aResponse().withStatus(200)));
        String expectedMessageText = "Укажите имя адресата";

        String messageText = transferCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }

    @Test
    public void executeIncorrectAmountTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getUserName()).thenReturn("ABOBA");
        when(message.getText()).thenReturn("/transfer name .200");
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/transfers"))
                .willReturn(aResponse().withStatus(200)));
        String expectedMessageText = "Некорректная сумма перевода";

        String messageText = transferCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }

    @Test
    public void executeHttpErrorTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getUserName()).thenReturn("ABOBA");
        when(message.getText()).thenReturn("/transfer name 200.2");
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/transfers"))
                .willReturn(aResponse().withStatus(404)));
        String expectedMessageText = "Непредвиденная ошибка сети!";

        String messageText = transferCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }

    @Test
    public void executeErrorTest() {
        String expectedMessageText = "Непредвиденная ошибка!";

        String messageText = transferCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }
}
