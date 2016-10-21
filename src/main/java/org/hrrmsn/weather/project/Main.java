package org.hrrmsn.weather.project;

/**
 *
 * @author hrrmsn
 */
public class Main {
    private static final String MY_LOCATION_LATITUDE = "55.6092";
    private static final String MY_LOCATION_LONGTITUDE = "37.5845";
    
    public static void main(String[] args) throws WeatherException {
        ForecastLoader forecastLoader = new ForecastLoader(MY_LOCATION_LATITUDE, MY_LOCATION_LONGTITUDE);
        JSONWeatherParser forecastParser = new JSONWeatherParser(forecastLoader.getForecast());
        System.out.println("icon: " + forecastParser.getIcon());
        System.out.println("temperature: " + forecastParser.getTemperature());
    }
}
