package ru.smaliav.fitnessbot.bot.command;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smaliav.fitnessbot.bot.command.core.BaseCommand;
import ru.smaliav.fitnessbot.bot.command.weight.GetWeightCommand;
import ru.smaliav.fitnessbot.bot.command.weight.RemoveWeightCommand;
import ru.smaliav.fitnessbot.bot.command.weight.SetWeightCommand;

import java.util.List;

@Getter
@Component
public class CommandStore {

    private final List<BaseCommand> store;

    @Autowired
    public CommandStore(
            StartCommand startCommand,
            HelpCommand helpCommand,
            GetWeightCommand getWeightCommand,
            SetWeightCommand setWeightCommand,
            RemoveWeightCommand removeWeightCommand
    ) {
        store = List.of(
                startCommand,
                helpCommand,
                getWeightCommand,
                setWeightCommand,
                removeWeightCommand
        );
    }

}
