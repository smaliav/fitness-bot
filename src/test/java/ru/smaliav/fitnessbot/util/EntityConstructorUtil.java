package ru.smaliav.fitnessbot.util;

import ru.smaliav.fitnessbot.enums.ReportStatus;
import ru.smaliav.fitnessbot.repository.entity.ReportEntity;
import ru.smaliav.fitnessbot.repository.entity.UserEntity;

import java.time.LocalDateTime;

public abstract class EntityConstructorUtil {

    public static ReportEntity reportEntity(Long id, String text, UserEntity userEntity) {
        var reportEntity = new ReportEntity();
        reportEntity.setId(id);
        reportEntity.setText(text);
        reportEntity.setStatus(ReportStatus.NEW);
        reportEntity.setCreated(LocalDateTime.now());
        reportEntity.setUser(userEntity);
        return reportEntity;
    }

    public static UserEntity userEntity(Long id) {
        var userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setTelegramId(1L);
        userEntity.setChatId(1L);
        userEntity.setCreated(LocalDateTime.now());
        userEntity.setLastUsed(LocalDateTime.now());
        return userEntity;
    }
}
