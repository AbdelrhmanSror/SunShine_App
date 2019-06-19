package com.example.sunshine.weatherapp.SchedulingNotification;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.sunshine.weatherapp.R;
import com.example.sunshine.weatherapp.constants;
import com.example.sunshine.weatherapp.fetchingData.WeatherInfo;
import com.example.sunshine.weatherapp.storingData.WeatherDB;
import com.example.sunshine.weatherapp.storingData.WeatherDao;
import com.example.sunshine.weatherapp.storingData.WeatherEntry;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

public class WeatherWorker extends Worker {
    private Context context;
    private static WorkManager manager;


    public WeatherWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        boolean isVisible = getDefaultSharedPreferences(context).getBoolean(constants.VISIBLE, false);
        //if user currently active then do not start the job
        //mark it as failure/success because the use for sure update the app so no need to send notification
        // and wait for coming periodic task.
        if(isVisible)
        {
            return Result.success();
        }
        try {
            String mquery = getInputData().getString("query");
            WeatherDB weatherDB = WeatherDB.GetDataBase(getApplicationContext());
            WeatherDao weatherDao = weatherDB.weatherDao();
            WeatherInfo weatherInfo = new WeatherInfo(mquery);
            ArrayList<WeatherEntry> data = weatherInfo.FetchingInfo();
            if (weatherDao != null) {
                //weatherDao.deletePreviousWeather();
                weatherDao.insert(data);
            }
            Notification.FireNotification(context);
            return Result.success();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return Result.failure();
        }


    }


    public static void startWorkManger(Context context) {
        manager = WorkManager.getInstance(context);
        String Location = getDefaultSharedPreferences(context).getString(context.getString(R.string.prfLocationKey), context.getString(R.string.prfLocationDefault));
        Data query = new Data.Builder().putString("query", Location).build();
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true).build();
        //build work request with initial delay whenever user activate the notification, workmanager starts its work after 6 hours
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
                .Builder(WeatherWorker.class, 3, TimeUnit.DAYS)
                .setInitialDelay(6,TimeUnit.HOURS)
                .setInputData(query).setConstraints(constraints).build();
        manager.enqueueUniquePeriodicWork(context.getString(R.string.notification), ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest);
    }

    public static void cancelWorkManger(Context context) {
        if(manager!=null)
        manager.cancelUniqueWork(context.getString(R.string.notification));
    }
}
