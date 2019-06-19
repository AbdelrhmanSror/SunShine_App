package com.example.sunshine.weatherapp.SchedulingNotification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.example.sunshine.weatherapp.DisplayingData.main.MainActivity;
import com.example.sunshine.weatherapp.R;
import static com.example.sunshine.weatherapp.constants.CHANNEL_ID;
import static com.example.sunshine.weatherapp.constants.NOTIFICATION_ID;

 class Notification {

    private static void createNotificatioChannel(NotificationManager manager)
    {
        /*
         * all api starting with version 26 must have channel for notification
         * if current device below api 26 then we do nothing.
         */
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Alarm", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setDescription("We missing u");
            manager.createNotificationChannel(channel);
        }

    }
    private static void createNotifaction(Context context,NotificationManager manager)
    {
        Intent weatherintent=new Intent(context, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,NOTIFICATION_ID,weatherintent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.a1)
                /* for backward compatibility for api below 26 that doesn't have channel
                 * you must set default with priority so notification appears in status bar and can be audibly intrusive
                 * */
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle("WeatherToday").setContentIntent(pendingIntent)
                .setAutoCancel(true).setContentText("where are u?")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.a1))
                .setStyle(new NotificationCompat.BigPictureStyle().bigLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.a1)))
                .setStyle(new NotificationCompat.BigTextStyle().bigText("why did you disappear,We are missing you,"));
        manager.notify(NOTIFICATION_ID,builder.build());

    }
     static void FireNotification(Context context)
    {
        NotificationManager manager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificatioChannel( manager);
        createNotifaction(context,manager);
    }
   /* private static Calendar FormatToUtc()
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,19);
        calendar.set(Calendar.MINUTE,44);
        return calendar;

    }*/
}
