package ru.smaliav.fitnessbot.business.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.smaliav.fitnessbot.BaseTest;
import ru.smaliav.fitnessbot.mapper.ReportMapper;
import ru.smaliav.fitnessbot.repository.ReportRepository;
import ru.smaliav.fitnessbot.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.smaliav.fitnessbot.business.service.ReportService.SUCCESS_RESPONSE;
import static ru.smaliav.fitnessbot.util.EntityConstructorUtil.userEntity;
import static ru.smaliav.fitnessbot.util.ObjectConstructorUtil.fitnessUser;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportServiceTest extends BaseTest {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ReportMapper reportMapper;

    private ReportService reportService;

    @BeforeEach
    public void setUp() {
        reportService = new ReportService(
            reportRepository,
            reportMapper
        );

        userRepository.save(userEntity(1));
    }

    @Test
    public void givenCorrectReport_whenSaveReport_thenSaveAndReturnSuccessComment() {
        var fitnessUser = fitnessUser(1);

        String comment = reportService.saveReport(fitnessUser, new String[]{"Report"});

        assertThat(comment).isEqualTo(SUCCESS_RESPONSE.formatted(1));
        assertThat(reportRepository.findAll()).hasSize(1);
    }

    @Test
    public void givenEmptyReport_whenSaveReport_thenThrow() {
        var fitnessUser = fitnessUser(1);

        assertThrows(IllegalArgumentException.class, () ->
            reportService.saveReport(fitnessUser, new String[]{})
        );
    }
}
