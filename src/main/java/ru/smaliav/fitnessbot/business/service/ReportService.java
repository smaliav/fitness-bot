package ru.smaliav.fitnessbot.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.object.Report;
import ru.smaliav.fitnessbot.repository.ReportRepository;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional
    public String saveReport(FitnessUser fitnessUser, String[] args) {
        if (args.length == 0) throw new IllegalArgumentException("Отзыв не может быть пустым!");

        String reportStr = String.join(" ", args);
        Report report = new Report(fitnessUser, reportStr);

        report = reportRepository.saveReport(report);

        return "Спасибо! Ваш отзыв (№%d) был сохранен.".formatted(report.getId());
    }

}
