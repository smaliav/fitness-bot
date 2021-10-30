package ru.smaliav.fitnessbot.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.smaliav.fitnessbot.bot.command.core.BaseCommand;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.service.StatsService;
import ru.smaliav.fitnessbot.util.Utils;

@Slf4j
public abstract class BaseInfoCommand extends BaseCommand {

    public BaseInfoCommand(String commandId, String description, StatsService statsService) {
        super(commandId, description, statsService);
    }

    void sendMessage(AbsSender absSender, FitnessUser fUser, String commandName, String text) {
        String userName = Utils.extractUserName(fUser);
        log.info("Processing - User: {}, Command: {}", userName, commandName);

        // Creating a message to send
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(fUser.getChatId().toString());
        message.setText(text);

        // In order to save Fitness Bot statistics
        logUsedCommand(fUser);

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred while processing '{}' command for {}", commandName, userName, e);
        }
    }

}
