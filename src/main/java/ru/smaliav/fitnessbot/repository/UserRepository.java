package ru.smaliav.fitnessbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smaliav.fitnessbot.repository.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserEntityByTelegramId(Long telegramId);
}
