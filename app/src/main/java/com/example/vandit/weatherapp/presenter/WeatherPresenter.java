package com.example.vandit.weatherapp.presenter;

import android.content.Context;
import android.view.View;

import com.example.vandit.weatherapp.R;
import com.example.vandit.weatherapp.api.WeatherApiServices;
import com.example.vandit.weatherapp.contract.WeatherContract;
import com.example.vandit.weatherapp.model.WeatherInfo;
import com.example.vandit.weatherapp.utils.rx.AppRxSchedulers;

import androidx.annotation.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class WeatherPresenter extends BasePresenter<WeatherContract.View> implements WeatherContract.Presenter {

    private AppRxSchedulers mAppRxSchedulers;
    private Context mContext;

    public WeatherPresenter(@NonNull Context context, WeatherContract.View view, @NonNull AppRxSchedulers appRxSchedulers) {
        super(view);
        this.mAppRxSchedulers = appRxSchedulers;
        this.mContext = context;
    }

    @Override
    public void loadData(@NonNull String cityName) {
        if(view == null){
            return;
        }
        addDisposable(WeatherApiServices.getInstance(this.mContext).
                getWeatherInfoByCityName(cityName, this.mContext.getString(R.string.unit_metric))
                .subscribeOn(mAppRxSchedulers.io())
                .observeOn(mAppRxSchedulers.androidThread())
                .doOnSubscribe(disposable -> view.showProgress())
                .subscribe(weatherInfo -> {
                    view.hideProgress();
                    view.showData(weatherInfo);
                }, e -> view.showError(e.getMessage())));
    }
}
