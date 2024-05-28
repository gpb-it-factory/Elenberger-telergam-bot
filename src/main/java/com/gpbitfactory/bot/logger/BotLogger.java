package com.gpbitfactory.bot.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BotLogger {
    private static final Logger logger = LoggerFactory.getLogger(BotLogger.class);

    public void logMessage(String message) {
        logger.info(message);
    }
}
