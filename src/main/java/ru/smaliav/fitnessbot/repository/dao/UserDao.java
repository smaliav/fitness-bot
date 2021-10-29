package ru.smaliav.fitnessbot.repository.dao;

import org.springframework.stereotype.Repository;
import ru.smaliav.fitnessbot.repository.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDao {

    private final EntityManager em;

    public UserDao(EntityManager em) {
        this.em = em;
    }

    public List<UserEntity> getUsers() {
        TypedQuery<UserEntity> query = em.createQuery("" +
                "SELECT u FROM UserEntity u",
                UserEntity.class
        );

        return query.getResultList();
    }

}
