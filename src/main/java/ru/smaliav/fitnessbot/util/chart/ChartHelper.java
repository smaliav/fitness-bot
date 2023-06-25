package ru.smaliav.fitnessbot.util.chart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.stereotype.Component;
import ru.smaliav.fitnessbot.business.object.core.ITimedMetric;
import ru.smaliav.fitnessbot.util.Utils;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
@Component
public class ChartHelper {

    private final ChartSettings settings;

    private Font DEFAULT_PLAIN_FONT;
    private Font DEFAULT_BOLD_FONT;
    private Font WATERMARK_FONT;

    @PostConstruct
    protected void postConstruct() {
        DEFAULT_PLAIN_FONT = new Font(settings.getFont(), Font.PLAIN, settings.getFontSize());
        DEFAULT_BOLD_FONT = new Font(settings.getFont(), Font.BOLD, settings.getFontSize());
        WATERMARK_FONT = new Font(settings.getFont(), Font.BOLD, settings.getWatermarkFontSize());
    }

    public void createTimeSeriesPlot(Collection<? extends ITimedMetric> data, long userId) {
        TimeSeries timeSeries = new TimeSeries("Progress");

        data.forEach(weight ->
            timeSeries.add(
                new Day(Date.from(weight.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant())), weight.getValue()
            )
        );

        TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
        timeSeriesCollection.addSeries(timeSeries);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                settings.getTitle(),
                settings.getTimeAxisLabel(),
                settings.getValueAxisLabel(),
                timeSeriesCollection,
                false,
                false,
                false
        );
        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer renderer) {
            renderer.setSeriesPaint(0, Color.blue);
            renderer.setDrawSeriesLineAsPath(true);
        }

        // Configuring Date Axis
        DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat(Utils.DATE_PATTERN));
        dateAxis.setTickLabelFont(DEFAULT_PLAIN_FONT);
        dateAxis.setLabelFont(DEFAULT_BOLD_FONT);

        // Configuring Value Axis
        NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
        numberAxis.setTickLabelFont(DEFAULT_PLAIN_FONT);

        BufferedImage image = chart.createBufferedImage(600, 400);
        File outputFile = new File(getChartLocation(userId));
        outputFile.mkdirs();

        addWatermark(image);
        try {
            ImageIO.write(image, settings.getExtension(), outputFile);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void addWatermark(BufferedImage image) {
        Graphics2D graphics2D = image.createGraphics();

        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, settings.getWatermarkAlpha());
        graphics2D.setComposite(alphaComposite);
        graphics2D.setColor(Color.lightGray);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setFont(WATERMARK_FONT);

        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        Rectangle2D rectangle2D = fontMetrics.getStringBounds(settings.getWatermarkText(), graphics2D);

        int centerX = (image.getWidth() - (int) rectangle2D.getWidth()) / 2;
        int centerY = (image.getHeight() - (int) rectangle2D.getHeight()) / 2;

        graphics2D.drawString(settings.getWatermarkText(), centerX, centerY);
        graphics2D.dispose();
    }

    public String getChartLocation(long userId) {
        return "./charts/chart-%d.%s".formatted(userId, settings.getExtension());
    }
}
