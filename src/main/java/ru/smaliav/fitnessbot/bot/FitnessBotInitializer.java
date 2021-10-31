package ru.smaliav.fitnessbot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class FitnessBotInitializer {

    @Autowired
    public FitnessBotInitializer(FitnessBot fitnessBot) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(fitnessBot);
        } catch (TelegramApiException e) {
            log.error("Error occurred while starting Fitness Bot", e);
        }
    }

}
