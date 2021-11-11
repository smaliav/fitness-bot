package ru.smaliav.fitnessbot.business.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WeightSettings {

    @Value("${fitnessbot.min-weight}")
    private Double minWeight;

    @Value("${fitnessbot.max-weight}")
    private Double maxWeight;

}
