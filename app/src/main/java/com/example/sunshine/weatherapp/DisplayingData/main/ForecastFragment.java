package com.example.sunshine.weatherapp.DisplayingData.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sunshine.weatherapp.DisplayingData.details.DetailsActivity;
import com.example.sunshine.weatherapp.DisplayingData.settings.SettingsActivity;
import com.example.sunshine.weatherapp.R;
import com.example.sunshine.weatherapp.constants;
import com.example.sunshine.weatherapp.storingData.WeatherEntry;
import com.example.sunshine.weatherapp.storingData.WeatherViewModel;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment implements RecycleAdapter.ForecastClickListner {
    private View rootview;
    private WeatherViewModel weatherViewModel;
    private RecycleAdapter weatherAdapter;
    private String prevUnit;
    private String prevLocation;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public ForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.forecastfragment, menu);//inflating the menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // get the value stored in location preference and pas it to the thread
        if (id == R.id.refreshButton) {
            fetchWeatherData();
            return true;
        } else if (id == R.id.settings) {

            openSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   /* private void openPreferredLocationInMap() {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = sharedPrefs.getString(
                getString(R.string.prfLocationKey),
                getString(R.string.prfLocationDefault));

        // Using the URI scheme for showing a location found on a map.  This super-handy
        // intent can is detailed in the "Common Intents" page of Android's developer site:
        // http://developer.android.com/guide/components/intents-common.html#Maps
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }*/

    /**
     * if location changed then we want to fetch data again
     * else if unit changed we just want to update the recycler adapter.
     */
    private void chackAndUpdateIfNecessary() {
        String location = getDefaultSharedPreferences(Objects.requireNonNull(getActivity())).getString(getString(R.string.prfLocationKey), getString(R.string.prfLocationDefault));
        String unit = getDefaultSharedPreferences(getActivity()).getString(getString(R.string.listkey), getString(R.string.metric));
        if (!Objects.equals(location, prevLocation)) {
            fetchWeatherData();
            Log.v("update", location);

        } else if (unit != null && !unit.equals(prevUnit)) {
            Log.v("update", unit);
            weatherAdapter.notifyDataSetChanged();
            // weatherAdapter.submitList(x);
        }


    }

    private void fetchWeatherData() {
        if (checkingConnection()) {
            String userprefcity = getDefaultSharedPreferences(Objects.requireNonNull(getActivity())).getString(getString(R.string.prfLocationKey), getString(R.string.prfLocationDefault));
            //make sure if user enter an empty string setup ui with the default value
            if (TextUtils.isEmpty(userprefcity))
                weatherViewModel.FetchAndInsert(getString(R.string.prfLocationDefault));
            else {
                weatherViewModel.FetchAndInsert(userprefcity);
            }
            setLastUpdateTime();
        } else {
            Toast.makeText(getActivity(), getString(R.string.connection), Toast.LENGTH_LONG).show();
        }

    }

    //when returning from settings activity decide if it deserve to update data or not based on changes happened or not
    @Override
    public void onStart() {
        ReadPeriviousPrefrences();
        chackAndUpdateIfNecessary();
        super.onStart();

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    private void openSettings() {
        /*
          here i store values of unitTemp and location prefrence so if user change the prefrences
          i can choose if it deserve to fetch data again or just update the recycler adapter with new UnitTemp.
         */
        Storepreviousprefrences();
        Intent settings = new Intent(getActivity(), SettingsActivity.class);
        //this cond makes sure that the activity or the receiver exists if not then no need to start the activity
        if (settings.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            startActivity(settings);
        }

    }


    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.forecastfragment, container, false);
        weatherViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(WeatherViewModel.class);
        sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        isVisible(true);
        AutomaticUpdate();
        return rootview;
    }

    private void LoadData() {
        fetchWeatherData();
        setupRecyclerview();
    }


    // opening details activity by sending intents and prints the details of item selected in the details activity
    @Override
    public void openDetailsActivity(WeatherEntry data, View itemView, int CurrentItemSelectedPos) {
        //checking if it is two pane layout or not
        if (Objects.requireNonNull(getActivity()).findViewById(R.id.detailscontainer) == null) {
            Intent x = new Intent(getActivity(), DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data);
            x.putExtras(bundle);
            if (x.resolveActivity(getActivity().getPackageManager()) != null) {

                startActivity(x);
                getActivity().overridePendingTransition(R.anim.right,R.anim.outleft);
            }

        } else {
            /*
             * when user click on item if there is a previous item selected clear the background color of
             * previous selected item
             */
            if (weatherViewModel.getPreviouspos() != -1) {
                weatherViewModel.getPreviousItem()[0].setBackgroundColor(0);

            }
            // when item is clicked in two pane mode change color of view to blue
            itemView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blue));
            weatherViewModel.setPreviouspos(CurrentItemSelectedPos);
            weatherViewModel.setPreviousItem(new View[]{itemView});

            // data of selected item to preserve in viewModel
            weatherViewModel.setSelectedItem(data);
        }

    }

    private void setupRecyclerview() {
        RecyclerView mRecycler = rootview.findViewById(R.id.recycleView);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(rootview.getContext(), DividerItemDecoration.VERTICAL);
        weatherAdapter = new RecycleAdapter(this, getActivity());
        mRecycler.setAdapter(weatherAdapter);
        mRecycler.addItemDecoration(itemDecorator);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(linearLayoutManager);
        // when configurations change i want to scroll to same item i previously selected.
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(Objects.requireNonNull(getContext())) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_ANY;
            }
        };
        if (weatherViewModel.getPreviouspos() != -1) {
            smoothScroller.setTargetPosition(weatherViewModel.getPreviouspos());
            linearLayoutManager.startSmoothScroll(smoothScroller);
        }
        weatherViewModel.GetAllData().observe(this, new Observer<List<WeatherEntry>>() {
            @Override
            public void onChanged(@Nullable List<WeatherEntry> weathers) {
                //i want when user every time open the app to see first item in recycler view is selected(only in two pane mode)
                weatherViewModel.setPreviouspos(0);
                weatherAdapter.submitList(weathers);

            }
        });


    }


    /**
     * storing the current unit temp and location in shared pref so when user back from settings activity
     * i can detect if something changed or not and based upon update the forecast fragment.
     */
    private void Storepreviousprefrences() {
        String currentunit = getDefaultSharedPreferences(Objects.requireNonNull(getActivity())).getString(getString(R.string.listkey), getString(R.string.metric));
        String currentlocation = getDefaultSharedPreferences(getActivity()).getString(getString(R.string.prfLocationKey), getString(R.string.prfLocationDefault));
        editor.putString(getString(R.string.metric), currentunit);
        editor.putString(getString(R.string.prfLocationKey), currentlocation);
        editor.apply();
    }

    private void ReadPeriviousPrefrences() {
        prevUnit = sharedPref.getString(getString(R.string.metric), getString(R.string.metric));
        prevLocation = sharedPref.getString(getString(R.string.prfLocationKey), getString(R.string.prfLocationDefault));
    }

    /**
     * whenever activity/fragment is destroyed or created i store its state in pref so i can use it
     * in work manager to prevent the work in background if user was active;
     */
    private void isVisible(boolean state) {
        editor.putBoolean(constants.VISIBLE, state);
        editor.apply();
    }

    @Override
    public void onDetach() {
        isVisible(false);
        super.onDetach();
    }

    /**
     * storing last time user has opened the app so if he opened the app again after few second
     * the app does not have to update date automatically unless the difference time between the
     * last and current time is more than 2 hour.
     */
    private void setLastUpdateTime() {
        editor.putLong(constants.LAST_OPEN_TIME, System.currentTimeMillis());
        editor.apply();
    }

    private long getLastTimeUpdate() {
        long lastTime = sharedPref.getLong(constants.LAST_OPEN_TIME, 0);
        if (lastTime == 0) {
            return 0;
        }
        return lastTime;
    }

    private void AutomaticUpdate() {
        long currenttime = System.currentTimeMillis();
        if (getLastTimeUpdate() != 0 && (currenttime - getLastTimeUpdate() >= TimeUnit.HOURS.toMillis(2))) {
            LoadData();
        } else if (getLastTimeUpdate() == 0) {
            LoadData();
        } else {
            setupRecyclerview();
        }

    }

    private boolean checkingConnection() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();

        return ni != null && ni.getState() == NetworkInfo.State.CONNECTED;
    }
}
