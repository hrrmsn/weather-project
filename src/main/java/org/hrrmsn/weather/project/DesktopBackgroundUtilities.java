package org.hrrmsn.weather.project;

import java.io.IOException;

/**
 *
 * @author hrrmsn
 */
public class DesktopBackgroundUtilities {
    private static final String FORECAST_BACKGROUND_URI = "file:///home/hrrmsn/Pictures/Wallpapers/forecast.jpg";
    
    public DesktopBackgroundUtilities() {
    }
    
    public void setBackgroundWithForecast() throws WeatherException {
        StringBuilder bashCommand = new StringBuilder("gsettings set org.gnome.desktop.background picture-uri " 
                + FORECAST_BACKGROUND_URI);
        Process process;
        try {
            process = Runtime.getRuntime().exec(bashCommand.toString());
            process.waitFor();
        } catch (IOException e) {
            throw new WeatherException("Error when executing bash command: " + bashCommand, e);
        } catch (InterruptedException e) {
            throw new WeatherException("Error when executing bash command: " + bashCommand, e);
        }
    }
}
