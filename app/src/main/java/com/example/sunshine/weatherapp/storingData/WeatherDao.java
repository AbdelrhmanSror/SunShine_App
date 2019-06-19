package com.example.sunshine.weatherapp.storingData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.ArrayList;
import java.util.List;

@Dao
 public interface WeatherDao
{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArrayList<WeatherEntry> weather);

    @Query("SELECT * FROM "+WeatherEntry.TABLE_NAME)
    LiveData<List<WeatherEntry>> getAlldata();
    @Query("DELETE FROM "+WeatherEntry.TABLE_NAME)
    void deletePreviousWeather();



}
