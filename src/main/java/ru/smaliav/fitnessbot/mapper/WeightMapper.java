package ru.smaliav.fitnessbot.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import ru.smaliav.fitnessbot.business.object.Weight;
import ru.smaliav.fitnessbot.repository.entity.WeightEntity;

import java.util.Collection;
import java.util.List;

@Mapper(
    uses = {
        UserMapper.class
    },
    disableSubMappingMethodsGeneration = true
)
public interface WeightMapper {

    WeightEntity b2e(Weight business, @Context BaseCycleAvoidingContext context);
    List<WeightEntity> b2eList(Collection<Weight> businessCollection, @Context BaseCycleAvoidingContext context);

    Weight e2b(WeightEntity entity, @Context BaseCycleAvoidingContext context);
    List<Weight> e2bList(Collection<WeightEntity> entityCollection, @Context BaseCycleAvoidingContext context);

}
