package com.example.sunshine.weatherapp.storingData;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "weather", indices = {@Index(value = {"date"}, unique = true)})
public class WeatherEntry implements Serializable {
    static final String TABLE_NAME = "weather";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "minTemp")
    private double minTemp;
    @ColumnInfo(name = "maxTemp")
    private double maxTemp;
    @ColumnInfo(name = "Forecast")
    private String description;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "code")
    private int code; //this code used to find approriate image for certain condition
    @ColumnInfo(name = "windSpeed")
    private float windSpeed;
    @ColumnInfo(name = "windDirection")
    private String windDirection;
    /* @ColumnInfo(name = "humidity")
    private double humidity;
    @ColumnInfo(name = "pressure")
    private double pressure;
   */

    public WeatherEntry(double minTemp, double maxTemp, String description, String date, int code, float windSpeed, String windDirection) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.description = description;
        this.date = date;
        this.code=code;
        this.windSpeed = windSpeed;
        this.windDirection=windDirection;

       /* this.humidity=humidity;
        this.pressure=pressure;
        */
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }


}
