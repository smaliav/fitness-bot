package ru.smaliav.fitnessbot.bot.command;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CommandStore {

    private final StartCommand startCommand;
    private final HelpCommand helpCommand;

    @Autowired
    public CommandStore(
            StartCommand startCommand,
            HelpCommand helpCommand
    ) {
        this.startCommand = startCommand;
        this.helpCommand = helpCommand;
    }

}
