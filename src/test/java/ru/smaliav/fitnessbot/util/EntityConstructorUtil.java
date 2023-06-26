package ru.smaliav.fitnessbot.util;

import ru.smaliav.fitnessbot.enums.ReportStatus;
import ru.smaliav.fitnessbot.repository.entity.ReportEntity;
import ru.smaliav.fitnessbot.repository.entity.UserEntity;

import java.time.LocalDateTime;

public abstract class EntityConstructorUtil {

    public static ReportEntity reportEntity(int id, String text, UserEntity userEntity) {
        var reportEntity = new ReportEntity();
        reportEntity.setId((long) id);
        reportEntity.setText(text);
        reportEntity.setStatus(ReportStatus.NEW);
        reportEntity.setCreated(LocalDateTime.now());
        reportEntity.setUser(userEntity);
        return reportEntity;
    }

    public static UserEntity userEntity(int id) {
        var userEntity = new UserEntity();
        userEntity.setId((long) id);
        userEntity.setTelegramId(1L);
        userEntity.setChatId(1L);
        userEntity.setCreated(LocalDateTime.now());
        userEntity.setLastUsed(LocalDateTime.now());
        return userEntity;
    }
}
