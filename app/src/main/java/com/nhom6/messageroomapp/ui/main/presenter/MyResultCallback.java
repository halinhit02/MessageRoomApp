package com.nhom6.messageroomapp.ui.main.presenter;

import androidx.annotation.StringRes;

public interface MyResultCallback<T> {

    void onSuccess(T value);
    void onFailed(String message, @StringRes int errorRes);
}

