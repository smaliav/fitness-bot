package ru.smaliav.fitnessbot.bot.command.action;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ActionResult {

    private String text;
    private IAction action;
    private boolean isSuccess;

    public ActionResult(IAction action, boolean isSuccess) {
        this.action = action;
        this.isSuccess = isSuccess;
    }

    public void setUnsuccessful(String text) {
        this.isSuccess = false;
        this.text = text;
    }

}
