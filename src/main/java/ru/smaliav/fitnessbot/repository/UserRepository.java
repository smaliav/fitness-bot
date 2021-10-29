package ru.smaliav.fitnessbot.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaliav.fitnessbot.repository.dao.UserDao;
import ru.smaliav.fitnessbot.repository.entity.UserEntity;

import java.util.List;

@Repository
public class UserRepository {

    private final UserDao userDao;

    @Autowired
    public UserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<UserEntity> getUsers() {
        return userDao.getUsers();
    }

}
