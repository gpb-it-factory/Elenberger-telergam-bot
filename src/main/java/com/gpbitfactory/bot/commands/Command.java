package com.gpbitfactory.bot.commands;


import org.telegram.telegrambots.meta.api.objects.Message;

public interface Command {
    String execute(Message message);
    String getText();
}
