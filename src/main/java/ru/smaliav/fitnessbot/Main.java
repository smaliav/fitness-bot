package ru.smaliav.fitnessbot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;

@Slf4j
public class Main {

    private static final Map<String, String> env = System.getenv();

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new FitnessBot(env.get("BOT_NAME"), env.get("BOT_TOKEN")));
        } catch (TelegramApiException e) {
            log.error("Error occurred while registering Fitness Bot", e);
        }
    }

}
