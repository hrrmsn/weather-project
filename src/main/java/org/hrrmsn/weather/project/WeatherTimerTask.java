package org.hrrmsn.weather.project;

import java.util.TimerTask;

/**
 *
 * @author hrrmsn
 */
public class WeatherTimerTask extends TimerTask {
    private static final String MY_LOCATION_LATITUDE = "55.6092";
    private static final String MY_LOCATION_LONGTITUDE = "37.5845";
    
    public WeatherTimerTask() {
    }
    
    @Override
    public void run() {
        ForecastLoader forecastLoader = new ForecastLoader(MY_LOCATION_LATITUDE, MY_LOCATION_LONGTITUDE);
        try {
            JSONWeatherParser forecastParser = new JSONWeatherParser(forecastLoader.getForecast());
            
            DesktopBackgroundBuilder backgroundBuilder = new DesktopBackgroundBuilder();
            backgroundBuilder.build(forecastParser.getIcon(), forecastParser.getTemperature());
            // print to console
//            System.out.println("icon: " + forecastParser.getIcon());
//            System.out.println("temperature: " + forecastParser.getTemperature());
            
            DesktopBackgroundUtilities desktopUtilities = new DesktopBackgroundUtilities();
            desktopUtilities.setBackgroundWithForecast();
        } catch (WeatherException e) {
            e.printStackTrace();
        }
    }
}
