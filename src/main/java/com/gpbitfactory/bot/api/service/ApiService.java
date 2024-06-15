package com.gpbitfactory.bot.api.service;


import org.telegram.telegrambots.meta.api.objects.Message;

public interface ApiService {

    boolean post(Message message);

}
