package ru.smaliav.fitnessbot.repository.entity;

import lombok.Getter;
import lombok.Setter;
import ru.smaliav.fitnessbot.repository.entity.core.ModifiedCreatedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table(name = "users")
public class UserEntity extends ModifiedCreatedEntity {

    @Column(name = "t_id")
    private Long telegramId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "last_used")
    private LocalDateTime lastUsed;

}
