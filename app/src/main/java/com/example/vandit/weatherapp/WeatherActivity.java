package com.example.vandit.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vandit.weatherapp.fragment.WeatherFragment;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        loadWeatherFragment();
    }

    private void loadWeatherFragment(){
        getSupportFragmentManager().beginTransaction().add(R.id.container, WeatherFragment.newInstance()).commit();
    }
}
