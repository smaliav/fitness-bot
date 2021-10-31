package ru.smaliav.fitnessbot.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.object.Weight;
import ru.smaliav.fitnessbot.repository.WeightRepository;
import ru.smaliav.fitnessbot.util.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeightService {

    private final WeightRepository weightRepository;

    @Autowired
    public WeightService(WeightRepository weightRepository) {
        this.weightRepository = weightRepository;
    }

    public String getWeightsByUserId(long userId) {
        List<Weight> weights = weightRepository.getWeightsByUserId(userId);
        StringBuilder res = new StringBuilder("Ваш вес за последние 3 месяца:\n");

        weights.forEach(weight -> {
            res.append(weight.getDate().format(Utils.getDefaultDateFormat())).append("\t")
                    .append(weight.getWeight()).append("\n");
        });

        return res.toString();
    }

    public String getWeightByUserIdAndDate(long userId, String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, Utils.getDefaultDateFormat());
        Weight weight = weightRepository.getWeightByUserIdAndDate(userId, date);
        return weight == null ? "Вес не установлен" : weight.getWeight().toString();
    }

    @Transactional
    public String removeWeightByUserIdAndDate(long userId, String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, Utils.getDefaultDateFormat());
        int deletedCnt = weightRepository.removeWeightByUserIdAndDate(userId, date);
        return (deletedCnt > 0) ? "Запись успешно удалена" : "Такой записи не существует";
    }

    @Transactional
    public String removeAllWeightsByUserId(long userId) {
        int deletedCnt = weightRepository.removeAllWeightsByUserId(userId);
        return "Записей удалено: " + deletedCnt;
    }

    @Transactional
    public void saveWeight(FitnessUser fitnessUser, String weightStr) {
        saveWeight(fitnessUser, weightStr, LocalDate.now().format(Utils.getDefaultDateFormat()));
    }

    @Transactional
    public Weight saveWeight(FitnessUser fitnessUser, String weightStr, String dateStr) {
        Double weightNum = Utils.parseDouble(weightStr);
        LocalDate date = LocalDate.parse(dateStr, Utils.getDefaultDateFormat());
        LocalDateTime now = LocalDateTime.now();

        Weight weight = weightRepository.getWeightByUserIdAndDate(fitnessUser.getId(), date);

        if (weight == null) {
            weight = new Weight();
            weight.setUser(fitnessUser);
            weight.setCreated(now);
        } else {
            weight.setModified(now);
        }
        weight.setWeight(weightNum);
        weight.setDate(date);

        return weightRepository.saveWeight(weight);
    }

}
