package com.nhom6.messageroomapp.data.api;

import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.base.BasePagingResponse;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.conversation.Conversation;
import com.nhom6.messageroomapp.data.model.conversation.ConversationCreateRequest;
import com.nhom6.messageroomapp.data.model.storage.SignedUrl;
import com.nhom6.messageroomapp.data.model.user.UserSearchPagingResponse;
import com.nhom6.messageroomapp.data.model.user.UserUpdateRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataAPIService {

    @GET("api/conversations")
    Call<BaseAPIResponse<BasePagingResponse<Conversation>>> getConversationPaging(@Query("pageIndex") Integer index,
                                                                                  @Query("pageSize") Integer size,
                                                                                  @Query("userId") Integer userId);

    @POST("api/conversations")
    Call<BaseAPIResponse<Conversation>> createConversation(@Body ConversationCreateRequest request);


    @GET("api/users/{id}")
    Call<BaseAPIResponse<AppUser>> getUser(@Path("id") Integer userId);

    @PUT("api/users")
    Call<BaseAPIResponse<Boolean>> updateUser(@Body UserUpdateRequest request);

    @GET("api/users")
    Call<BaseAPIResponse<UserSearchPagingResponse>> searchUser(@Query("pageIndex") Integer index,
                                                               @Query("pageSize") Integer size,
                                                               @Query("keyword") String keyword);

    @GET("api/storages")
    Call<BaseAPIResponse<SignedUrl>> getUploadSignedUrl(@Query("folderName") String folderName,
                                                        @Query("id") Integer id,
                                                        @Query("fileName") String fileName);
}
