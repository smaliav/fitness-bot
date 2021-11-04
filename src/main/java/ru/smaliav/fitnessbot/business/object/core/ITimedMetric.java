package ru.smaliav.fitnessbot.business.object.core;

import java.time.LocalDate;

public interface ITimedMetric {

    Double getValue();
    LocalDate getDate();

}
