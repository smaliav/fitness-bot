package ru.smaliav.fitnessbot.bot.command.weight;

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
import ru.smaliav.fitnessbot.business.service.StatsService;
import ru.smaliav.fitnessbot.business.service.UserService;
import ru.smaliav.fitnessbot.business.service.WeightService;
import ru.smaliav.fitnessbot.exception.InvalidArgumentException;
import ru.smaliav.fitnessbot.util.Utils;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class RemoveWeightCommand extends BaseActionCommand {

    public static final String ID = "rm_weight";
    public static final String DESCRIPTION = "Removes weight records";

    private final UserService userService;
    private final WeightService weightService;

    @Autowired
    public RemoveWeightCommand(UserService userService, StatsService statsService, WeightService weightService) {
        super(ID, DESCRIPTION, statsService);
        this.userService = userService;
        this.weightService = weightService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        FitnessUser fitnessUser = userService.registerOrUpdate(user, chat);
        ActionResult actionResult = performAction(fitnessUser, arguments);

        sendMessage(absSender, fitnessUser, ID, arguments, actionResult.getText());
    }

    protected ActionResult performAction(FitnessUser fitnessUser, String[] args, IAction action) {
        String text;

        switch ((WeightAction) action) {
            case REMOVE_ALL -> text = weightService.removeAllWeightsByUserId(fitnessUser.getId());
            case REMOVE_DATE -> text = weightService.removeWeightByUserIdAndDate(fitnessUser.getId(), args[0]);
            case REMOVE_TODAY -> text = weightService.removeWeightByUserIdAndDate(fitnessUser.getId(),
                    LocalDate.now().format(Utils.getDefaultDateFormat()));
            default -> throw new InvalidArgumentException();
        }

        return new ActionResult(text, action);
    }

    protected WeightAction determineAction(String[] args) {
        WeightAction resAction;

        switch (args.length) {
            case 1 -> {
                if (Objects.equals(args[0], WeightAction.REMOVE_ALL.getArg())) {
                    resAction = WeightAction.REMOVE_ALL;
                } else if (Objects.equals(args[0], WeightAction.REMOVE_TODAY.getArg())) {
                    resAction = WeightAction.REMOVE_TODAY;
                } else {
                    resAction = WeightAction.REMOVE_DATE;
                }
            }
            default -> resAction = WeightAction.INVALID;
        }

        return resAction;
    }

}
