package com.nhom6.messageroomapp.data.api;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface FileUploadService {
    @PUT
    Call<ResponseBody> uploadFile(@Header("Content-Type") String contentType,
                                  @Url String signedUrl,
                                  @Body RequestBody body);
}
