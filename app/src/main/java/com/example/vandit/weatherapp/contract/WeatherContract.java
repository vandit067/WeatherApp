package com.example.vandit.weatherapp.contract;

import com.example.vandit.weatherapp.model.WeatherInfo;

import androidx.annotation.NonNull;

public interface WeatherContract {

    interface View {
        void showProgress();

        void hideProgress();

        void showError(@NonNull String message);

        void showData(@NonNull WeatherInfo weatherInfo);
    }

    interface Presenter {
        void loadData(@NonNull String cityName);
    }
}
