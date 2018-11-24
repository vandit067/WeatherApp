package com.example.vandit.weatherapp.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;

public class UiUtils {

    public static void showSnackBar(View view, String message, int length){
        Snackbar.make(view, message, length).show();
    }

    public static void hideKeyBoard(@NonNull View view){
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
