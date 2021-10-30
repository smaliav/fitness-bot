package ru.smaliav.fitnessbot.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.mapper.UserMapper;
import ru.smaliav.fitnessbot.repository.dao.UserDao;
import ru.smaliav.fitnessbot.repository.entity.UserEntity;

import java.util.List;

@Repository
public class UserRepository {

    private final UserDao userDao;
    private final UserMapper userMapper;

    @Autowired
    public UserRepository(UserDao userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    public FitnessUser getUserByTelegramId(Long tId) {
        UserEntity entity = userDao.getUserByTelegramId(tId);
        return userMapper.e2b(entity);
    }

    public List<UserEntity> getUsers() {
        return userDao.getUsers();
    }

    public FitnessUser saveOrUpdateUser(FitnessUser fUser) {
        UserEntity entity = userMapper.b2e(fUser);
        entity = userDao.saveOrUpdateUser(entity);
        return userMapper.e2b(entity);
    }

}
