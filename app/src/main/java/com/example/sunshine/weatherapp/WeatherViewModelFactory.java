/*package com.example.sunshine.weatherapp;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.sunshine.weatherapp.storingData.WeatherViewModel;

public class WeatherViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    public WeatherViewModelFactory(Application application)
    {
        this.application=application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(WeatherViewModel.class))
        {
            return (T)new WeatherViewModel(application,new SavedStateVMFactory(application));
        }
        throw new IllegalArgumentException("unknown model class");
    }
}
*/