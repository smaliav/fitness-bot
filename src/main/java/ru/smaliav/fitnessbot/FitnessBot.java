package ru.smaliav.fitnessbot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.smaliav.fitnessbot.command.HelpCommand;
import ru.smaliav.fitnessbot.command.StartCommand;

@Slf4j
public class FitnessBot extends TelegramLongPollingCommandBot {

    private final String botName;
    private final String botToken;

    public FitnessBot(String botName, String botToken) {
        super();
        this.botName = botName;
        this.botToken = botToken;

        register(new StartCommand());
        register(new HelpCommand());
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message request = update.getMessage();
        SendMessage answer = new SendMessage(request.getChatId().toString(), "Недопустимый текст, ознакомьтесь с командами /help");

        try {
            execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred while processing nonCommandUpdate", e);
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

}
