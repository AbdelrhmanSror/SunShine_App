package com.example.sunshine.weatherapp.DisplayingData.details;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.widget.ShareActionProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sunshine.weatherapp.BR;
import com.example.sunshine.weatherapp.R;
import com.example.sunshine.weatherapp.storingData.WeatherEntry;
import com.example.sunshine.weatherapp.storingData.WeatherViewModel;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    //View binding var.
    private ViewDataBinding bind;
    //data to be shared if user clicked on share button
    String sharedData;

    public DetailsFragment() {
        setHasOptionsMenu(true);
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailsfragment, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.Actionbar);


        // Get the provider and hold onto it to set/change the share intent.
        ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // Attach an intent to this ShareActionProvider.  You can update this at any time,
        // like when the user selects a new piece of data they might like to share.
        if (mShareActionProvider != null) {
            //mShareActionProvider.setShareIntent(CreateShareIntent());

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        WeatherViewModel mdetailsViewModel;
        View mRootView = inflater.inflate(R.layout.fragment_details, container, false);

        bind = DataBindingUtil.bind(mRootView);
        mdetailsViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(WeatherViewModel.class);
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            WeatherEntry data = (WeatherEntry) bundle.getSerializable("data");
            initializeData(Objects.requireNonNull(data));
        }

        //for two pane mode
        mdetailsViewModel.getSelectedIem().observe(getActivity(), new Observer<WeatherEntry>() {
            @Override
            public void onChanged(@Nullable WeatherEntry weatherEntry) {
                if (weatherEntry != null)
                    initializeData(weatherEntry);


            }
        });

        return bind.getRoot();
    }

     /*public Intent CreateShareIntent() {
         Intent mIntent = new Intent(Intent.ACTION_SEND);
         mIntent.setType("text/plain");
         mIntent.putExtra(Intent.EXTRA_TEXT, datax);
         return mIntent;

     }*/
    private void initializeData(WeatherEntry data) {
        bind.setVariable(BR.weatherEntry, data);
        bind.executePendingBindings();
    }


}


