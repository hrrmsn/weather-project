package org.hrrmsn.weather.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author hrrmsn
 */
public class DesktopBackgroundBuilder {
    private static final String PICTURES_FOLDER = "/home/hrrmsn/Plan9/dev/languages/java/weather-project/pictures";
    
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
        BufferedImage iconImg = loadImage(PICTURES_FOLDER + File.separator + "icons" + File.separator + iconName 
                + ".png");
        return iconImg;
    }
    
    private BufferedImage loadBackgroundTemplate() throws WeatherException {
        BufferedImage backgroundTemplate = loadImage(PICTURES_FOLDER + File.separator 
                + "desktop_background_template.jpg");
        return backgroundTemplate;
    }
    
    public void build(String iconName, double temperature) throws WeatherException {
        BufferedImage backgroundWithForecast = new BufferedImage(2560, 1600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = backgroundWithForecast.createGraphics();
        g.drawImage(loadBackgroundTemplate(), 0, 0, null);
        g.drawImage(loadIconImage(iconName), 2000, 150, null);
        int fontSize = 20;
        g.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
        g.setColor(Color.RED);
        g.drawString(Double.toString(temperature) + "\u00b0C", 2090, 190);
        g.dispose();
        
        try {
            ImageIO.write(backgroundWithForecast, "jpg", new File(PICTURES_FOLDER + File.separator + "background.jpg"));
        } catch (IOException e) {
            throw new WeatherException("Error when writing background with forecast: " + PICTURES_FOLDER 
                    + File.separator + "background.jpg", e);
        }
    }
}
