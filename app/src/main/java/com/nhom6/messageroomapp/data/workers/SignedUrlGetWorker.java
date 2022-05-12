package com.nhom6.messageroomapp.data.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.storage.SignedUrl;
import com.nhom6.messageroomapp.data.model.storage.SignedUrlGetRequest;
import com.nhom6.messageroomapp.data.model.storage.SignedUrlGetResponse;
import com.nhom6.messageroomapp.data.repository.APINetwork;
import com.nhom6.messageroomapp.ui.main.presenter.ObjectCallback;
import com.nhom6.messageroomapp.utils.Constant;

public class SignedUrlGetWorker extends ListenableWorker {
    public SignedUrlGetWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        Data input = getInputData();
        SignedUrlGetRequest request = new Gson().fromJson(input.getString("SignedUrlGet"), SignedUrlGetRequest.class);
        return CallbackToFutureAdapter.getFuture(completer -> {
            ObjectCallback<BaseAPIResponse<SignedUrl>> callback = object -> {
                String result = new Gson().toJson(object, SignedUrlGetResponse.class);
                Data output = new Data.Builder()
                        .putString(Constant.OUTPUT_DATA_TAG, result)
                        .build();
                completer.set(Result.success(output));
            };
            APINetwork.GetUploadSignedUrl(request, callback);
            return callback;
        });
    }
}
