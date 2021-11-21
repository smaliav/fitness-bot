package ru.smaliav.fitnessbot.repository.dao.core;

import ru.smaliav.fitnessbot.repository.entity.core.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class BaseDao<T extends BaseEntity> {

    @PersistenceContext
    protected EntityManager em;

    public T saveOrUpdate(T entity) {
        if (entity.getId() == null) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }

}
