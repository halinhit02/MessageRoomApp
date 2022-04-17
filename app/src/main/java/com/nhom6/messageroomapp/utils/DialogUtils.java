package com.nhom6.messageroomapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;

import com.nhom6.messageroomapp.R;

public class DialogUtils {
    public static AlertDialog alertDialog;
    public static void showLoadingDialog(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.loading_layout, null);
        AlertDialog loadDialog = new AlertDialog.Builder(context)
                .setView(v)
                .setCancelable(false)
                .create();
        Window window = loadDialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        loadDialog.show();
        alertDialog = loadDialog;
    }

    public static void dismissDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }
}
