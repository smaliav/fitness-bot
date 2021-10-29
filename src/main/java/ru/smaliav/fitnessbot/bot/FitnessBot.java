package ru.smaliav.fitnessbot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.smaliav.fitnessbot.bot.command.CommandStore;

@Slf4j
@Component
public class FitnessBot extends TelegramLongPollingCommandBot {

    private final String botName;
    private final String botToken;

    @Autowired
    public FitnessBot(Environment env, CommandStore commandStore) {
        super();
        this.botName = env.getProperty("BOT_NAME");
        this.botToken = env.getProperty("BOT_TOKEN");

        register(commandStore.getStartCommand());
        register(commandStore.getHelpCommand());
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
