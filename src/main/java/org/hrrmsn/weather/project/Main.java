package org.hrrmsn.weather.project;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author hrrmsn
 */
public class Main {
    private static final long DELAY = 0;
    private static final long PERIOD = 120_000;
    
    private static final String MY_LOCATION_LATITUDE = "55.6092";
    private static final String MY_LOCATION_LONGITUDE = "37.5845";    
    
    public static void main(String[] args) throws WeatherException, IOException {
//        Timer weatherTimer = new Timer();
//        weatherTimer.schedule(new WeatherTimerTask(), DELAY, PERIOD);

        ForecastLoader forecastLoader = new ForecastLoader(MY_LOCATION_LATITUDE, MY_LOCATION_LONGITUDE);
        JSONWeatherParser forecastParser = new JSONWeatherParser(forecastLoader.getForecast());

        DesktopBackgroundBuilder backgroundBuilder = new DesktopBackgroundBuilder();
        backgroundBuilder.build(forecastParser);

        DesktopBackgroundUtilities backgroundUtilities = new DesktopBackgroundUtilities();
        backgroundUtilities.setBackgroundWithForecast();

        // test
//        double[] hourlyTemperatures = forecastParser.getHourlyTemperatures();

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WidgetFrame widgetFrame = new WidgetFrame();
                widgetFrame.setVisible(true);
                
                //make frame transparent
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice gd = ge.getDefaultScreenDevice();
                if (gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT)) {
                    System.out.println("Transclucency windows are supported");
                } else {
                    System.out.println("Transclucency windows aren't supported");
                }
            }
        });
    }
}
