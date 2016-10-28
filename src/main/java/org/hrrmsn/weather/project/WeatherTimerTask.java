package org.hrrmsn.weather.project;

import java.util.TimerTask;

/**
 *
 * @author hrrmsn
 */
public class WeatherTimerTask extends TimerTask {
    private static final String MY_LOCATION_LATITUDE = "55.6092";
    private static final String MY_LOCATION_LONGITUDE = "37.5845";
    
    public WeatherTimerTask() {
    }
    
    @Override
    public void run() {
        ForecastLoader forecastLoader = new ForecastLoader(MY_LOCATION_LATITUDE, MY_LOCATION_LONGITUDE);
        try {
            JSONWeatherParser forecastParser = new JSONWeatherParser(forecastLoader.getForecast());
            
            DesktopBackgroundBuilder backgroundBuilder = new DesktopBackgroundBuilder();
            backgroundBuilder.build(forecastParser);
            
            DesktopBackgroundUtilities desktopUtilities = new DesktopBackgroundUtilities();
            desktopUtilities.setBackgroundWithForecast();
        } catch (WeatherException e) {
            e.printStackTrace();
        }
    }
}
