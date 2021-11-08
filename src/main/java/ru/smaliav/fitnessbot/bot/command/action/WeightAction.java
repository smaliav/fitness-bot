package ru.smaliav.fitnessbot.bot.command.action;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.smaliav.fitnessbot.bot.command.core.IAction;

@Getter
@AllArgsConstructor
public enum WeightAction implements IAction {

    SET_TODAY,
    SET_DATE,
    GET_LIMITED,
    GET_DATE,
    REMOVE_DATE,
    REMOVE_ALL   ("all"),
    REMOVE_TODAY ("today"),
    INVALID;

    private final String arg;

    WeightAction() {
        arg = null;
    }

}
