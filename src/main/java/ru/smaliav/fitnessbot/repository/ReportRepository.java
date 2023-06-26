package ru.smaliav.fitnessbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smaliav.fitnessbot.repository.entity.ReportEntity;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
}
