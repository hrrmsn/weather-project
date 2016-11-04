package org.hrrmsn.weather.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    
    private JSONObject getCurrentlyJSONObject() {
        return jsonObject.getJSONObject("currently");
    }
    
    private JSONArray getHourlyJSONArray() {
        return jsonObject.getJSONObject("hourly").getJSONArray("data");
    }
    
    public String getIcon() {
        return getCurrentlyJSONObject().getString("icon");
    }
    
    public double getTemperature() {
        return getCurrentlyJSONObject().getDouble("temperature");
    }
    
    public double getApparentTemperature() {
        return getCurrentlyJSONObject().getDouble("apparentTemperature");
    }
    
    public double getWindSpeed() {
        return getCurrentlyJSONObject().getDouble("windSpeed");
    }
    
    public long getHourlyZeroUnixTimestamp() {
        return getHourlyJSONArray().getJSONObject(0).getLong("time");
    }
    
    // kindOfData is "temperature" for simple temperature
    // kindOfData is "apparentTemperature" for apparent temperature
    // kindOfData is "windSpeed" for wind speed
    private List<Double> getHourlyData(String kindOfData) {
        JSONArray hourlyJSONArray = getHourlyJSONArray();
        List<Double> hourlyData = new ArrayList<Double>();
        for (int i = 0; i < hourlyJSONArray.length(); i++) {
            hourlyData.add(hourlyJSONArray.getJSONObject(i).getDouble(kindOfData));
        }
        return hourlyData;
    }
    
    public List<Double> getHourlyTemperatures() {
        return getHourlyData("temperature");
    }
    
    public List<Double> getApparentHourlyTemperatures() {
        return getHourlyData("apparentTemperature");
    }
    
    public List<Double> getHourlyWindSpeeds() {
        return getHourlyData("windSpeed");
    }
    
    public List<Date> getHourlyTimeData() {
        JSONArray hourlyJSONArray = getHourlyJSONArray();
        List<Date> hourlyData = new ArrayList<Date>();
        for (int i = 0; i < hourlyJSONArray.length(); i++) {
            long time = hourlyJSONArray.getJSONObject(i).getLong("time");
            Date date = new Date((long)(time * 1000));
            hourlyData.add(date);
        }
        return hourlyData;
    }
}
