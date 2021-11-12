package ru.smaliav.fitnessbot.util;

import org.telegram.telegrambots.meta.api.objects.User;
import ru.smaliav.fitnessbot.business.object.FitnessUser;

import java.time.format.DateTimeFormatter;

public abstract class Utils {

    public static String extractUserName(User user) {
        return (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getFirstName(), user.getLastName());
    }

    public static String extractUserName(FitnessUser user) {
        return (user.getNickname() != null) ? user.getNickname() :
                String.format("%s %s", user.getFirstName(), user.getSecondName());
    }

    public static DateTimeFormatter getDefaultDateFormat() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    public static Double parseDouble(String str) {
        String doubleStr = str.replaceAll(",", "\\.");
        return Double.parseDouble(doubleStr);
    }

}
