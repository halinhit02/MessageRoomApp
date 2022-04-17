package com.nhom6.messageroomapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.nhom6.messageroomapp.MRApplication;
import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.api.AuthenticateAPIService;
import com.nhom6.messageroomapp.data.api.RetrofitClient;
import com.nhom6.messageroomapp.data.model.auth.AuthResult;
import com.nhom6.messageroomapp.data.model.auth.LoginRequest;
import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.user.UserRegisterRequest;
import com.nhom6.messageroomapp.ui.main.presenter.ObjectCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APINetwork {
    private static AuthenticateAPIService service = null;

    public static AuthenticateAPIService getAuthAPIService() {
        if (service == null) {
            service = RetrofitClient.getClient().create(AuthenticateAPIService.class);
        }
        RetrofitClient.retrofit2 = null;
        return service;
    }

    public static void login(LoginRequest request, MutableLiveData<BaseAPIResponse<AuthResult>> authResponseCallback) {
        getAuthAPIService().login(request).enqueue(new LiveDataResponseCallback<>(authResponseCallback));
    }

    public static void register(UserRegisterRequest request, ObjectCallback<BaseAPIResponse<AuthResult>> authResponseCallback) {
        getAuthAPIService().register(request).enqueue(new ApiResponseCallback<>(authResponseCallback));
    }

    static class ApiResponseCallback<T> implements Callback<BaseAPIResponse<T>> {

        private final ObjectCallback<BaseAPIResponse<T>> callback;

        public ApiResponseCallback(ObjectCallback<BaseAPIResponse<T>> objectCallback) {
            this.callback = objectCallback;
        }

        @Override
        public void onResponse(@NonNull Call<BaseAPIResponse<T>> call, @NonNull Response<BaseAPIResponse<T>> response) {
            if (response.isSuccessful() && response.body() != null) {
                callback.onVoid(response.body());
                return;
            }
            callback.onVoid(new BaseAPIResponse<T>(false, MRApplication.the().getString(R.string.error_response), response.code()));
        }

        @Override
        public void onFailure(@NonNull Call<BaseAPIResponse<T>> call, @NonNull Throwable t) {
            callback.onVoid(new BaseAPIResponse<>(false, MRApplication.the().getString(R.string.error_response)));
        }
    }

    static class LiveDataResponseCallback<T> implements Callback<BaseAPIResponse<T>> {

        private final MutableLiveData<BaseAPIResponse<T>> mutableLiveData;

        public LiveDataResponseCallback(MutableLiveData<BaseAPIResponse<T>> mutableLiveData) {
            this.mutableLiveData = mutableLiveData;
        }

        @Override
        public void onResponse(@NonNull Call<BaseAPIResponse<T>> call, @NonNull Response<BaseAPIResponse<T>> response) {
            if (response.isSuccessful() && response.body() != null) {
                mutableLiveData.setValue(response.body());
                return;
            }
            mutableLiveData.setValue(new BaseAPIResponse<T>(false, MRApplication.the().getString(R.string.error_response), response.code()));
        }

        @Override
        public void onFailure(@NonNull Call<BaseAPIResponse<T>> call, @NonNull Throwable t) {
            mutableLiveData.setValue(new BaseAPIResponse<>(false, MRApplication.the().getString(R.string.error_response)));
        }
    }
}
