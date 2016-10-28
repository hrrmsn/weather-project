package org.hrrmsn.weather.project;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author hrrmsn
 */
public class JSONWeatherParser {
    private JSONObject jsonObject;
    
    public JSONWeatherParser(String jsonText) {
        this.jsonObject = new JSONObject(jsonText);
    }
    
    public String getIcon() {
        return jsonObject.getJSONObject("currently").getString("icon");
    }
    
    public double getTemperature() {
        return jsonObject.getJSONObject("currently").getDouble("temperature");
    }
    
    public double getApparentTemperature() {
        return jsonObject.getJSONObject("currently").getDouble("apparentTemperature");
    }
    
    public double getWindSpeed() {
        return jsonObject.getJSONObject("currently").getDouble("windSpeed");
    }
    
    public double[] getHourlyTemperatures() {
        JSONArray hourlyJSONArray = jsonObject.getJSONObject("hourly").getJSONArray("data");
        double[] hourlyTemperatures = new double[hourlyJSONArray.length()];
        for (int i = 0; i < hourlyJSONArray.length(); i++) {
            hourlyTemperatures[i] = hourlyJSONArray.getJSONObject(i).getDouble("temperature");
        }
        return hourlyTemperatures;
    }
}
