package ru.smaliav.fitnessbot.business.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.object.Weight;
import ru.smaliav.fitnessbot.repository.WeightRepository;
import ru.smaliav.fitnessbot.util.ChartHelper;
import ru.smaliav.fitnessbot.util.Utils;
import ru.smaliav.fitnessbot.util.WordDeclinationEnum;
import ru.smaliav.fitnessbot.util.WordDeclinationHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class WeightService {

    private final WeightSettings weightSettings;
    private final WeightRepository weightRepository;

    @Value("${fitnessbot.get-weights.max-months}")
    private int maxMonths;

    @Autowired
    public WeightService(WeightRepository weightRepository, WeightSettings weightSettings) {
        this.weightSettings = weightSettings;
        this.weightRepository = weightRepository;
    }

    public String getWeightsByUserIdWithChart(long userId) {
        List<Weight> weights = weightRepository.getWeightsByUserIdLimited(userId);
        ChartHelper.createTimeSeriesPlot(weights, userId);

        StringBuilder res = new StringBuilder("Ваш вес за последние %d %s:\n"
                .formatted(maxMonths, WordDeclinationHelper.getDeclination(WordDeclinationEnum.MONTH, maxMonths)));

        if (weights.isEmpty()) {
            res.append("Записи отсутствуют");
        } else {
            weights.forEach(weight -> {
                res.append(weight.getDate().format(Utils.getDefaultDateFormat())).append("\t")
                        .append(weight.getValue()).append("\n");
            });
        }

        return res.toString();
    }

    public String getWeightByUserIdAndDate(long userId, String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, Utils.getDefaultDateFormat());
        Weight weight = weightRepository.getWeightByUserIdAndDate(userId, date);
        return weight == null ? "Вес не установлен" : weight.getValue().toString();
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
        Double weightNum = parseWeight(weightStr);
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
        weight.setValue(weightNum);
        weight.setDate(date);

        return weightRepository.saveWeight(weight);
    }

    private Double parseWeight(String str) {
        Double res = Utils.parseDouble(str);

        if (Double.compare(res, weightSettings.getMaxWeight()) > 0) {
            throw new IllegalArgumentException("Вы ввели слишком большой вес!");
        } else if (Double.compare(weightSettings.getMinWeight(), res) != -1) {
            throw new IllegalArgumentException("Вы ввели слишком маленький вес!");
        }

        return res;
    }

}
