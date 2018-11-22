package com.example.vandit.weatherapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vandit.weatherapp.R;
import com.example.vandit.weatherapp.contract.WeatherContract;
import com.example.vandit.weatherapp.model.WeatherInfo;
import com.example.vandit.weatherapp.presenter.WeatherPresenter;
import com.example.vandit.weatherapp.utils.UiUtils;
import com.example.vandit.weatherapp.utils.rx.AppRxSchedulers;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherFragment extends Fragment implements WeatherContract.View, TextView.OnEditorActionListener, TextWatcher {

    @BindView(R.id.fragment_weather_rl_main_view)
    RelativeLayout mRlMainView;
    @BindView(R.id.fragment_weather_tiet_city)
    TextInputEditText mEtCity;
    @BindView(R.id.fragment_weather_til_city)
    TextInputLayout mTilCity;
    @BindView(R.id.fragment_weather_iv_weather_icon)
    ImageView mIvWeatherIcon;
    @BindView(R.id.fragment_weather_tv_last_update)
    TextView mTvLastUpdate;
    @BindView(R.id.fragment_weather_tv_city)
    TextView mTvCity;
    @BindView(R.id.fragment_weather_tv_description)
    TextView mTvDescription;
    @BindView(R.id.fragment_weather_tv_humidity)
    TextView mTvHumidity;
    @BindView(R.id.fragment_weather_tv_pressure)
    TextView mTvPressure;
    @BindView(R.id.fragment_weather_tv_temp)
    TextView mTvTemp;
    @BindView(R.id.fragment_weather_rl_content)
    RelativeLayout mRlContentView;
    @BindView(R.id.fragment_weather_progressbar)
    ProgressBar mProgressBar;

    private Context mContext;

    private WeatherPresenter mWeatherPresenter;

    public static WeatherFragment newInstance() {
       return new WeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mWeatherPresenter = new WeatherPresenter(this.mContext, this, new AppRxSchedulers());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mEtCity.setOnEditorActionListener(this);
        this.mEtCity.addTextChangedListener(this);
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_SEARCH){
            if(TextUtils.isEmpty(v.getText())){
                UiUtils.showSnackBar(this.mRlContentView, getString(R.string.text_message_enter_city), Snackbar.LENGTH_LONG);
            }
            this.mWeatherPresenter.loadData(v.getText().toString());
            return true;
        }
        return false;
    }

    @Override
    public void showProgress() {
        this.mRlContentView.setVisibility(View.GONE);
        this.mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        this.mRlContentView.setVisibility(View.VISIBLE);
        this.mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(@NonNull String message) {
        this.mRlContentView.setVisibility(View.GONE);
        this.mProgressBar.setVisibility(View.GONE);
        UiUtils.showSnackBar(this.mRlContentView, getString(R.string.text_message_no_city_data), Snackbar.LENGTH_LONG);
    }

    @Override
    public void showData(@NonNull WeatherInfo weatherInfo) {
        hideProgress();
        this.mTvLastUpdate.setText(String.format(getString(R.string.text_last_update_date), DateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis()))));
        this.mTvCity.setText(weatherInfo.getName());
        this.mTvDescription.setText(weatherInfo.getWeather().get(0).getDescription());
        this.mTvTemp.setText(String.format("%s%s", weatherInfo.getMain().getTemp(), getString(R.string.celsius)));
        this.mTvHumidity.setText(String.format("%s: %s%%", getString(R.string.humidity), weatherInfo.getMain().getHumidity()));
        this.mTvPressure.setText(String.format("%s: %s m/s", getString(R.string.wind_speed), weatherInfo.getWind().getSpeed()));
        Picasso.with(this.mContext).load(String.format(getString(R.string.text_weather_icon_url), weatherInfo.getWeather().get(0).getIcon())).into(this.mIvWeatherIcon);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(count == 0){
            this.mRlContentView.setVisibility(View.GONE);
            this.mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
