package com.example.sunshine.weatherapp.storingData;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import android.view.View;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private LiveData<List<WeatherEntry>> getAllData;
    private WeatherRepository weatherRepository;
    private MutableLiveData<WeatherEntry> selectedItem = new MutableLiveData<>();//data of selected item in recyclerview
    private int Previouspos = -1;//position of previous selected view in recycler view
    private View[] PreviousItem = new View[1]; //previous selected view in recycler view
    private boolean mTwoPane;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        weatherRepository = new WeatherRepository(application);
        getAllData = weatherRepository.GetAlldata();

    }

    /**
     * @param Query location of meant data
     */
    public void FetchAndInsert(String Query) {
        weatherRepository.FetchAndInsert(Query);

    }

    public LiveData<List<WeatherEntry>> GetAllData() {
        return getAllData;
    }

    public LiveData<WeatherEntry> getSelectedIem() {
        return selectedItem;
    }

    public void setSelectedItem(WeatherEntry selecteditem) {
        selectedItem.setValue(selecteditem);
    }

    public int getPreviouspos() {
        return Previouspos;
    }

    public void setPreviouspos(int previouspos) {
        Previouspos = previouspos;
    }

    public View[] getPreviousItem() {
        return PreviousItem;
    }

    public void setPreviousItem(View[] previousItem) {
        PreviousItem = previousItem;
    }

    public boolean ismTwoPane() {
        return mTwoPane;
    }

    public void setmTwoPane(boolean mTwoPane) {
        this.mTwoPane = mTwoPane;
    }

}
