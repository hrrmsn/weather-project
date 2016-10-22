package org.hrrmsn.weather.project;

import java.util.Timer;

/**
 *
 * @author hrrmsn
 */
public class Main {
    private static final long DELAY = 10_000;
    private static final long PERIOD = 10_000;
    
    public static void main(String[] args) {
        Timer weatherTimer = new Timer();
        weatherTimer.schedule(new WeatherTimerTask(), DELAY, PERIOD);
    }
}
