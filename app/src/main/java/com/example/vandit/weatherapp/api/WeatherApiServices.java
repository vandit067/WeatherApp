package com.example.vandit.weatherapp.api;

import android.content.Context;

import com.example.vandit.weatherapp.R;
import com.example.vandit.weatherapp.api.adapter.WeatherAdapter;
import com.example.vandit.weatherapp.model.WeatherInfo;

import androidx.annotation.NonNull;
import io.reactivex.Observable;

public class WeatherApiServices {

    private static WeatherApiServices mWeatherApiServices;
    private WeatherAdapter mWeatherAdapter;
    private String mApiKey;

    private WeatherApiServices(@NonNull Context context) {
        this.mWeatherAdapter = new WeatherAdapter(context);
        this.mApiKey = context.getString(R.string.api_key);
    }

    public static WeatherApiServices getInstance(@NonNull Context context){
        if(mWeatherApiServices == null){
            mWeatherApiServices = new WeatherApiServices(context);
        }
        return mWeatherApiServices;
    }

    // retrieve weather info by city name
    public Observable<WeatherInfo> getWeatherInfoByCityName(String cityName, String unit){
        return this.mWeatherAdapter.getWeatherApi().getWeatherInfoByCityName(cityName, unit, this.mApiKey);
    }

}
