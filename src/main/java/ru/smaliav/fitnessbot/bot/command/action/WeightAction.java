package ru.smaliav.fitnessbot.bot.command.action;

import lombok.Getter;

@Getter
public enum WeightAction implements IAction {

    SET_TODAY   (Action.SET.toString()),
    SET_DATE    (Action.SET.toString(), "date"),
    GET_LIMITED (Action.GET.toString()),
    GET_DATE    (Action.GET.toString(), "date"),
    REMOVE_DATE (Action.REMOVE.toString(), "date"),
    REMOVE_ALL  (Action.REMOVE.toString(), "all"),
    INVALID     ();

    private final String arg1;
    private final String arg2;

    WeightAction() {
        this.arg1 = null;
        this.arg2 = null;
    }

    WeightAction(String arg1) {
        this.arg1 = arg1;
        this.arg2 = null;
    }

    WeightAction(String arg1, String arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

}
