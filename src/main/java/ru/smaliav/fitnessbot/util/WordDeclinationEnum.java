package ru.smaliav.fitnessbot.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WordDeclinationEnum {

    MONTH (new String[]{"месяц", "месяца", "месяцев"});

    private final String[] declinations;

}
