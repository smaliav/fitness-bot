package ru.smaliav.fitnessbot.bot.command.core;

import ru.smaliav.fitnessbot.bot.command.action.ActionResult;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.service.StatsService;

public abstract class BaseActionCommand extends BaseCommand {

    public BaseActionCommand(String commandId, String description, StatsService statsService) {
        super(commandId, description, statsService);
    }

    protected abstract ActionResult performAction(FitnessUser fitnessUser, String[] args) throws Exception;

}
