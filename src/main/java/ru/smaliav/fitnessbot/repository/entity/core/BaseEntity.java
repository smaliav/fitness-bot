package ru.smaliav.fitnessbot.repository.entity.core;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
