package ru.smaliav.fitnessbot.repository.entity;

import lombok.Getter;
import lombok.Setter;
import ru.smaliav.fitnessbot.repository.entity.core.ModifiedCreatedEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@Entity
@Table(name = "weights")
public class WeightEntity extends ModifiedCreatedEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "weight", nullable = false)
    private Double value;

    @Column(name = "date", nullable = false)
    private LocalDate date;

}
