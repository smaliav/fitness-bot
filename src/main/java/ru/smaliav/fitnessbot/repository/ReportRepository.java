package ru.smaliav.fitnessbot.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaliav.fitnessbot.business.object.Report;
import ru.smaliav.fitnessbot.mapper.IdCycleAvoidingContext;
import ru.smaliav.fitnessbot.mapper.ReportMapper;
import ru.smaliav.fitnessbot.repository.dao.ReportDao;
import ru.smaliav.fitnessbot.repository.entity.ReportEntity;

@Repository
public class ReportRepository {

    private final ReportDao reportDao;
    private final ReportMapper reportMapper;

    @Autowired
    public ReportRepository(ReportDao reportDao, ReportMapper reportMapper) {
        this.reportDao = reportDao;
        this.reportMapper = reportMapper;
    }

    public Report saveReport(Report report) {
        ReportEntity entity = reportMapper.b2e(report, new IdCycleAvoidingContext());
        entity = reportDao.saveOrUpdate(entity);
        return reportMapper.e2b(entity, new IdCycleAvoidingContext());
    }

}
