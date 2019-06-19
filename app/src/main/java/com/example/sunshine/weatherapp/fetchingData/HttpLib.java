package com.example.sunshine.weatherapp.fetchingData;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;


class HttpLib {
    private String PurposeOfConnection;
    private Uri.Builder builder;
    private Uri uri;



    HttpLib(String purposeOfConnection) {
        this.PurposeOfConnection = purposeOfConnection;
    }

    private URL get_url_object() {
        URL mUrl = null;
        try {
            mUrl = new URL(uri.toString());//creating an object url from the Uri object
            //surrounding it with try catch block because Malformdeurl exception may happen
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return mUrl;
    }

    private String getJson() {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            httpURLConnection = (HttpURLConnection) get_url_object().openConnection();
            httpURLConnection.setRequestMethod(PurposeOfConnection);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200)//checking if the connection is successful
            {
                inputStream = httpURLConnection.getInputStream();
                if (inputStream != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                    String read = bufferedReader.readLine();
                    while (read != null) {
                        stringBuilder.append(read);
                        read = bufferedReader.readLine();
                    }
                    inputStream.close();

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();

        }
        return stringBuilder.toString();

    }


    void addQuery(String name, String value) {
        builder.appendQueryParameter(name, value);

    }

    void addurl(String url) {
        builder = Uri.parse(url).buildUpon();

    }

    void addPath(String name) {
        builder.appendPath(name);

    }

    void Build() {
        if (builder == null) {
            return;
        }
        uri = builder.build();
    }

    String StartFetching() {
        return getJson();
    }


}
