package org.hrrmsn.weather.project;

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
}
