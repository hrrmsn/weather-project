package org.hrrmsn.weather.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author hrrmsn
 */
public class ForecastLoader {
    private static final String DARK_SKY_SECRET_KEY = "45a79aaba25b853a41f8545ea4442db6";
    private static final String API_DARK_SKY_URL = "https://api.darksky.net/forecast";
    private static final String URL_SEPARATOR = "/";
    
    private String latitude;
    private String longitude;
    
    public ForecastLoader(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public String getForecast() throws WeatherException {
        String url = API_DARK_SKY_URL + URL_SEPARATOR + DARK_SKY_SECRET_KEY + URL_SEPARATOR + latitude + "," 
                + longitude;
        String charset = "UTF-8";
        String unitsParam = "si";
        InputStream response = null;
        String forecast = "";
        try {
            String query = String.format("units=%s", URLEncoder.encode(unitsParam, charset));
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Accept-Encoding", "gzip");
            response = new GZIPInputStream(connection.getInputStream());
            forecast = IOUtils.toString(response, charset);
        } catch (UnsupportedEncodingException e) {
            throw new WeatherException("Problems with encoding: ", e);
        } catch (MalformedURLException e) {
            throw new WeatherException("Problems with http request to darksky.net: ", e);
        } catch (IOException e) {
            throw new WeatherException("Error when reading from input stream: ", e);
        } finally {
            IOUtils.closeQuietly(response);
        }
        return forecast;
    }
}
