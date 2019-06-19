package com.example.sunshine.weatherapp.fetchingData;

import android.util.Log;

import com.example.sunshine.weatherapp.DisplayingData.WeatherUtility;
import com.example.sunshine.weatherapp.storingData.WeatherEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.sunshine.weatherapp.constants.APPID_PARAM;
import static com.example.sunshine.weatherapp.constants.DETAILS;
import static com.example.sunshine.weatherapp.constants.GET;
import static com.example.sunshine.weatherapp.constants.METRIC;
import static com.example.sunshine.weatherapp.constants.QUERY_PARAM;
import static com.example.sunshine.weatherapp.constants.SAMPLE_URL;
import static com.example.sunshine.weatherapp.constants.SAMPLE_URL_CITY_CODE;
import static com.example.sunshine.weatherapp.constants.key;
import static com.example.sunshine.weatherapp.constants.no_days;

public class WeatherInfo {
    private  String query;
    private  String Location_key;
    public WeatherInfo(String query) {
        this.query = query;
    }

    private String getCity() {
        HttpLib city = new HttpLib(GET);
        city.addurl(SAMPLE_URL_CITY_CODE);
        city.addQuery(APPID_PARAM, key);
        city.addQuery(QUERY_PARAM, query);

        city.Build();

        return city.StartFetching();
    }

    private String getForecast() {
        HttpLib forecast = new HttpLib(GET);
        forecast.addurl(SAMPLE_URL);
        forecast.addPath(no_days);
        forecast.addPath(Location_key);
        forecast.addQuery(APPID_PARAM, key);
        forecast.addQuery(DETAILS, "true");
        forecast.addQuery(METRIC, "true");

        forecast.Build();
        return forecast.StartFetching();
    }


    public ArrayList<WeatherEntry> FetchingInfo() {
        ArrayList<WeatherEntry> x = new ArrayList<>();
        double maxtemp_c;
        double mintemp_c;
        String description;
        String LocalTime;
        int code;//based on this code we can determine an image for every condition.
        //float pressure;
        // int humidity;
        float wind_speed;
        String windDirection;
        try {
            JSONArray root = new JSONArray(getCity());//get the code of the city
            JSONObject element = root.getJSONObject(0);
            Location_key = element.getString("Key");
            JSONObject root1 = new JSONObject(getForecast());//getting the info by using code we have got before.
            JSONArray dailyforecast = root1.getJSONArray("DailyForecasts");

            for (int i = 0; i < dailyforecast.length(); i++) {
                JSONObject choice = dailyforecast.getJSONObject(i);
                LocalTime = choice.getString("Date");
                JSONObject sun = choice.getJSONObject(("Sun"));
                String sunrise = sun.getString("Rise");
                String sunset = sun.getString("Set");
                String isDayorNight = WeatherUtility.isDayorNight(sunrise, sunset, LocalTime);
                Log.v("isdayornight", isDayorNight);
                JSONObject temprature = choice.getJSONObject("Temperature");
                JSONObject min = temprature.getJSONObject("Minimum");
                JSONObject max = temprature.getJSONObject("Maximum");
                maxtemp_c = max.getDouble("Value");
                mintemp_c = min.getDouble("Value");
                JSONObject day_night = choice.getJSONObject(isDayorNight);
                code = day_night.getInt("Icon");
                description = day_night.getString("IconPhrase");
                JSONObject Wind = day_night.getJSONObject("Wind");
                JSONObject Direction = Wind.getJSONObject("Direction");
                JSONObject speed = Wind.getJSONObject("Speed");
                wind_speed = (float) speed.getDouble("Value");
                windDirection = Direction.getString("Localized");
                WeatherEntry weatherEntry = new WeatherEntry(mintemp_c, maxtemp_c, description, LocalTime, code, wind_speed, windDirection);
                x.add(weatherEntry);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return x;

    }


}
