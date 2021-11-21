package ru.smaliav.fitnessbot.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

import java.util.IdentityHashMap;
import java.util.Map;

public abstract class BaseCycleAvoidingContext {

    private final Map<Object, Object> knownObjects = new IdentityHashMap<>();

    @BeforeMapping
    public <T> T getMappedInstance(Object src) {
        // noinspection unchecked
        return (T) knownObjects.get(src);
    }

    @BeforeMapping
    public void storeMappedInstance(Object src, @MappingTarget Object target) {
        knownObjects.put(src, target);
    }

}
