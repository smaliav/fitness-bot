package ru.smaliav.fitnessbot.repository.entity.core;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
public abstract class CreatedEntity extends BaseEntity {

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

}
