package ru.smaliav.fitnessbot.mapper;

import org.mapstruct.Mapper;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.repository.entity.UserEntity;

@Mapper
public interface UserMapper {

    UserEntity b2e(FitnessUser business);
    FitnessUser e2b(UserEntity entity);

}
