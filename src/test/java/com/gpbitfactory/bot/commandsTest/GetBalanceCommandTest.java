package com.gpbitfactory.bot.commandsTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.gpbitfactory.bot.api.ApiConfig;
import com.gpbitfactory.bot.commands.GetBalanceCommand;
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
public class GetBalanceCommandTest {
    private static WireMockServer wireMockServer;

    ApiConfig apiConfig = new ApiConfig();
    @Mock
    Message message;
    @Mock
    User user;

    private final GetBalanceCommand getBalanceCommand = new GetBalanceCommand("/getbalance",
            apiConfig.accountService("http://localhost:" + wireMockServer.port()));
    String jsonResponse = """
            [
              {
                "accountId": "52d2ef91-0b62-4d43-bb56-e7ec542ba8f8",
                "accountName": "Деньги на шашлык",
                "amount": "3228"
              }
            ]""";

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
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/users/777/accounts"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(jsonResponse)
                        .withHeader("Content-Type", "application/json")));

        String expectedMessageText = "Баланс ваших счетов:\nДеньги на шашлык: 3228 руб.\n";

        String messageText = getBalanceCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }

    @Test
    public void executeHttpError4xxTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(777L);
        when(user.getUserName()).thenReturn("ABOBA");
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/users/777/accounts"))
                .willReturn(aResponse()
                        .withStatus(404)));
        String expectedMessageText = "Счет не найден!";

        String messageText = getBalanceCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }

    @Test
    public void executeAnyOtherHttpErrorTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(777L);
        when(user.getUserName()).thenReturn("ABOBA");
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/users/777/accounts"))
                .willReturn(aResponse()
                        .withStatus(500)));
        String expectedMessageText = "Непредвиденная ошибка сети!";

        String messageText = getBalanceCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }

    @Test
    public void executeErrorTest() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(777L);
        when(user.getUserName()).thenReturn("ABOBA");
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/users/777/accounts"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("азазазаз")));
        String expectedMessageText = "Непредвиденная ошибка!";

        String messageText = getBalanceCommand.execute(message);

        Assertions.assertEquals(expectedMessageText, messageText);
    }
}
