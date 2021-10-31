package ru.smaliav.fitnessbot.business.object;

import lombok.Getter;
import lombok.Setter;
import ru.smaliav.fitnessbot.business.object.core.ModifiedCreatedBusinessObject;

import java.time.LocalDate;

@Getter @Setter
public class Weight extends ModifiedCreatedBusinessObject {

    private FitnessUser user;
    private Double weight;
    private LocalDate date;

}
