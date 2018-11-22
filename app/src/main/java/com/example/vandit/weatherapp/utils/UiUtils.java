package com.example.vandit.weatherapp.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class UiUtils {

    public static void showSnackBar(View view, String message, int length){
        Snackbar.make(view, message, length).show();
    }
}
