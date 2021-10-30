package ru.smaliav.fitnessbot.bot.command.core;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.service.StatsService;

import java.time.LocalDateTime;

// TODO Abstract class doesn't have any abstract methods
//      This should be suspicious, can we use interface instead?
public abstract class BaseCommand extends BotCommand {

    private final StatsService statsService;

    public BaseCommand(String commandId, String description, StatsService statsService) {
        super(commandId, description);
        this.statsService = statsService;
    }

    protected void logUsedCommand(FitnessUser fUser) {
        fUser.setLastUsed(LocalDateTime.now());
        statsService.registerLastUsage(fUser);
    }

}
