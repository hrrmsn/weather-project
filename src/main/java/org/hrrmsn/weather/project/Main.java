package org.hrrmsn.weather.project;

import java.util.Timer;

/**
 *
 * @author hrrmsn
 */
public class Main {
    private static final long DELAY = 0;
    private static final long PERIOD = 120_000;
    
    public static void main(String[] args) {
        Timer weatherTimer = new Timer();
        weatherTimer.schedule(new WeatherTimerTask(), DELAY, PERIOD);
    }
}
