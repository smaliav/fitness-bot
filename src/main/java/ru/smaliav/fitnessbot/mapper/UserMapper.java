package ru.smaliav.fitnessbot.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.repository.entity.UserEntity;

@Mapper(
    uses = {
        WeightMapper.class,
        ReportMapper.class
    },
    disableSubMappingMethodsGeneration = true
)
public interface UserMapper {

    UserEntity b2e(FitnessUser business, @Context BaseCycleAvoidingContext context);
    FitnessUser e2b(UserEntity entity, @Context BaseCycleAvoidingContext context);

}
