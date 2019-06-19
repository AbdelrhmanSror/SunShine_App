package com.example.sunshine.weatherapp.storingData;
import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.sunshine.weatherapp.SchedulingNotification.WeatherWorker;
import com.example.sunshine.weatherapp.fetchingData.WeatherInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class WeatherRepository {
    private WeatherDao weatherDao;
    private LiveData<List<WeatherEntry>> GetAllData;
    WorkManager manager;
    WeatherRepository(Application application) {
        WeatherDB weatherDB = WeatherDB.GetDataBase(application);
        weatherDao = weatherDB.weatherDao();
        GetAllData = weatherDao.getAlldata();
        manager=WorkManager.getInstance();
    }

    void FetchAndInsert(String Query) {
        new fetchAsyncTask(weatherDao).execute(Query);
    }



    LiveData<List<WeatherEntry>> GetAlldata() {
        return GetAllData;
    }
    private static class fetchAsyncTask extends AsyncTask<String,Void,Void>
    {
        WeatherDao weatherDao;
        private fetchAsyncTask(WeatherDao weatherDao)
        {
            this.weatherDao=weatherDao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            WeatherInfo weatherInfo = new WeatherInfo(strings[0]);
            ArrayList<WeatherEntry> data = weatherInfo.FetchingInfo();
            if (weatherDao != null) {
                //weatherDao.deletePreviousWeather();
                weatherDao.insert(data);
            }
            return null;
        }

    }
}
