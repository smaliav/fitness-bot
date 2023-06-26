package ru.smaliav.fitnessbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smaliav.fitnessbot.repository.entity.WeightEntity;

import java.time.LocalDate;
import java.util.List;

public interface WeightRepository extends JpaRepository<WeightEntity, Long> {

    WeightEntity findWeightEntityByUserIdAndDate(Long userId, LocalDate date);
    List<WeightEntity> findWeightEntitiesByUserIdAndDateGreaterThan(Long userId, LocalDate date);

    int deleteAllByUserId(Long userId);
    int deleteAllByUserIdAndDate(Long userId, LocalDate date);
}
