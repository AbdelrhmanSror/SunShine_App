package com.example.sunshine.weatherapp.DisplayingData.settings


import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager

import com.example.sunshine.weatherapp.R
import com.example.sunshine.weatherapp.SchedulingNotification.WeatherWorker
import java.util.concurrent.TimeUnit

import androidx.preference.PreferenceManager.getDefaultSharedPreferences


/**
 * A simple [Fragment] subclass.
 */
class settingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(bundle: Bundle, s: String) {
        setPreferencesFromResource(R.xml.settings_pref, s)
        val NotificationPrefrence = findPreference<SwitchPreference>(this.getString(R.string.notification))
        NotificationPrefrence!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            if (newValue as Boolean) {
                WeatherWorker.startWorkManger(activity)
                Toast.makeText(activity, "Notification is on", Toast.LENGTH_SHORT).show()
            } else {
                WeatherWorker.cancelWorkManger(context)
                Toast.makeText(activity, "Notification is off", Toast.LENGTH_SHORT).show()


            }
            true
        }


    }


}// Required empty public constructor
