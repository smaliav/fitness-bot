package ru.smaliav.fitnessbot.bot.command;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.smaliav.fitnessbot.bot.command.action.Action;
import ru.smaliav.fitnessbot.bot.command.action.ActionResult;
import ru.smaliav.fitnessbot.bot.command.action.WeightAction;
import ru.smaliav.fitnessbot.bot.command.core.BaseActionCommand;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.object.Weight;
import ru.smaliav.fitnessbot.business.service.StatsService;
import ru.smaliav.fitnessbot.business.service.UserService;
import ru.smaliav.fitnessbot.business.service.WeightService;
import ru.smaliav.fitnessbot.exception.InvalidArgumentException;
import ru.smaliav.fitnessbot.util.ChartHelper;
import ru.smaliav.fitnessbot.util.Utils;

import java.io.File;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Slf4j
@Component
public class WeightCommand extends BaseActionCommand {

    private static final String ID = "weight";
    private static final String DESCRIPTION = "Control your weight checkpoints";

    private final UserService userService;
    private final WeightService weightService;

    @Autowired
    public WeightCommand(
            UserService userService,
            StatsService statsService,
            WeightService weightService
    ) {
        super(ID, DESCRIPTION, statsService);
        this.userService = userService;
        this.weightService = weightService;
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] args) {
        FitnessUser fitnessUser = userService.registerOrUpdate(user, chat);
        ActionResult actionResult = performAction(fitnessUser, args);

        sendMessage(absSender, fitnessUser, ID, args, actionResult.getText());

        if (actionResult.isSuccess() && (actionResult.getAction() == WeightAction.GET_LIMITED)) {
            sendPhoto(absSender, fitnessUser, new File(ChartHelper.getChartLocation(fitnessUser.getId())));
        }
    }

    protected ActionResult performAction(FitnessUser fitnessUser, String[] args) {
        WeightAction action = determineAction(args);
        ActionResult actionResult = new ActionResult(action, false);

        try {
            actionResult = performAction(fitnessUser, action, args);
        } catch (InvalidArgumentException e) {
            actionResult.setUnsuccessful("Недопустимый текст, ознакомьтесь с командами /help");
        } catch (NumberFormatException e) {
            actionResult.setUnsuccessful("Неверный формат числа!");
        } catch (DateTimeParseException e) {
            actionResult.setUnsuccessful("Неверный формат даты! Попробуй ДД.ММ.ГГГГ");
        }

        return actionResult;
    }

    private ActionResult performAction(FitnessUser fitnessUser, WeightAction action, String[] args) {
        Weight weight;
        String text;

        switch (action) {
            case SET_TODAY -> {
                weightService.saveWeight(fitnessUser, args[1]);
                text = "Ваш сегодняшний вес был успешно сохранен!";
            }
            case SET_DATE -> {
                weight = weightService.saveWeight(fitnessUser, args[1], args[2]);
                text = "Вес за " + weight.getDate().format(Utils.getDefaultDateFormat())
                        + " был успешно сохранен!";
            }
            case GET_LIMITED -> {
                text = weightService.getWeightsByUserId(fitnessUser.getId());
                weightService.getWeightsByUserIdWithChart(fitnessUser.getId());
            }
            case GET_DATE -> text = weightService.getWeightByUserIdAndDate(fitnessUser.getId(), args[1]);
            case REMOVE_ALL -> text = weightService.removeAllWeightsByUserId(fitnessUser.getId());
            case REMOVE_DATE -> text = weightService.removeWeightByUserIdAndDate(fitnessUser.getId(), args[1]);
            default -> throw new InvalidArgumentException("Invalid first argument exception");
        }

        return new ActionResult(text, action, true);
    }

    private WeightAction determineAction(String[] args) {
        if (args.length < 1) return WeightAction.INVALID;

        WeightAction resAction;

        switch (Action.getByString(args[0])) {
            case SET -> resAction = parseSetAction(args);
            case GET -> resAction = parseGetAction(args);
            case REMOVE -> resAction = parseRemoveAction(args);
            default -> resAction = WeightAction.INVALID;
        }

        return resAction;
    }

    private WeightAction parseSetAction(String[] args) {
        WeightAction res;

        switch (args.length) {
            case 2 -> res = WeightAction.SET_TODAY;
            case 3 -> res = WeightAction.SET_DATE;
            default -> res = WeightAction.INVALID;
        }

        return res;
    }

    private WeightAction parseGetAction(String[] args) {
        WeightAction res;

        switch (args.length) {
            case 1 -> res = WeightAction.GET_LIMITED;
            case 2 -> res = WeightAction.GET_DATE;
            default -> res = WeightAction.INVALID;
        }

        return res;
    }

    private WeightAction parseRemoveAction(String[] args) {
        WeightAction res;

        switch (args.length) {
            case 2:
                if (Objects.equals(args[1], "all")) res = WeightAction.REMOVE_ALL;
                else res = WeightAction.REMOVE_DATE;
                break;
            default:
                res = WeightAction.INVALID;
        }

        return res;
    }

}
