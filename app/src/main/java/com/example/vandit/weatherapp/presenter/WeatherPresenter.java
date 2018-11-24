package com.example.vandit.weatherapp.presenter;

import android.content.Context;

import com.example.vandit.weatherapp.R;
import com.example.vandit.weatherapp.api.WeatherApiServices;
import com.example.vandit.weatherapp.contract.WeatherContract;
import com.example.vandit.weatherapp.utils.NetworkUtils;
import com.example.vandit.weatherapp.utils.rx.AppRxSchedulers;

import androidx.annotation.NonNull;
import retrofit2.HttpException;

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
        if (view == null) {
            return;
        }
        addDisposable(NetworkUtils.isNetworkAvailableObservable(this.mContext.getApplicationContext())
                .subscribeOn(mAppRxSchedulers.internet())
                .observeOn(mAppRxSchedulers.androidThread())
                .subscribe(isNetworkAvailable -> {
                    if (!isNetworkAvailable) {
                        view.hideProgress();
                        view.showError(mContext.getString(R.string.text_message_check_network));
                        return;
                    }
                    addDisposable(WeatherApiServices.getInstance(this.mContext).
                            getWeatherInfoByCityName(cityName, this.mContext.getString(R.string.unit_metric))
                            .subscribeOn(mAppRxSchedulers.io())
                            .observeOn(mAppRxSchedulers.androidThread())
                            .doOnSubscribe(disposable -> view.showProgress())
                            .subscribe(view::showData, e -> view.showError(((HttpException) e).response().raw().message())));
                }, e -> view.showError(((HttpException) e).response().raw().message())));
    }

}
