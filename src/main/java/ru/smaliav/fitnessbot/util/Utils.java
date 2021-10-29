package ru.smaliav.fitnessbot.util;

import org.telegram.telegrambots.meta.api.objects.User;

public abstract class Utils {

    public static String extractUserName(User user) {
        return (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getFirstName(), user.getLastName());
    }

}
