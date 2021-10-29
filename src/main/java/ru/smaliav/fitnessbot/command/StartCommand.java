package ru.smaliav.fitnessbot.command;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class StartCommand extends BotCommand {

    private static final String COMMAND_ID = "start";
    private static final String COMMAND_DESCRIPTION = "Starts a char with Fitness Bot";

    public StartCommand() {
        super(COMMAND_ID, COMMAND_DESCRIPTION);
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.enableMarkdown(true);
        answer.setChatId(chat.getId().toString());
        answer.setText("Привет, погнали! Для подробного описания команд используй /help");

        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred while processing /start command");
            throw e;
        }
    }

}
