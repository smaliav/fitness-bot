package ru.smaliav.fitnessbot.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import ru.smaliav.fitnessbot.business.object.Report;
import ru.smaliav.fitnessbot.repository.entity.ReportEntity;

@Mapper(
    uses = {
        UserMapper.class
    },
    disableSubMappingMethodsGeneration = true
)
public interface ReportMapper {

    ReportEntity b2e(Report business, @Context BaseCycleAvoidingContext context);
    Report e2b(ReportEntity entity, @Context BaseCycleAvoidingContext context);

}
