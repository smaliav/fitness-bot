package ru.smaliav.fitnessbot.util;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import ru.smaliav.fitnessbot.business.object.core.ITimedMetric;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

@Slf4j
public abstract class ChartHelper {

    public static String EXT = "png";

    public static void createTimeSeriesPlot(Collection<? extends ITimedMetric> data, long userId) {
        TimeSeries timeSeries = new TimeSeries("Прогресс");

        data.forEach(weight -> {
            timeSeries.add(new Day(Date.from(weight.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant())), weight.getValue());
        });

        TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
        timeSeriesCollection.addSeries(timeSeries);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Прогресс",
                "Дата",
                "Значение",
                timeSeriesCollection,
                false,
                false,
                false
        );

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer renderer) {
            renderer.setSeriesPaint(0, Color.blue);
            renderer.setDefaultShapesVisible(false);
            renderer.setDefaultShapesFilled(false);
            renderer.setDrawSeriesLineAsPath(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd.MM.yyyy"));

        BufferedImage image = chart.createBufferedImage(600, 400);
        File outputFile = new File(getChartLocation(userId));
        outputFile.mkdirs();
        try {
            ImageIO.write(image, EXT, outputFile);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static String getChartLocation(long userId) {
        return "./charts/chart-%d.%s".formatted(userId, EXT);
    }

}
