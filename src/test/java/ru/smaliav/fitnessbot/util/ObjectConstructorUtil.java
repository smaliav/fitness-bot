package ru.smaliav.fitnessbot.util;

import ru.smaliav.fitnessbot.business.object.FitnessUser;

import java.time.LocalDateTime;

public abstract class ObjectConstructorUtil {

    public static FitnessUser fitnessUser(int id) {
        var fitnessUser = new FitnessUser();
        fitnessUser.setId((long) id);
        fitnessUser.setCreated(LocalDateTime.now());
        fitnessUser.setChatId(1L);
        fitnessUser.setTelegramId(1L);
        return fitnessUser;
    }
}
