package ru.smaliav.fitnessbot.bot.command.core;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.service.StatsService;
import ru.smaliav.fitnessbot.util.Utils;

import java.io.File;
import java.time.LocalDateTime;

// TODO Abstract class doesn't have any abstract methods
//      This should be suspicious, can we use interface instead?
@Slf4j
public abstract class BaseCommand extends BotCommand {

    private final StatsService statsService;

    public BaseCommand(String commandId, String description, StatsService statsService) {
        super(commandId, description);
        this.statsService = statsService;
    }

    protected void sendMessage(AbsSender absSender, FitnessUser fUser, String commandName, String[] args, String text) {
        String userName = Utils.extractUserName(fUser);
        log.info("Processing - user: {}, command: {} {}", userName, commandName, args);

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

    protected void sendPhoto(AbsSender absSender, FitnessUser fitnessUser, File file) {
        SendPhoto photo = new SendPhoto();
        photo.setChatId(fitnessUser.getChatId().toString());
        photo.setPhoto(new InputFile(file));

        try {
            absSender.execute(photo);
            file.delete();
        } catch (TelegramApiException e) {
            log.error("Error occurred while sending photo to {}", Utils.extractUserName(fitnessUser));
        }
    }

    protected void logUsedCommand(FitnessUser fUser) {
        fUser.setLastUsed(LocalDateTime.now());
        statsService.registerLastUsage(fUser);
    }

}
