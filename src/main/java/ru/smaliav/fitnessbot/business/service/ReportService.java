package ru.smaliav.fitnessbot.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.object.Report;
import ru.smaliav.fitnessbot.mapper.IdCycleAvoidingContext;
import ru.smaliav.fitnessbot.mapper.ReportMapper;
import ru.smaliav.fitnessbot.repository.ReportRepository;

@RequiredArgsConstructor
@Service
public class ReportService {

    public static final String SUCCESS_RESPONSE = "Спасибо! Ваш отзыв (№%d) был сохранен.";
    public static final String EMPTY_RESPONSE = "Отзыв не может быть пустым!";

    private final ReportRepository repository;
    private final ReportMapper mapper;

    @Transactional
    public String saveReport(FitnessUser fitnessUser, String[] args) {
        if (args.length == 0) throw new IllegalArgumentException(EMPTY_RESPONSE);

        String reportStr = String.join(" ", args);
        Report report = new Report(fitnessUser, reportStr);

        var reportEntity = repository.save(mapper.b2e(report, new IdCycleAvoidingContext()));
        return SUCCESS_RESPONSE.formatted(reportEntity.getId());
    }
}
