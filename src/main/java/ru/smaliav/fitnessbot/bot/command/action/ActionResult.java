package ru.smaliav.fitnessbot.bot.command.action;

import lombok.Getter;
import lombok.Setter;
import ru.smaliav.fitnessbot.bot.command.core.IAction;

@Getter @Setter
public class ActionResult {

    private String text;
    private IAction action;
    private boolean isSuccess;

    public ActionResult(IAction action) {
        this.action = action;
        this.isSuccess = false;
    }

    public ActionResult(String text, IAction action) {
        this.text = text;
        this.action = action;
        this.isSuccess = true;
    }

    public void setUnsuccessful(String text) {
        this.isSuccess = false;
        this.text = text;
    }

}
