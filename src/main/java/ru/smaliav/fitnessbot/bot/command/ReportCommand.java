package ru.smaliav.fitnessbot.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.smaliav.fitnessbot.bot.command.action.ActionResult;
import ru.smaliav.fitnessbot.bot.command.action.ReportAction;
import ru.smaliav.fitnessbot.bot.command.core.BaseActionCommand;
import ru.smaliav.fitnessbot.bot.command.core.IAction;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.service.ReportService;
import ru.smaliav.fitnessbot.business.service.StatsService;
import ru.smaliav.fitnessbot.business.service.UserService;

@Component
public class ReportCommand extends BaseActionCommand {

    public static final String ID = "report";
    public static final String DESCRIPTION = "Sends reports";

    private final UserService userService;
    private final ReportService reportService;

    public ReportCommand(UserService userService, StatsService statsService, ReportService reportService) {
        super(ID, DESCRIPTION, statsService);
        this.userService = userService;
        this.reportService = reportService;
    }

    @Override
    protected IAction determineAction(String[] args) {
        return ReportAction.SAVE;
    }

    @Override
    protected ActionResult performAction(FitnessUser fitnessUser, String[] args, IAction action) {
        String text = reportService.saveReport(fitnessUser, args);

        return new ActionResult(text, action);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] args) {
        FitnessUser fitnessUser = userService.registerOrUpdate(user, chat);
        ActionResult result = performAction(fitnessUser, args);

        sendMessage(absSender, fitnessUser, ID, args, result.getText());
    }

}
