package com.example.sunshine.weatherapp.DisplayingData;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sunshine.weatherapp.R;
import com.example.sunshine.weatherapp.constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

public class WeatherUtility {
    //converting local time to utc
   /* private static String FormatToUtc(String localdate)
    {
        SimpleDateFormat UtcDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        UtcDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = UtcDate.format(convertLocalToDate(localdate));

        String x;
        Date dateutc=null;
        try {
            dateutc=UtcDate.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dt1 = new SimpleDateFormat("E, MMM dd",Locale.ENGLISH);
        x = dt1.format(dateutc);

        return x;

    }*/
    private static double imperialUnit(double number) {
        return ((number * (9 / 5f)) + 32);
    }
    @BindingAdapter("Temp")
    public static void FormateTemp(TextView view, double temp) {
        Context c=view.getContext();
        double temprature;
        if (isMetric(c)) {
            temprature = temp;
        } else {
            temprature = imperialUnit(temp);
        }
        view.setText(c.getString(R.string.formateTemp, temprature));
    }



    private static boolean isWithinRange(Date date, Date currentday, Date endday) {

        return (date.before(endday) || date.after(currentday));
    }

    public static String FormatDate(Context c, String date) {
        if (date == null) {
            return null;
        }
        Date Local = convertLocalToDate(date);//converting to local

        // The day string for forecast uses the following logic:
        // For today: "Today, June 8"
        // For tomorrow:  "Tomorrow"
        // For the next 5 days: "Wednesday" (just the day name)
        // For all days after that: "Mon Jun 8"
        Calendar calendar = Calendar.getInstance();
        Date currentday = convertCalendarToLocalDateFormat(calendar.getTime());//converting the current time to utc
        calendar.add(Calendar.DATE, 1);//increment the current day by one so i can get tomorrow and compare it
        Date tomorrow = convertCalendarToLocalDateFormat(calendar.getTime());//converting the tomorrow time to utc
        calendar.add(Calendar.DATE, 5);
        Date endDay = convertCalendarToLocalDateFormat(calendar.getTime());//converting the endDay time to utc
        try {
            if (Objects.requireNonNull(Local).compareTo(currentday) == 0) {
                SimpleDateFormat day = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
                return c.getString(R.string.today) + " " + c.getString(R.string.date, day.format(currentday));
            } else if (Local.compareTo(tomorrow) == 0) {
                return c.getString(R.string.TOMORROW);
            } else if (isWithinRange(Local, currentday, endDay)) {
                return c.getString(R.string.date, new SimpleDateFormat("EEEE", Locale.ENGLISH).format(Local));

            } else
                return c.getString(R.string.date, new SimpleDateFormat("E, MMM dd", Locale.ENGLISH).format(Local));



        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;

    }
    @BindingAdapter("setdate")
    public static void getDateFormat(TextView view,String date)
    {
        //i want date in details activity to be in format "Today"\n jun 25
        if(date==null)
        {
            return;
        }
        String Date=FormatDate(view.getContext(),date);
        if(view.getId()==R.id.list_item_date_textview) {
            if (Date.contains(","))
                view.setText(Date.split(",")[0]);
            else
                view.setText(Date);
        }
        else if(view.getId()==R.id.list_item_date2_textview)
        {
            if (Date.contains(","))
                view.setText(Date.split(",")[1]);
        }
        else
            view.setText(Date);

    }

    private static Date convertLocalToDate(String date) {

        if (TextUtils.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date localdate = null;
        try {
            localdate = df1.parse(df1.format(df.parse(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localdate;
    }

    private static Date convertCalendarToLocalDateFormat(Date date)
    {
        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date x;
        Date y = null;
        try {
            x = df.parse(df.format(date));//converting the calender date to string in format of df and then parse it to date in format of df
            y = df1.parse(df1.format(x));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return y;
    }

    public static int DisplayImage(int code) {
        switch (code) {
            case 1:
                return R.drawable.a1;

            case 2:
                return R.drawable.a2;
            case 3:
                return R.drawable.a3;

            case 4:
                return R.drawable.a4;
            case 5:
                return R.drawable.a5;
            case 6:
                return R.drawable.a6;
            case 7:
                return R.drawable.a7;
            case 9:
                return R.drawable.a9;
            case 11:
                return R.drawable.a11;

            case 12:
                return R.drawable.a12;
            case 13:
                return R.drawable.a13;
            case 14:
                return R.drawable.a14;
            case 15:
                return R.drawable.a15;
            case 16:
                return R.drawable.a16;

            case 17:
                return R.drawable.a17;
            case 18:
                return R.drawable.a18;
            case 19:
                return R.drawable.a19;
            case 20:
                return R.drawable.a20;

            case 21:
                return R.drawable.a21;

            case 22:
                return R.drawable.a22;

            case 23:
                return R.drawable.a23;

            case 24:
                return R.drawable.a24;

            case 25:
                return R.drawable.a25;

            case 26:
                return R.drawable.a26;

            case 29:
                return R.drawable.a29;

            case 30:
                return R.drawable.a30;

            case 31:
                return R.drawable.a31;

            case 32:
                return R.drawable.a32;

            case 33:
                return R.drawable.a33;

            case 34:
                return R.drawable.a34;

            case 35:
                return R.drawable.a35;

            case 36:
                return R.drawable.a36;

            case 37:
                return R.drawable.a37;

            case 38:
                return R.drawable.a38;

            case 39:
                return R.drawable.a39;

            case 40:
                return R.drawable.a40;

            case 41:
                return R.drawable.a41;

            case 42:
                return R.drawable.a42;

            case 43:
                return R.drawable.a43;

            case 44:
                return R.drawable.a44;
            default:
                return 0;
        }
    }
    @BindingAdapter("setImage")
    public static void setImage(ImageView view,int code)
    {
        view.setImageResource(DisplayImage(code));
    }

    /**
     * @param sunrise the time of sunrise in current location
     * @param sunset the time of sunset in current location
     * @param actualdate the current time of current location
     * @return is day or night  ----> day=true  night=false
     */
    public static String isDayorNight(@NonNull String sunrise, @NonNull String sunset, @NonNull String actualdate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Date rise = null;
        Date set = null;
        Date date = null;


        try {
            rise = df.parse(sunrise);
            set = df.parse(sunset);
            date = df.parse(actualdate);

        } catch (ParseException e) {
            e.printStackTrace();

        }

        if (Objects.requireNonNull(date).compareTo(rise) == 0 || date.compareTo(rise) > 0 || date.compareTo(set) < 0) {

            return constants.DAY;
        }

        return constants.NIGHT;


    }

    private static boolean isMetric(Context c) {
        String unitType = getDefaultSharedPreferences(c).getString(c.getString(R.string.listkey), c.getString(R.string.metric));
        return Objects.requireNonNull(unitType).equalsIgnoreCase("metric");

    }

    @BindingAdapter({"windSpeed","windDirection"})
    public static void formatWind(TextView view, float windSpeed, String windDirection) {
        Context c=view.getContext();
        if (isMetric(c)) {
            view.setText( c.getString(R.string.format_wind_metric, windSpeed, windDirection));
        }
        else {
            float mwind_speed = 0.621371f * windSpeed;
            view.setText(c.getString(R.string.format_wind_imperial, mwind_speed, windDirection));

        }


    }


}
