package com.nhom6.messageroomapp.data.workers;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.google.gson.Gson;
import com.nhom6.messageroomapp.data.model.base.BaseGetPagingRequest;
import com.nhom6.messageroomapp.data.model.storage.FileUploadRequest;
import com.nhom6.messageroomapp.data.model.storage.SignedUrlGetRequest;
import com.nhom6.messageroomapp.data.model.user.UserUpdateRequest;

public class WorkerLiveData {

    public static LiveData<WorkInfo> AllConversationGet(Context context, BaseGetPagingRequest request) {
        String conversationPagingGet = new Gson().toJson(request, BaseGetPagingRequest.class);
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(ConversationPagingGetWorker.class)
                .setInputData(new Data.Builder().putString("AllConversationGetRequest", conversationPagingGet).build())
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build();
        WorkManager.getInstance(context)
                .enqueueUniqueWork("AllConversationGetRequest", ExistingWorkPolicy.APPEND_OR_REPLACE, workRequest);
        return WorkManager.getInstance(context).getWorkInfoByIdLiveData(workRequest.getId());
    }

    public static LiveData<WorkInfo> UserUpdate(Context context, UserUpdateRequest request) {
        String userUpdate = new Gson().toJson(request, UserUpdateRequest.class);
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(UserUpdateWorker.class)
                .setInputData(new Data.Builder().putString("UserUpdateRequest", userUpdate).build())
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build();
        WorkManager.getInstance(context)
                .enqueueUniqueWork("UserUpdate", ExistingWorkPolicy.APPEND_OR_REPLACE, workRequest);
        return WorkManager.getInstance(context).getWorkInfoByIdLiveData(workRequest.getId());
    }

    public static LiveData<WorkInfo> SignedUrlGet(Context context, SignedUrlGetRequest request) {
        String userUpdate = new Gson().toJson(request, SignedUrlGetRequest.class);
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(SignedUrlGetWorker.class)
                .setInputData(new Data.Builder().putString("SignedUrlGet", userUpdate).build())
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build();
        WorkManager.getInstance(context)
                .enqueueUniqueWork("SignedUrlGet", ExistingWorkPolicy.APPEND_OR_REPLACE, workRequest);
        return WorkManager.getInstance(context).getWorkInfoByIdLiveData(workRequest.getId());
    }

    public static LiveData<WorkInfo> FileUpload(Context context, FileUploadRequest request) {
        String fileUpload = new Gson().toJson(request, FileUploadRequest.class);
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(FileUploadWorker.class)
                .setInputData(new Data.Builder().putString("FileUpload", fileUpload).build())
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build();
        WorkManager.getInstance(context)
                .enqueueUniqueWork("FileUploadPut", ExistingWorkPolicy.APPEND_OR_REPLACE, workRequest);
        return WorkManager.getInstance(context).getWorkInfoByIdLiveData(workRequest.getId());
    }
}
