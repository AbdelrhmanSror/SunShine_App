package com.example.sunshine.weatherapp.DisplayingData.settings;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshine.weatherapp.R;

import java.util.Objects;


public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingscontainer);
        SetUpToolBar();
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.settingscontainer, new settingsFragment()).commit();


    }

    public void SetUpToolBar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Settings");
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

    }


}



