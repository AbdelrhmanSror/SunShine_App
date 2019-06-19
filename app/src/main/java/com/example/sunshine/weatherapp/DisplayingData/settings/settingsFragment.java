package com.example.sunshine.weatherapp.DisplayingData.settings;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.sunshine.weatherapp.R;
import com.example.sunshine.weatherapp.SchedulingNotification.WeatherWorker;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;


/**
 * A simple {@link Fragment} subclass.
 */
public class settingsFragment extends PreferenceFragmentCompat {

    public settingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.settings_pref,s);
        final SwitchPreference NotificationPrefrence= findPreference(this.getString(R.string.notification));
        NotificationPrefrence.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                if((boolean)newValue)
                {
                    WeatherWorker.startWorkManger(getActivity());
                    Toast.makeText(getActivity(),"Notification is on",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    WeatherWorker.cancelWorkManger(getContext());
                    Toast.makeText(getActivity(),"Notification is off",Toast.LENGTH_SHORT).show();


                }
                return true;
            }
        });


    }


}
