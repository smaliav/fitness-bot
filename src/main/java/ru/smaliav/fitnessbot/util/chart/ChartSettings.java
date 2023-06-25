package ru.smaliav.fitnessbot.util.chart;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties("fitnessbot.chart")
@Component
public class ChartSettings {
    private String font;
    private Integer fontSize;
    private String extension;
    private String title;
    private String timeAxisLabel;
    private String valueAxisLabel;
    private String watermarkText;
    private Integer watermarkFontSize;
    private Float watermarkAlpha;
}
