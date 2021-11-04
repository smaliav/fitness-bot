package ru.smaliav.fitnessbot.bot.command.action;

import java.util.Objects;

public enum Action implements IAction {

    SET,
    GET,
    REMOVE,
    INVALID;

    public static Action getByString(String actionStr) {
        for (Action action: Action.values()) {
            if (Objects.equals(action.toString(), actionStr.toUpperCase())) {
                return action;
            }
        }

        return INVALID;
    }

}
