package ru.smaliav.fitnessbot.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.smaliav.fitnessbot.util.Utils;

@Component
public class HelpCommand extends BaseInfoCommand {

    private static final String ID = "help";
    private static final String DESCRIPTION = "Shows possible commands";
    private static final String TEXT = """
            На данный момент доступен следующий набор команд:
            /start - начинает диалог с Фитнес Ботом
            /help - выводит сообщение, которое вы сейчас видите""";

    public HelpCommand() {
        super(ID, DESCRIPTION);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String chatId = chat.getId().toString();
        String userName = Utils.extractUserName(user);

        sendMessage(absSender, userName, chatId, ID, TEXT);
    }

}
