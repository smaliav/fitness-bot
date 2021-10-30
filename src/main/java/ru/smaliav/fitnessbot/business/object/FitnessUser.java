package ru.smaliav.fitnessbot.business.object;

import lombok.Getter;
import lombok.Setter;
import ru.smaliav.fitnessbot.business.object.core.ModifiedCreatedBusinessObject;

@Getter @Setter
public class FitnessUser extends ModifiedCreatedBusinessObject {

    private Long telegramId;
    private String nickname;
    private String firstName;
    private String secondName;
    private Long chatId;

}
