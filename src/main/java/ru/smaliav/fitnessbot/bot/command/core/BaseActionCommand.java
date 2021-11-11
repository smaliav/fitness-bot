package ru.smaliav.fitnessbot.bot.command.core;

import ru.smaliav.fitnessbot.bot.command.action.ActionResult;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.service.StatsService;

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
        } catch (NumberFormatException e) {
            actionResult.setUnsuccessful("Неверный формат числа!");
        } catch (DateTimeParseException e) {
            actionResult.setUnsuccessful("Неверный формат даты! Попробуй ДД.ММ.ГГГГ");
        } catch (IllegalArgumentException e) {
            if (e.getMessage() == null || e.getMessage().isEmpty()) {
                actionResult.setUnsuccessful("Недопустимый текст, ознакомьтесь с командами /help");
            } else {
                actionResult.setUnsuccessful(e.getMessage());
            }
        }

        return actionResult;
    }

    protected abstract ActionResult performAction(FitnessUser fitnessUser, String[] args, IAction action);

}
