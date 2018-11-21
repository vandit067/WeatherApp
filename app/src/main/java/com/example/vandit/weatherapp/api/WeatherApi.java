package com.example.vandit.weatherapp.api;

import com.example.vandit.weatherapp.model.WeatherInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather")
    Observable<WeatherInfo> getWeatherInfoByCityName(@Query("q") String cityName, @Query("units") String unit, @Query("appId") String appId);
}
