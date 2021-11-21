package ru.smaliav.fitnessbot.repository.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.smaliav.fitnessbot.repository.dao.core.BaseDao;
import ru.smaliav.fitnessbot.repository.entity.WeightEntity;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public class WeightDao extends BaseDao<WeightEntity> {

    @Value("${fitnessbot.get-weights.max-months}")
    private int maxMonths;

    public List<WeightEntity> getWeightsByUserId(long userId) {
        TypedQuery<WeightEntity> query = em.createQuery("" +
                "SELECT w FROM WeightEntity w WHERE w.user.id = :userId ORDER BY w.date DESC",
                WeightEntity.class
        );

        query.setParameter("userId", userId);

        return query.getResultList();
    }

    public List<WeightEntity> getWeightsByUserIdLimited(long userId) {
        LocalDate limitDate = LocalDate.now().minusMonths(maxMonths);

        TypedQuery<WeightEntity> query = em.createQuery("" +
                        "SELECT w FROM WeightEntity w WHERE w.user.id = :userId " +
                        "AND w.date > :limitDate ORDER BY w.date DESC",
                WeightEntity.class
        );

        query.setParameter("userId", userId);
        query.setParameter("limitDate", limitDate);

        return query.getResultList();
    }

    public WeightEntity getWeightByUserIdAndDate(long userId, LocalDate date) {
        TypedQuery<WeightEntity> query = em.createQuery("" +
                        "SELECT w FROM WeightEntity w WHERE w.user.id = :userId AND w.date = :date",
                WeightEntity.class
        );

        query.setParameter("userId", userId);
        query.setParameter("date", date);

        List<WeightEntity> res = query.getResultList();

        return res.isEmpty() ? null : res.get(0);
    }

    public void removeWeight(WeightEntity entity) {
        em.remove(entity);
    }

    public void removeWeights(Collection<WeightEntity> weights) {
        for (WeightEntity entity : weights) {
            removeWeight(entity);
        }
    }

}
