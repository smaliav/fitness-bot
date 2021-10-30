package ru.smaliav.fitnessbot.business.object.core;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public abstract class ModifiedCreatedBusinessObject extends CreatedBusinessObject {

    private LocalDateTime modified;

}
