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
import ru.smaliav.fitnessbot.util.ChartHelper;

import java.io.File;

@Component
public class GetWeightCommand extends BaseActionCommand {

    public static final String ID = "get_weight";
    public static final String DESCRIPTION = "Get your weight records";

    private final UserService userService;
    private final WeightService weightService;

    @Autowired
    public GetWeightCommand(UserService userService, WeightService weightService, StatsService statsService) {
        super(ID, DESCRIPTION, statsService);
        this.userService = userService;
        this.weightService = weightService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        FitnessUser fitnessUser = userService.registerOrUpdate(user, chat);
        ActionResult actionResult = performAction(fitnessUser, arguments);

        sendMessage(absSender, fitnessUser, ID, arguments, actionResult.getText());

        if (actionResult.isSuccess() && (actionResult.getAction() == WeightAction.GET_LIMITED)) {
            sendPhoto(absSender, fitnessUser, new File(ChartHelper.getChartLocation(fitnessUser.getId())));
        }
    }

    protected ActionResult performAction(FitnessUser fitnessUser, String[] args, IAction action) {
        String text;

        switch ((WeightAction) action) {
            case GET_LIMITED -> text = weightService.getWeightsByUserIdWithChart(fitnessUser.getId());
            case GET_DATE -> text = weightService.getWeightByUserAndDate(fitnessUser.getId(), args[0]);
            default -> throw new IllegalArgumentException();
        }

        return new ActionResult(text, action);
    }

    protected WeightAction determineAction(String[] args) {
        WeightAction resAction;

        switch (args.length) {
            case 0 -> resAction = WeightAction.GET_LIMITED;
            case 1 -> resAction = WeightAction.GET_DATE;
            default -> resAction = WeightAction.INVALID;
        }

        return resAction;
    }

}
