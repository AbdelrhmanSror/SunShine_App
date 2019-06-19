package com.example.sunshine.weatherapp.DisplayingData.main;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sunshine.weatherapp.BR;
import com.example.sunshine.weatherapp.DisplayingData.WeatherUtility;
import com.example.sunshine.weatherapp.R;
import com.example.sunshine.weatherapp.storingData.WeatherEntry;
import com.example.sunshine.weatherapp.storingData.WeatherViewModel;
import java.util.Objects;
import static com.example.sunshine.weatherapp.constants.VIEW_TYPE_FUTURE_DAY;
import static com.example.sunshine.weatherapp.constants.VIEW_TYPE_TODAY;

public class RecycleAdapter extends ListAdapter<WeatherEntry, RecycleAdapter.viewHolder> {
    private ForecastClickListner forecastFragment;
    private Context c;
    private WeatherViewModel weatherViewModel;

    RecycleAdapter(ForecastFragment x, Context c) {
        super(DIFF_CALLBACK);
        this.forecastFragment = x;
        this.c = c;
        weatherViewModel = ViewModelProviders.of(Objects.requireNonNull(x.getActivity())).get(WeatherViewModel.class);
    }

    private static DiffUtil.ItemCallback<WeatherEntry> DIFF_CALLBACK = new DiffUtil.ItemCallback<WeatherEntry>() {
        @Override
        public boolean areItemsTheSame(@NonNull WeatherEntry oldItem, @NonNull WeatherEntry newItem) {
            return oldItem.getDate().equals(newItem.getDate());
        }

        @Override
        public boolean areContentsTheSame(@NonNull WeatherEntry oldItem, @NonNull WeatherEntry newItem) {
            return Objects.equals(oldItem,newItem);
        }
    };
    @Override
    @NonNull
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding MainView= DataBindingUtil.inflate(inflater,R.layout.main_layout_adapter,viewGroup,false);
        ViewDataBinding dayview= DataBindingUtil.inflate(inflater,R.layout.list_item_forecast_today,viewGroup,false);

        if (i == VIEW_TYPE_TODAY) {
            return new viewHolder(dayview);

        }
        return new viewHolder(MainView);
    }


    class viewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding viewDataBinding;

        viewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding=viewDataBinding;
            viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    forecastFragment.updateDetailsActivity(getItem(getAdapterPosition()), itemView, getAdapterPosition());

                }
            });
        }
        void Bind(WeatherEntry weatherEntry)
        {
            viewDataBinding.setVariable(BR.weatherEntry, weatherEntry);
            viewDataBinding.executePendingBindings();
        }


    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        WeatherEntry x = getItem(i);
        viewHolder.Bind(x);
        RestoreSelectedViewState(viewHolder.itemView, i, x);

    }

    /**
     * this Restoring only happens in two pane mode.
     *
     * @param itemView the view  that is registered as previous selected to be restored
     */
    private void RestoreSelectedViewState(View itemView, int position, WeatherEntry data) {
        if (weatherViewModel.getPreviouspos() != -1) {
            /*
             * when the user scrolling i set each view to its default cause when scrolling the views is being recycled
             * so if i applied a specific condition to view it will also applied to the same recycled view.
             * no of time we cleared the background of recycled view is no of times that view is being recycled
             */
            if (weatherViewModel.getPreviouspos() != position && itemView.getBackground() == weatherViewModel.getPreviousItem()[0].getBackground()) {
                itemView.setBackgroundColor(0);
            }
            /*
             * when configuration changes happen i want to restore the selected item state
             * also when scrolling back to the actual view .
             */
            if (weatherViewModel.getPreviouspos() == position) {

                //get the previous item selected and clear the background so only
                // only one iem in recycler view is selected
                //also first time the user open the app
                itemView.setBackgroundColor(ContextCompat.getColor(c, R.color.blue));
                weatherViewModel.setPreviousItem(new View[]{itemView});
                weatherViewModel.setSelectedItem(data);

            }
        }
    }


    @Override
    public int getItemViewType(int position) {

        return position == 0 && !weatherViewModel.ismTwoPane() ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }


    public interface ForecastClickListner {
        void updateDetailsActivity(WeatherEntry data, View itemView, int itemSelectedPos);
    }


}

