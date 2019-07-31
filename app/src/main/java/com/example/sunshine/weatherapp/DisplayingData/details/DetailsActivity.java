package com.example.sunshine.weatherapp.DisplayingData.details;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshine.weatherapp.R;

import java.util.Objects;


public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        SetUpToolBar();
        FragmentTransaction x = getSupportFragmentManager().beginTransaction();
        x.replace(R.id.detailscontainer, new DetailsFragment()).commit();

    }

    public void SetUpToolBar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Details");
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left,R.anim.outright);

    }
}
