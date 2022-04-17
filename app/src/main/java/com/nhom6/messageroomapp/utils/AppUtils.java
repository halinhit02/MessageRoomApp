package com.nhom6.messageroomapp.utils;

import static android.content.Context.MODE_PRIVATE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;
import com.nhom6.messageroomapp.MRApplication;
import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.common.AppUser;

public class AppUtils {

    private static final String nameSharePref = MRApplication.the().getString(R.string.app_name);

    public static boolean checkPermission(FragmentActivity context) {
        if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            context.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.WRITE_STORAGE_REQUEST_CODE);
            return false;
        }
        return true;
    }

    public static AppUser getAppUser() {
        SharedPreferences sharedPreferences = MRApplication.the().getSharedPreferences(nameSharePref, MODE_PRIVATE);
        String responseString =  sharedPreferences.getString(Constant.Response_Key, "");
        return new Gson().fromJson(responseString, AppUser.class);
    }

    public static void saveAppUser(AppUser appUser) {
        SharedPreferences sharedPreferences = MRApplication.the().getSharedPreferences(nameSharePref, MODE_PRIVATE);
        String appUserString = new Gson().toJson(appUser, AppUser.class);
        sharedPreferences.edit().putString(Constant.Response_Key, appUserString).apply();
    }

    public static void saveStringSharedRef(String key, String value) {
        SharedPreferences sharedPreferences = MRApplication.the().getSharedPreferences(nameSharePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringSharedPref (String key) {
        SharedPreferences sharedPreferences = MRApplication.the().getSharedPreferences(nameSharePref, MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void clearSharedPref() {
        SharedPreferences sharedPreferences = MRApplication.the().getSharedPreferences(nameSharePref, MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public static void showMessageResult(String message) {
        if (DialogUtils.alertDialog != null)
            DialogUtils.alertDialog.dismiss();
        MRApplication.showMessage(message);
    }
}
