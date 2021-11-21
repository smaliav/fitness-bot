package ru.smaliav.fitnessbot.bot.command.action;

import lombok.Getter;
import ru.smaliav.fitnessbot.bot.command.core.IAction;

@Getter
public enum ReportAction implements IAction {

    SAVE,
    ;

    private final String arg;

    ReportAction() {
        this.arg = null;
    }

}
