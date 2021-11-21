package ru.smaliav.fitnessbot.repository.entity;

import lombok.Getter;
import lombok.Setter;
import ru.smaliav.fitnessbot.repository.entity.core.ModifiedCreatedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Getter @Setter
@Entity
@Table(name = "users")
public class UserEntity extends ModifiedCreatedEntity {

    @Column(name = "t_id", nullable = false)
    private Long telegramId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "last_used", nullable = false)
    private LocalDateTime lastUsed;

    @OneToMany(mappedBy = "user")
    private Set<WeightEntity> weights;

    @OneToMany(mappedBy = "user")
    private Set<ReportEntity> reports;

}
