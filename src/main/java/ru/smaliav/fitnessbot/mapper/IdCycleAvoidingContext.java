package ru.smaliav.fitnessbot.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.util.Pair;
import ru.smaliav.fitnessbot.business.object.core.BaseBusinessObject;
import ru.smaliav.fitnessbot.repository.entity.core.BaseEntity;

import java.util.HashMap;
import java.util.Map;

public class IdCycleAvoidingContext extends BaseCycleAvoidingContext {

    private final Map<Pair<Class<?>, Object>, Object> knownInstancesById = new HashMap<>();

    @BeforeMapping
    public <T extends BaseEntity> T getMappedInstanceEntity(BaseBusinessObject src) {
        return getMappedInstance(src);
    }

    @BeforeMapping
    public <T extends BaseBusinessObject> T getMappedInstanceBusiness(BaseEntity src) {
        return getMappedInstance(src);
    }

    @BeforeMapping
    public void storeMappedEntityInstance(BaseBusinessObject source, @MappingTarget BaseEntity target) {
        storeMappedInstance(source, target);
    }

    @BeforeMapping
    public void storeMappedBusinessInstance(BaseEntity source, @MappingTarget BaseBusinessObject target) {
        storeMappedInstance(source, target);
    }

    @Override
    public <T> T getMappedInstance(Object src) {
        if (src == null) return null;

        Pair<Class<?>, Object> classIdPair = makeClassIdPair(src);

        if (classIdPair != null) {
            // noinspection unchecked
            return (T) knownInstancesById.get(classIdPair);
        }

        return super.getMappedInstance(src);
    }

    @Override
    public void storeMappedInstance(Object source, @MappingTarget Object target) {
        Pair<Class<?>, Object> classIdPair = makeClassIdPair(source);

        if (classIdPair != null) {
            knownInstancesById.put(classIdPair, target);
        } else {
            super.storeMappedInstance(source, target);
        }
    }

    private Pair<Class<?>, Object> makeClassIdPair(Object obj) {
        Object id = getObjectId(obj);

        if (id != null) {
            return Pair.of(obj.getClass(), id);
        }

        return null;
    }

    private Object getObjectId(Object obj) {
        if (obj == null) return null;

        if (obj instanceof BaseBusinessObject businessObject) {
            return businessObject.getId();
        } else if (obj instanceof BaseEntity entity) {
            return entity.getId();
        }

        return null;
    }

}
