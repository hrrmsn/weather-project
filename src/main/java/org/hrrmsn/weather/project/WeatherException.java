package org.hrrmsn.weather.project;

/**
 *
 * @author hrrmsn
 */
public class WeatherException extends Exception {
    public WeatherException() {
    }
    
    public WeatherException(String message) {
        super(message);
    }
    
    public WeatherException(String message, Throwable e) {
        super(message, e);
    }
}
