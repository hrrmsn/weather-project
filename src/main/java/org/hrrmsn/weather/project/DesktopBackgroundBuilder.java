package org.hrrmsn.weather.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

/**
 *
 * @author hrrmsn
 */
public class DesktopBackgroundBuilder {
    private static final String WEATHER_PICTURES_FOLDER = "/home/hrrmsn/Plan9/dev/languages/java/weather-project"
            + "/pictures";
    private static final String HOME_PICTURES_FOLDER = "/home/hrrmsn/Pictures/Wallpapers";
    
    public DesktopBackgroundBuilder() {
    }
    
    private BufferedImage loadImage(String imagePath) throws WeatherException {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(imagePath));
        } catch(IOException e) {
            throw new WeatherException("Error when loading image: " + imagePath, e);
        }
        return img;
    }
    
    private BufferedImage loadIconImage(String iconName) throws WeatherException {
        BufferedImage iconImg = loadImage(WEATHER_PICTURES_FOLDER + File.separator + "icons" + File.separator + iconName 
                + ".png");
        return iconImg;
    }
    
    private BufferedImage loadBackgroundTemplate() throws WeatherException {
        BufferedImage backgroundTemplate = loadImage(WEATHER_PICTURES_FOLDER + File.separator + "white.jpg");
        return backgroundTemplate;
    }
    
    private void createHourlyTemperaturesChart(JSONWeatherParser forecastParser) throws IOException {
        double[] hours = new double[49];
        for (int i = 0; i < hours.length; i++) {
            hours[i] = i;
        }
        
        XYChart chart = new XYChart(500, 500);
        chart.setTitle("Hourly temperatures");
        chart.setXAxisTitle("hours");
        chart.setYAxisTitle("\u00b0C");
        XYSeries series = chart.addSeries("t(hour)", hours, forecastParser.getHourlyTemperatures());
        series.setMarker(SeriesMarkers.SQUARE);
        
        BitmapEncoder.saveJPGWithQuality(chart, "/home/hrrmsn/Pictures/Wallpapers/chart.jpg", 1f);
    }
    
    public void build(JSONWeatherParser forecastParser) throws WeatherException {
        BufferedImage backgroundWithForecast = new BufferedImage(2560, 1600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = backgroundWithForecast.createGraphics();
        
        g.drawImage(loadBackgroundTemplate(), 0, 0, null);
        g.drawImage(loadIconImage(forecastParser.getIcon()), 2300, 150, null);
        
        int fontSize = 22;
        g.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
        g.setColor(Color.RED);
        g.drawString(Double.toString(forecastParser.getTemperature()) + "\u00b0C", 2403, 190);
        
        g.drawString("feels like " + Double.toString(forecastParser.getApparentTemperature()) + "\u00b0C", 2300, 235);
        
        g.drawString("wind: " + Double.toString(forecastParser.getWindSpeed()) + " m/s", 2300, 265);
        
//        try {
//            createHourlyTemperaturesChart(forecastParser);
//        } catch (IOException e) {
//            throw new WeatherException("Error when creating hourly temperatures chart: ", e);
//        }
        
        g.dispose();
        
        try {
            ImageIO.write(backgroundWithForecast, "jpg", new File(HOME_PICTURES_FOLDER + File.separator 
                    + "forecast.jpg"));
        } catch (IOException e) {
            throw new WeatherException("Error when writing background with forecast: " + HOME_PICTURES_FOLDER 
                    + File.separator + "forecast.jpg", e);
        }
    }
}
