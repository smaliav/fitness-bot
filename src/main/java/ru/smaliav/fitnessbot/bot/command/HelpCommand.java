package ru.smaliav.fitnessbot.bot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.service.StatsService;
import ru.smaliav.fitnessbot.business.service.UserService;

@Component
public class HelpCommand extends BaseInfoCommand {

    private static final String ID = "help";
    private static final String DESCRIPTION = "Shows possible commands";
    private static final String TEXT = """
            На данный момент доступен следующий набор команд:
            /start - начинает диалог с Фитнес Ботом
            /help - выводит сообщение, которое вы сейчас видите""";

    private final UserService userService;

    @Autowired
    public HelpCommand(UserService userService, StatsService statsService) {
        super(ID, DESCRIPTION, statsService);
        this.userService = userService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        FitnessUser fitnessUser = userService.registerOrUpdate(user, chat);
        sendMessage(absSender, fitnessUser, ID, TEXT);
    }

}
