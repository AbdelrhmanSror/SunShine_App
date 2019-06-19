package com.example.sunshine.weatherapp.storingData;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import android.content.Context;
import androidx.annotation.NonNull;

import java.io.Serializable;

@Database(entities = WeatherEntry.class,version = 2,exportSchema = false)
public abstract class WeatherDB extends RoomDatabase implements Serializable {
    private static final Migration MIGRATION_4_5=new Migration(4,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //creating new database
            database.execSQL("CREATE TABLE weather_new" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
                    ",minTemp REAL NOT NULL" +
                    ",maxTemp REAL NOT NULL" +
                    ",Forecast TEXT" +
                    ",date TEXT" +
                    ",code INTEGER NOT NULL" +
                    ",windSpeed REAL NOT NULL" +
                    ",windDirection TEXT)");
            //copy the old values of old database into new database
            database.execSQL("INSERT INTO weather_new " +
                    "(id,mintemp,maxtemp,Forecast,date,code,windSpeed,windDirection) " +
                    "SELECT id,minTemp,maxTemp,Forecast,date,code,windSpeed,windDirection FROM "+WeatherEntry.TABLE_NAME);
            //delete the old database.
            database.execSQL("DROP TABLE "+WeatherEntry.TABLE_NAME);
            //change the name of new database to the name of old database.
            database.execSQL("ALTER TABLE weather_new RENAME TO "+WeatherEntry.TABLE_NAME);
            //unique date for every weather forecast.
            database.execSQL("CREATE UNIQUE INDEX index_weather_date ON "+WeatherEntry.TABLE_NAME+"(date)");



        }
    };
    public abstract WeatherDao weatherDao();
     private  static volatile WeatherDB Instance;

      public static WeatherDB GetDataBase(Context context)
     {
         if(Instance==null)
         {
             synchronized (WeatherDB.class)
             {
                 Instance= Room.databaseBuilder(context.getApplicationContext(),WeatherDB.class,"WeatherDB").addMigrations(MIGRATION_4_5).allowMainThreadQueries().build();
             }
         }
         return Instance;
     }
}
