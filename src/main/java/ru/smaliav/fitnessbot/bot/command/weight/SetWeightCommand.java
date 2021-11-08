package ru.smaliav.fitnessbot.bot.command.weight;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.smaliav.fitnessbot.bot.command.action.ActionResult;
import ru.smaliav.fitnessbot.bot.command.core.IAction;
import ru.smaliav.fitnessbot.bot.command.action.WeightAction;
import ru.smaliav.fitnessbot.bot.command.core.BaseActionCommand;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.object.Weight;
import ru.smaliav.fitnessbot.business.service.StatsService;
import ru.smaliav.fitnessbot.business.service.UserService;
import ru.smaliav.fitnessbot.business.service.WeightService;
import ru.smaliav.fitnessbot.exception.InvalidArgumentException;
import ru.smaliav.fitnessbot.util.Utils;

@Component
public class SetWeightCommand extends BaseActionCommand {

    public static final String ID = "set_weight";
    public static final String DESCRIPTION = "Save your weight records";

    private final UserService userService;
    private final WeightService weightService;

    @Autowired
    public SetWeightCommand(UserService userService, StatsService statsService, WeightService weightService) {
        super(ID, DESCRIPTION, statsService);
        this.userService = userService;
        this.weightService = weightService;
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        FitnessUser fitnessUser = userService.registerOrUpdate(user, chat);
        ActionResult actionResult = performAction(fitnessUser, arguments);

        sendMessage(absSender, fitnessUser, ID, arguments, actionResult.getText());
    }

    protected ActionResult performAction(FitnessUser fitnessUser, String[] args, IAction action) {
        String text;

        switch ((WeightAction) action) {
            case SET_TODAY -> {
                weightService.saveWeight(fitnessUser, args[0]);
                text = "Ваш сегодняшний вес был успешно сохранен!";
            }
            case SET_DATE -> {
                Weight weight = weightService.saveWeight(fitnessUser, args[0], args[1]);
                text = "Вес за " + weight.getDate().format(Utils.getDefaultDateFormat())
                        + " был успешно сохранен!";
            }
            default -> throw new InvalidArgumentException();
        }

        return new ActionResult(text, action);
    }

    protected WeightAction determineAction(String[] args) {
        if (args.length < 1) return WeightAction.INVALID;

        WeightAction resAction;

        switch (args.length) {
            case 1 -> resAction = WeightAction.SET_TODAY;
            case 2 -> resAction = WeightAction.SET_DATE;
            default -> resAction = WeightAction.INVALID;
        }

        return resAction;
    }

}
