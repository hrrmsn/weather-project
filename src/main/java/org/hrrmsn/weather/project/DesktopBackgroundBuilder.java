package org.hrrmsn.weather.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    
    private void createHourlyTemperaturesChart(JSONWeatherParser forecastParser) throws WeatherException {
        // parse hourly zero unix time
        long currentUnixTimestamp = forecastParser.getHourlyZeroUnixTimestamp();
        Date time = new Date((long)(currentUnixTimestamp * 1000));
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        String formattedDateStr = sdf.format(time);
        System.out.println("formatted date: " + formattedDateStr);
        
        Date formattedDate;
        try {
            formattedDate = sdf.parse(formattedDateStr);
        } catch (ParseException e) {
            throw new WeatherException("Error when parsing UNIX time: ", e);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formattedDate);
        int hoursOfStart = calendar.get(Calendar.HOUR_OF_DAY);
        int minutesOfStart = calendar.get(Calendar.MINUTE);
        System.out.println("hours: " + hoursOfStart);
        System.out.println("minutes: " + minutesOfStart);
        
        // create list of time strings
        List<String> hours = new ArrayList<String>();
        int index = 0;
        while (index < 49) {
            String currentTime = Integer.toString(hoursOfStart) + ":" + Integer.toString(minutesOfStart);
            hours.add(currentTime);
            ++index;
            if (hoursOfStart < 23) {
                ++hoursOfStart;
            } else {
                hoursOfStart = 0;
            }
        }
        
        // create chart
        XYChart chart = new XYChart(900, 500);
        chart.setTitle("Temperature, wind speed");
        chart.setXAxisTitle("time");
        chart.setYAxisTitle("\u00b0C, m/s");
        
        XYSeries temperatureSeries = chart.addSeries("temperature(time)", forecastParser.getHourlyTimeData(), 
                forecastParser.getHourlyTemperatures());
        XYSeries apparentTemperatureSeries = chart.addSeries("apparent temperature(time)", 
                forecastParser.getHourlyTimeData(), forecastParser.getApparentHourlyTemperatures());
        XYSeries windSpeedSeries = chart.addSeries("wind speed(time)", forecastParser.getHourlyTimeData(), 
                forecastParser.getHourlyWindSpeeds());
        
        temperatureSeries.setMarker(SeriesMarkers.CIRCLE);
        temperatureSeries.setMarkerColor(Color.BLACK);
        
        apparentTemperatureSeries.setMarker(SeriesMarkers.CIRCLE);
        apparentTemperatureSeries.setMarkerColor(Color.RED);
        
        windSpeedSeries.setMarker(SeriesMarkers.CIRCLE);
        windSpeedSeries.setMarkerColor(Color.BLUE);
        
        // write chart into jpg image
        try {
            BitmapEncoder.saveJPGWithQuality(chart, "/home/hrrmsn/Pictures/Wallpapers/chart.jpg", 1f);
        } catch (IOException e) {
            throw new WeatherException("Error when creating chart image: ", e);
        }
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
        
        // create chart
        createHourlyTemperaturesChart(forecastParser);
        
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
