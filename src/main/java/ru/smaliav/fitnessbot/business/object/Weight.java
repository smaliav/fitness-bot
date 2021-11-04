package ru.smaliav.fitnessbot.business.object;

import lombok.Getter;
import lombok.Setter;
import ru.smaliav.fitnessbot.business.object.core.ITimedMetric;
import ru.smaliav.fitnessbot.business.object.core.ModifiedCreatedBusinessObject;

import java.time.LocalDate;

@Getter @Setter
public class Weight extends ModifiedCreatedBusinessObject implements ITimedMetric {

    private FitnessUser user;
    private Double value;
    private LocalDate date;

}
