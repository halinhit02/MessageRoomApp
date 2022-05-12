package com.nhom6.messageroomapp;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class MRApplication extends Application {
    private static MRApplication sInstance;

    public static MRApplication the() {
        return sInstance;
    }

    public static void showMessage(String message) {
        Toast.makeText(sInstance, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}

