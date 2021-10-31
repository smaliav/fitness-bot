package ru.smaliav.fitnessbot.mapper;

import org.mapstruct.Mapper;
import ru.smaliav.fitnessbot.business.object.Weight;
import ru.smaliav.fitnessbot.repository.entity.WeightEntity;

import java.util.Collection;
import java.util.List;

@Mapper
public interface WeightMapper {

    WeightEntity b2e(Weight business);
    List<WeightEntity> b2eList(Collection<Weight> businessCollection);

    Weight e2b(WeightEntity entity);
    List<Weight> e2bList(Collection<WeightEntity> entityCollection);

}
