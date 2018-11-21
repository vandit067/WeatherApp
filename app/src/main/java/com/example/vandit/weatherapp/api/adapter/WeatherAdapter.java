package com.example.vandit.weatherapp.api.adapter;

import android.content.Context;

import com.example.vandit.weatherapp.R;
import com.example.vandit.weatherapp.api.WeatherApi;
import com.google.gson.GsonBuilder;

import androidx.annotation.NonNull;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherAdapter {

    private static  WeatherAdapter mWeatherAdapter;

    private WeatherApi mWeatherApi;

    public WeatherAdapter(@NonNull Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        this.mWeatherApi = retrofit.create(WeatherApi.class);
    }

    public static WeatherAdapter getInstance(@NonNull Context context){
        if(mWeatherAdapter == null){
            mWeatherAdapter = new WeatherAdapter(context);
        }
        return mWeatherAdapter;
    }

    @NonNull
    public WeatherApi getWeatherApi() {
        return this.mWeatherApi;
    }
}
