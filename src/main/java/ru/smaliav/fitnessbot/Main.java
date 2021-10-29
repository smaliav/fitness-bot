package ru.smaliav.fitnessbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;

@Slf4j
@SpringBootApplication
public class Main {

    private static final Map<String, String> env = System.getenv();

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new FitnessBot(env.get("BOT_NAME"), env.get("BOT_TOKEN")));
        } catch (TelegramApiException e) {
            log.error("Error occurred in Fitness Bot", e);
        }
    }

}
