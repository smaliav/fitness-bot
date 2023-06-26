package ru.smaliav.fitnessbot.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.business.object.Weight;
import ru.smaliav.fitnessbot.mapper.IdCycleAvoidingContext;
import ru.smaliav.fitnessbot.mapper.WeightMapper;
import ru.smaliav.fitnessbot.repository.WeightRepository;
import ru.smaliav.fitnessbot.util.Utils;
import ru.smaliav.fitnessbot.util.WordDeclinationEnum;
import ru.smaliav.fitnessbot.util.WordDeclinationHelper;
import ru.smaliav.fitnessbot.util.chart.ChartHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeightService {

    private final WeightSettings settings;
    private final WeightRepository repository;
    private final ChartHelper chartHelper;
    private final WeightMapper mapper;

    @Transactional
    public String getWeightsByUserIdWithChart(long userId) {
        var weightEntities = repository.findWeightEntitiesByUserIdAndDateGreaterThan(userId,
            LocalDate.now().minusMonths(settings.getMaxMonths()));
        var weights = mapper.e2bList(weightEntities, new IdCycleAvoidingContext());
        chartHelper.createTimeSeriesPlot(weights, userId);

        StringBuilder res = new StringBuilder("Ваш вес за последние %d %s:\n".formatted(
            settings.getMaxMonths(),
            WordDeclinationHelper.getDeclination(WordDeclinationEnum.MONTH, settings.getMaxMonths())));

        if (weights.isEmpty()) {
            res.append("Записи отсутствуют");
        } else {
            weights.forEach(weight ->
                res.append(weight.getDate().format(Utils.getDefaultDateFormat())).append("\t")
                        .append(weight.getValue()).append("\n")
            );
        }

        return res.toString();
    }

    @Transactional
    public String getWeightByUserAndDate(long userId, String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, Utils.getDefaultDateFormat());
        var weightEntity = repository.findWeightEntityByUserIdAndDate(userId, date);
        var weight = mapper.e2b(weightEntity, new IdCycleAvoidingContext());
        return weight == null ? "Вес не установлен" : weight.getValue().toString();
    }

    @Transactional
    public String removeWeightByUserAndDate(FitnessUser fitnessUser, String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, Utils.getDefaultDateFormat());
        String res;

        int removedCnt = repository.deleteAllByUserIdAndDate(fitnessUser.getId(), date);

        if (removedCnt > 0) {
            fitnessUser.getWeights().removeIf(w -> w.getDate().equals(date));
            res = "Запись успешно удалена";
        } else {
            res = "Такой записи не существует";
        }

        return res;
    }

    @Transactional
    public String removeAllWeightsByUser(FitnessUser fitnessUser) {
        var removedCnt = repository.deleteAllByUserId(fitnessUser.getId());
        fitnessUser.getWeights().clear();
        return "Записей удалено: " + removedCnt;
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

        var weightEntity = repository.findWeightEntityByUserIdAndDate(fitnessUser.getId(), date);
        var weight = mapper.e2b(weightEntity, new IdCycleAvoidingContext());

        if (weight == null) {
            weight = new Weight();
            weight.setUser(fitnessUser);
            weight.setCreated(now);
        } else {
            weight.setModified(now);
        }
        weight.setValue(weightNum);
        weight.setDate(date);

        weightEntity = repository.save(mapper.b2e(weight, new IdCycleAvoidingContext()));
        return mapper.e2b(weightEntity, new IdCycleAvoidingContext());
    }

    private Double parseWeight(String str) {
        Double res = Utils.parseDouble(str);

        if (Double.compare(res, settings.getMaxWeight()) > 0) {
            throw new IllegalArgumentException("Вы ввели слишком большой вес!");
        } else if (Double.compare(settings.getMinWeight(), res) != -1) {
            throw new IllegalArgumentException("Вы ввели слишком маленький вес!");
        }

        return res;
    }
}
