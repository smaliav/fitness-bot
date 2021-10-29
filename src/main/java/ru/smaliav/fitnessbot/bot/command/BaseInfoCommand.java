package ru.smaliav.fitnessbot.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public abstract class BaseInfoCommand extends BotCommand {

    public BaseInfoCommand(String commandId, String description) {
        super(commandId, description);
    }

    void sendMessage(AbsSender absSender, String userName, String chatId, String commandName, String text) {
        log.info("Processing - User: {}, Command: {}", userName, commandName);
        SendMessage message = new SendMessage();

        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(text);

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred while processing '{}' command for {}", commandName, userName, e);
        }
    }

}
