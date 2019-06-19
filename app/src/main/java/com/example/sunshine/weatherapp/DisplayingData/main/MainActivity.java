package com.example.sunshine.weatherapp.DisplayingData.main;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.sunshine.weatherapp.DisplayingData.details.DetailsFragment;
import com.example.sunshine.weatherapp.R;
import com.example.sunshine.weatherapp.storingData.WeatherViewModel;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    boolean mTwoPane;
    WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_scrollingtoolbar);
        SetUpToolBar();
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        //checking if its two pane or not by checking if the details container is existed or not

        if (findViewById(R.id.detailscontainer) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.detailscontainer, new DetailsFragment());
                fm.commit();
            }
        } else {
            mTwoPane = false;
        }
        weatherViewModel.setmTwoPane(mTwoPane);



    }

    public void SetUpToolBar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        TextView mainTitle = findViewById(R.id.mainTitle);
        setSupportActionBar(toolbar);
        //to disable the action bar title and use my own custom title.
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        mainTitle.setText(R.string.app_name);

        //getSupportActionBar().setLogo(R.mipmap.sunnyicons3);
        ImageView logo = findViewById(R.id.logo);
        logo.setImageResource(R.mipmap.sunnyicons3);
    }


}
