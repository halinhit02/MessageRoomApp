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
import com.nhom6.messageroomapp.data.model.message.MessageCreateRequest;
import com.nhom6.messageroomapp.data.model.message.MessageCreateResponse;
import com.nhom6.messageroomapp.data.model.message.MessageResponse;
import com.nhom6.messageroomapp.data.repository.APINetwork;
import com.nhom6.messageroomapp.ui.main.presenter.ObjectCallback;
import com.nhom6.messageroomapp.utils.Constant;

public class UploadMessageWorker<T, G> extends ListenableWorker {
    public UploadMessageWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        Data inputData = getInputData();
        MessageCreateRequest request = new Gson().fromJson(inputData.getString("MessageCreateRequest"), MessageCreateRequest.class);
        return CallbackToFutureAdapter.getFuture(completer -> {
            ObjectCallback<BaseAPIResponse<MessageResponse>> callback = object -> {
                String result = new Gson().toJson(object, MessageCreateResponse.class);
                Data output = new Data.Builder()
                        .putString(Constant.OUTPUT_DATA_TAG, result)
                        .build();
                completer.set(Result.success(output));
            };
            APINetwork.CreateMessage(request, callback);
            return callback;
        });
    }
}
