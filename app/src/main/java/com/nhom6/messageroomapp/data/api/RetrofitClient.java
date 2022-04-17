package com.nhom6.messageroomapp.data.api;

import static com.nhom6.messageroomapp.utils.Constant.BASE_GOOGLE_URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nhom6.messageroomapp.MRApplication;
import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.utils.Constant;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static volatile Retrofit retrofit = null;
    public static volatile Retrofit retrofit2 = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .setDateFormat("HH:mm:ss dd/MM/yyyy")
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientWithToken(String token) {
        if (retrofit2 == null) {
            String authToken = MRApplication.the().getString(R.string.base_token, token);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request request = chain.request();
                        Headers headers = request.headers().newBuilder().add("Authorization", authToken).build();
                        request = request.newBuilder().headers(headers).build();
                        return chain.proceed(request);
                    });
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .setDateFormat("HH:mm:ss dd/MM/yyyy")
                    .create();
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit2;
    }

    public static Retrofit getClientUploadFile() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BASE_GOOGLE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
