package ru.smaliav.fitnessbot.repository.dao;

import org.springframework.stereotype.Repository;
import ru.smaliav.fitnessbot.repository.dao.core.BaseDao;
import ru.smaliav.fitnessbot.repository.entity.UserEntity;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDao extends BaseDao<UserEntity> {

    public UserEntity getUserByTelegramId(Long id) {
        TypedQuery<UserEntity> query = em.createQuery("" +
                "SELECT u FROM UserEntity u WHERE u.telegramId = :id",
                UserEntity.class
        );

        query.setParameter("id", id);

        List<UserEntity> res = query.getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

    public List<UserEntity> getUsers() {
        TypedQuery<UserEntity> query = em.createQuery("" +
                "SELECT u FROM UserEntity u",
                UserEntity.class
        );

        return query.getResultList();
    }

}
