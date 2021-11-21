package ru.smaliav.fitnessbot.business.object;

import lombok.Getter;
import lombok.Setter;
import ru.smaliav.fitnessbot.business.object.core.ModifiedCreatedBusinessObject;

import java.time.LocalDateTime;
import java.util.Set;

@Getter @Setter
public class FitnessUser extends ModifiedCreatedBusinessObject {

    private Long telegramId;
    private String nickname;
    private String firstName;
    private String secondName;
    private Long chatId;
    private LocalDateTime lastUsed;

    private Set<Weight> weights;
    private Set<Report> reports;

}
