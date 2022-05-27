package com.nhom6.messageroomapp;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.nhom6.messageroomapp.data.model.message.ChatManager;

public class MRApplication extends Application {
    private static MRApplication sInstance;
    private ChatManager mChatManager;

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
        mChatManager = new ChatManager(this);
    }

    public ChatManager getChatManager() {
        return mChatManager;
    }
}

