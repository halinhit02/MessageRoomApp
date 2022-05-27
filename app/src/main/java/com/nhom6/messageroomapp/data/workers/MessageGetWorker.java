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
import com.nhom6.messageroomapp.data.model.message.MessageGetRequest;
import com.nhom6.messageroomapp.data.model.message.MessageGetResponse;
import com.nhom6.messageroomapp.data.model.message.MessageResponse;
import com.nhom6.messageroomapp.data.repository.APINetwork;
import com.nhom6.messageroomapp.ui.main.presenter.ObjectCallback;
import com.nhom6.messageroomapp.utils.Constant;

public class MessageGetWorker extends ListenableWorker {
    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public MessageGetWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        Data input = getInputData();
        MessageGetRequest request = new Gson().fromJson(input.getString("MessageGet"), MessageGetRequest.class);
        return CallbackToFutureAdapter.getFuture(completer -> {
            ObjectCallback<BaseAPIResponse<MessageResponse>> callback = object -> {
                String result = new Gson().toJson(object, MessageGetResponse.class);
                Data output = new Data.Builder()
                        .putString(Constant.OUTPUT_DATA_TAG, result)
                        .build();
                completer.set(Result.success(output));
            };
            APINetwork.GetMessage(request, callback);
            return callback;
        });
    }
}