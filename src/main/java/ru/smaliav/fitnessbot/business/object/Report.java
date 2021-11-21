package ru.smaliav.fitnessbot.business.object;

import lombok.Getter;
import lombok.Setter;
import ru.smaliav.fitnessbot.business.object.core.ModifiedCreatedBusinessObject;
import ru.smaliav.fitnessbot.enums.ReportStatus;

import java.time.LocalDateTime;

@Getter @Setter
public class Report extends ModifiedCreatedBusinessObject {

    private FitnessUser user;
    private String text;
    private ReportStatus status;

    public Report(FitnessUser user, String text) {
        this.user = user;
        this.text = text;
        this.status = ReportStatus.NEW;
        this.setCreated(LocalDateTime.now());
    }

}
