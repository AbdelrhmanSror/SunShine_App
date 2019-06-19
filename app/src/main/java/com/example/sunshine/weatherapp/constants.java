package com.example.sunshine.weatherapp;

public class constants {
    public static final int HOUR = 3600000;

    public static final int VIEW_TYPE_TODAY = 0;
    public static final int VIEW_TYPE_FUTURE_DAY = 1;
    public static final String DESCRIPTION="description";
    public static final String CODE="code";
    public static final String MAXTEMP="maxtemp";
    public static final String MINTEMP="mintemp";
    public static final String DATE="date";


    public static final String CHANNEL_ID= BuildConfig.APPLICATION_ID+" channel";
    public static final int NOTIFICATION_ID=5;

    public static final String GET = "GET";
    public static final String  POST="POST";
    public static final String DELETE="DELETE";

    public static final String key = "w80eG8ZprWvvKX2mECZTzrG8RR5lFEkS";
    public static final String SAMPLE_URL = "http://dataservice.accuweather.com/forecasts/v1/daily";
    public static final String SAMPLE_URL_CITY_CODE = "http://dataservice.accuweather.com/locations/v1/search?";
    public static final String QUERY_PARAM = "q";
    public static final String APPID_PARAM = "apikey";
    public static final String DETAILS = "details";
    public static final String METRIC = "metric";
    public static final String no_days = "5day";
    public static final String DAY="Day";
    public static final String NIGHT="Night";
    public static final String VISIBLE ="destroy";
    public static final String LAST_OPEN_TIME="lastOpenTime";
}
