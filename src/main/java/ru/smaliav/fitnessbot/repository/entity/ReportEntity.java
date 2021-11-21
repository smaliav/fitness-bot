package ru.smaliav.fitnessbot.repository.entity;

import lombok.Getter;
import lombok.Setter;
import ru.smaliav.fitnessbot.enums.ReportStatus;
import ru.smaliav.fitnessbot.repository.entity.core.ModifiedCreatedEntity;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "reports")
public class ReportEntity extends ModifiedCreatedEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "status", nullable = false)
    private ReportStatus status;

}
