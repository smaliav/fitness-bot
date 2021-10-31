package ru.smaliav.fitnessbot.bot.command.core;

import lombok.extern.slf4j.Slf4j;
import ru.smaliav.fitnessbot.business.service.StatsService;

@Slf4j
public abstract class BaseInfoCommand extends BaseCommand {

    public BaseInfoCommand(String commandId, String description, StatsService statsService) {
        super(commandId, description, statsService);
    }

}
