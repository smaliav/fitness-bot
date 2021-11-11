package ru.smaliav.fitnessbot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.service.WeightSettings;

import java.time.format.DateTimeFormatter;

@Component
public class Utils {

    private static WeightSettings weightSettings;

    @Autowired
    private Utils(WeightSettings weightSettings) {
        Utils.weightSettings = weightSettings;
    }

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

    public static Double parseWeight(String str) {
        Double res = parseDouble(str);

        if (Double.compare(res, weightSettings.getMaxWeight()) > 0) {
            throw new IllegalArgumentException("Вы ввели слишком большой вес!");
        } else if (Double.compare(weightSettings.getMinWeight(), res) != -1) {
            throw new IllegalArgumentException("Вы ввели слишком маленький вес!");
        }

        return res;
    }

}
