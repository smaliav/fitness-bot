package ru.smaliav.fitnessbot.bot.command.core;

import ru.smaliav.fitnessbot.bot.command.action.ActionResult;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.service.StatsService;
import ru.smaliav.fitnessbot.exception.InvalidArgumentException;

import java.time.format.DateTimeParseException;

public abstract class BaseActionCommand extends BaseCommand {

    public BaseActionCommand(String commandId, String description, StatsService statsService) {
        super(commandId, description, statsService);
    }

    protected abstract IAction determineAction(String[] args);

    protected ActionResult performAction(FitnessUser fitnessUser, String[] args) {
        IAction action = determineAction(args);
        ActionResult actionResult = new ActionResult(action);

        try {
            actionResult = performAction(fitnessUser, args, action);
        } catch (InvalidArgumentException e) {
            actionResult.setUnsuccessful("Недопустимый текст, ознакомьтесь с командами /help");
        } catch (NumberFormatException e) {
            actionResult.setUnsuccessful("Неверный формат числа!");
        } catch (DateTimeParseException e) {
            actionResult.setUnsuccessful("Неверный формат даты! Попробуй ДД.ММ.ГГГГ");
        }

        return actionResult;
    }

    protected abstract ActionResult performAction(FitnessUser fitnessUser, String[] args, IAction action);

}
