package ru.smaliav.fitnessbot.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.smaliav.fitnessbot.util.Utils;

@Slf4j
@Component
public class StartCommand extends BaseInfoCommand {

    private static final String ID = "start";
    private static final String DESCRIPTION = "Starts a chat with Fitness Bot";
    private static final String TEXT = """
            Привет, это Фитнес Бот! Тут ты можешь ставить чекпоинты и следить за своим весом.
            Для большей информации воспользуйся командой /help""";

    public StartCommand() {
        super(ID, DESCRIPTION);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String chatId = chat.getId().toString();
        String userName = Utils.extractUserName(user);

        sendMessage(absSender, userName, chatId, ID, TEXT);
    }

}
