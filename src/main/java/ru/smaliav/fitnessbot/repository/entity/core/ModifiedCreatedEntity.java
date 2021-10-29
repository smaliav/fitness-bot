package ru.smaliav.fitnessbot.repository.entity.core;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
public class ModifiedCreatedEntity extends CreatedEntity {

    @Column(name = "modified")
    private LocalDateTime modified;

}
