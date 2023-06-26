package ru.smaliav.fitnessbot.util;

import ru.smaliav.fitnessbot.business.object.FitnessUser;

public abstract class ObjectConstructorUtil {

    public static FitnessUser fitnessUser(Long id) {
        var fitnessUser = new FitnessUser();
        fitnessUser.setId(id);
        return fitnessUser;
    }
}
