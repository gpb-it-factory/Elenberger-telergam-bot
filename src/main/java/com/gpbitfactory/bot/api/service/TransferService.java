package com.gpbitfactory.bot.api.service;

import com.gpbitfactory.bot.api.model.TransferDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.regex.Pattern;

@Service
@Slf4j
public class TransferService implements ApiService {
    private final RestClient restClient;

    @Autowired
    public TransferService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void post(Message message) {
        TransferDTO transferDTO = createTransferDto(message);
        restClient.post().uri("/api/v1/transfers")
                .body(transferDTO)
                .retrieve().toEntity(String.class);
        log.info("Перевод от " + transferDTO.from()
                + " для " + transferDTO.to()
                + " на сумму " + transferDTO.amount()
                + "прошел успешно!");
    }

    private TransferDTO createTransferDto(Message message) {
        String[] messageText = message.getText().split(" ");
        if (messageText.length < 3)
            throw new IllegalArgumentException("Недостаточно данных. Проверьте имя отправителя и сумму перевода");
        if (messageText.length > 3) throw new IllegalArgumentException("Некорректный ввод");
        if (messageText[1].isBlank()) throw new IllegalArgumentException("Укажите имя адресата");
        if (messageText[2].isBlank()) throw new IllegalArgumentException("Укажите сумму перевода");
        if (isCorrectNumber(messageText[2])) {
            String from = message.getFrom().getUserName();
            String to = messageText[1];
            String amount = messageText[2];
            return new TransferDTO(from, to, amount);
        } else throw new IllegalArgumentException("Некорректная сумма перевода");
    }

    private boolean isCorrectNumber(String number) {
        String pattern = "^\\d{1,64}(\\.\\d{1,2})?$";
        return Pattern.matches(pattern, number);
    }
}
