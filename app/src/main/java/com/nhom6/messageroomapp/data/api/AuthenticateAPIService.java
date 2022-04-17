package com.nhom6.messageroomapp.data.api;

import com.nhom6.messageroomapp.data.model.auth.AuthResult;
import com.nhom6.messageroomapp.data.model.auth.LoginRequest;
import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.user.UserRegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticateAPIService {

    @POST("api/users/authenticate")
    Call<BaseAPIResponse<AuthResult>> login(@Body LoginRequest request);

    @POST("api/users/register")
    Call<BaseAPIResponse<AuthResult>> register(@Body UserRegisterRequest request);
}
