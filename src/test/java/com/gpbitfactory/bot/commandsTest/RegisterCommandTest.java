package com.gpbitfactory.bot.commandsTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.gpbitfactory.bot.api.ApiConfig;
import com.gpbitfactory.bot.commands.RegisterCommand;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@WireMockTest
@Import(ApiConfig.class)
@ExtendWith(MockitoExtension.class)
public class RegisterCommandTest {

    private static WireMockServer wireMockServer;
    ApiConfig apiConfig = new ApiConfig();
    @Mock
    Message message;
    @Mock
    User user;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8088);

    @BeforeAll
    public static void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
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
        stubFor(post(urlEqualTo("/api/v1/users")).willReturn(aResponse()
                .withStatus(204)
                ));
        RegisterCommand registerCommand = new RegisterCommand("/register", apiConfig.userService("http://localhost:"+wireMockServer.port()));
        Assertions.assertEquals(registerCommand.execute(message), "Пользователь " + message.getFrom().getUserName() + " успешно зарегистрирован");
    }

    @Test
    public void executeErrorTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(777L);
        when(user.getUserName()).thenReturn("ABOBA");
        stubFor(post(urlEqualTo("/api/v1/users")).willReturn(aResponse()
                .withStatus(404)
        ));
        RegisterCommand registerCommand = new RegisterCommand("/register", apiConfig.userService("http://localhost:"+wireMockServer.port()));
        Assertions.assertEquals(registerCommand.execute(message), "Непредвиденная ошибка");
    }
}
