package com.gpbitfactory.bot.commandsTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.gpbitfactory.bot.api.ApiConfig;
import com.gpbitfactory.bot.commands.CreateAccountCommand;
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
public class CreateAccountCommandTest {
    private static WireMockServer wireMockServer;

    ApiConfig apiConfig = new ApiConfig();
    @Mock
    Message message;
    @Mock
    User user;

    CreateAccountCommand createAccountCommand = new CreateAccountCommand("/createaccount",
            apiConfig.accountService("http://localhost:" + wireMockServer.port()));

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
        when(message.getText()).thenReturn("/createaccount name");
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/users/accounts"))
                .willReturn(aResponse().withStatus(204)));
        String expectedMessageText = "Пользователь " + message.getFrom().getUserName() + " успешно зарегистрировал новый счет";

        String messageText = createAccountCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }

    @Test
    public void executeHttpErrorTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(777L);
        when(user.getUserName()).thenReturn("ABOBA");
        when(message.getText()).thenReturn("/createaccount name");
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/users/accounts"))
                .willReturn(aResponse().withStatus(404)));
        String expectedMessageText = "Ошибка соединения, попробуйте позже!";

        String messageText = createAccountCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }

    @Test
    public void executeNoSuchElementTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(777L);
        when(user.getUserName()).thenReturn("ABOBA");
        when(message.getText()).thenReturn("/createaccount");
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/users/accounts"))
                .willReturn(aResponse().withStatus(404)));
        String expectedMessageText = "Укажите название для вашего счета!";

        String messageText = createAccountCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }

    @Test
    public void executeIllegalArgumentTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(777L);
        when(user.getUserName()).thenReturn("ABOBA");
        when(message.getText()).thenReturn("/createaccount thereis20symbolsplus1");
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/users/accounts"))
                .willReturn(aResponse().withStatus(404)));
        String expectedMessageText = "Слишком длинное название счета. Лимит 20 символов";

        String messageText = createAccountCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }

    @Test
    public void executeErrorTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(777L);
        when(user.getUserName()).thenReturn("ABOBA");
        when(message.getText()).thenReturn(null);
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/users/accounts"))
                .willReturn(aResponse().withStatus(404)));
        String expectedMessageText = "Непредвиденная ошибка";

        String messageText = createAccountCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }
}
