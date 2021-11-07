package ru.smaliav.fitnessbot.bot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.smaliav.fitnessbot.bot.command.core.BaseInfoCommand;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.service.StatsService;
import ru.smaliav.fitnessbot.business.service.UserService;

@Component
public class HelpCommand extends BaseInfoCommand {

    private static final String ID = "help";
    private static final String DESCRIPTION = "Shows possible commands";
    private static final String TEXT = """
            На данный момент доступен следующий набор команд:
            
            /get\\_weight - выводит вес за последнее время
            /get\\_weight `<дата>` - выводит вес за дату
            
            /set\\_weight `<вес>` - сохраняет сегодняшний вес
            /set\\_weight `<вес>` `<дата>` - сохраняет вес за дату
            
            /rm\\_weight `<дата>` - удаляет запись за дату
            /rm\\_weight `all` - удаляет все записи
            
            /help - выводит список команд""";

    private final UserService userService;

    @Autowired
    public HelpCommand(UserService userService, StatsService statsService) {
        super(ID, DESCRIPTION, statsService);
        this.userService = userService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        FitnessUser fitnessUser = userService.registerOrUpdate(user, chat);
        sendMessage(absSender, fitnessUser, ID, arguments, TEXT);
    }

}
