package ru.smaliav.fitnessbot.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaliav.fitnessbot.business.object.Weight;
import ru.smaliav.fitnessbot.mapper.WeightMapper;
import ru.smaliav.fitnessbot.repository.dao.WeightDao;
import ru.smaliav.fitnessbot.repository.entity.WeightEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public class WeightRepository {

    private final WeightDao weightDao;
    private final WeightMapper weightMapper;

    @Autowired
    public WeightRepository(WeightDao weightDao, WeightMapper weightMapper) {
        this.weightDao = weightDao;
        this.weightMapper = weightMapper;
    }

    public List<Weight> getWeightsByUserId(long userId) {
        List<WeightEntity> weightEntities = weightDao.getWeightsByUserId(userId);
        return weightMapper.e2bList(weightEntities);
    }

    public Weight getWeightByUserIdAndDate(long userId, LocalDate date) {
        WeightEntity weightEntity = weightDao.getWeightByUserIdAndDate(userId, date);
        return weightMapper.e2b(weightEntity);
    }

    public int removeWeightByUserIdAndDate(long userId, LocalDate date) {
        return weightDao.removeWeightByUserIdAndDate(userId, date);
    }

    public int removeAllWeightsByUserId(long userId) {
        return weightDao.removeAllWeightsByUserId(userId);
    }

    public Weight saveWeight(Weight weight) {
        WeightEntity weightEntity = weightMapper.b2e(weight);
        weightEntity = weightDao.saveOrUpdate(weightEntity);
        return weightMapper.e2b(weightEntity);
    }

}
