package com.nhom6.messageroomapp.data.repository;

import static com.nhom6.messageroomapp.utils.FileUtils.getMimeType;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.nhom6.messageroomapp.MRApplication;
import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.api.AuthenticateAPIService;
import com.nhom6.messageroomapp.data.api.DataAPIService;
import com.nhom6.messageroomapp.data.api.FileUploadService;
import com.nhom6.messageroomapp.data.api.RetrofitClient;
import com.nhom6.messageroomapp.data.model.auth.AuthResult;
import com.nhom6.messageroomapp.data.model.auth.LoginRequest;
import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.base.BaseGetPagingRequest;
import com.nhom6.messageroomapp.data.model.base.BasePagingResponse;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.conversation.Conversation;
import com.nhom6.messageroomapp.data.model.conversation.ConversationCreateRequest;
import com.nhom6.messageroomapp.data.model.conversation.ConversationEditRequest;
import com.nhom6.messageroomapp.data.model.conversation.ConversationGetRequest;
import com.nhom6.messageroomapp.data.model.message.MessageCreateRequest;
import com.nhom6.messageroomapp.data.model.message.MessageGetRequest;
import com.nhom6.messageroomapp.data.model.message.MessagePagingRequest;
import com.nhom6.messageroomapp.data.model.message.MessageResponse;
import com.nhom6.messageroomapp.data.model.participant.Participant;
import com.nhom6.messageroomapp.data.model.participant.ParticipantAddRequest;
import com.nhom6.messageroomapp.data.model.participant.ParticipantDeleteRequest;
import com.nhom6.messageroomapp.data.model.participant.ParticipantPagingRequest;
import com.nhom6.messageroomapp.data.model.participant.ParticipantUpdateRequest;
import com.nhom6.messageroomapp.data.model.storage.FileUploadRequest;
import com.nhom6.messageroomapp.data.model.storage.SignedUrl;
import com.nhom6.messageroomapp.data.model.storage.SignedUrlGetRequest;
import com.nhom6.messageroomapp.data.model.user.UserRegisterRequest;
import com.nhom6.messageroomapp.data.model.user.UserSearchPagingResponse;
import com.nhom6.messageroomapp.data.model.user.UserSearchRequest;
import com.nhom6.messageroomapp.data.model.user.UserUpdateRequest;
import com.nhom6.messageroomapp.ui.main.presenter.ObjectCallback;
import com.nhom6.messageroomapp.utils.Constant;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APINetwork {
    private static AuthenticateAPIService service = null;
    public static DataAPIService dataAPIService = null;

    public static AuthenticateAPIService getAuthAPIService() {
        if (service == null) {
            service = RetrofitClient.getClient().create(AuthenticateAPIService.class);
        }
        RetrofitClient.retrofit2 = null;
        return service;
    }

    public static DataAPIService getDataAPIService() {
        if (dataAPIService == null) {
            String token = getToken();
            dataAPIService = RetrofitClient.getClientWithToken(token).create(DataAPIService.class);
        }
        return dataAPIService;
    }

    private static String getToken() {
        SharedPreferences preferences = MRApplication.the().getSharedPreferences(MRApplication.the().getString(R.string.app_name), Context.MODE_PRIVATE);
        return preferences.getString(Constant.Token_Key, "");
    }

    public static void login(LoginRequest request, MutableLiveData<BaseAPIResponse<AuthResult>> authResponseCallback) {
        getAuthAPIService().login(request).enqueue(new LiveDataResponseCallback<>(authResponseCallback));
    }

    public static void register(UserRegisterRequest request, ObjectCallback<BaseAPIResponse<AuthResult>> authResponseCallback) {
        getAuthAPIService().register(request).enqueue(new ApiResponseCallback<>(authResponseCallback));
    }

    //conversation
    public static void GetAllConversation(BaseGetPagingRequest request, ObjectCallback<BaseAPIResponse<BasePagingResponse<Conversation>>> conversationCallback) {
        getDataAPIService().getConversationPaging(request.pageIndex, request.pageSize, request.userId)
                .enqueue(new ApiResponseCallback<>(conversationCallback));
    }

    public static void GetConversation(ConversationGetRequest request, MutableLiveData<BaseAPIResponse<Conversation>> conversationLiveData) {
        getDataAPIService().getConversation(request.getId(), request.getUserId())
                .enqueue(new LiveDataResponseCallback<>(conversationLiveData));
    }

    public static void CreateConversation(ConversationCreateRequest request, MutableLiveData<BaseAPIResponse<Conversation>> conversationCallback) {
        getDataAPIService().createConversation(request)
                .enqueue(new LiveDataResponseCallback<>(conversationCallback));
    }

    public static void EditConversation(ConversationEditRequest request, ObjectCallback<BaseAPIResponse<Boolean>> conversationCallback) {
        getDataAPIService().editConversation(request)
                .enqueue(new ApiResponseCallback<>(conversationCallback));
    }

    //message
    public static void GetAllMessage(MessagePagingRequest request, MutableLiveData<BaseAPIResponse<BasePagingResponse<MessageResponse>>> messageAllCallback) {
        getDataAPIService().getMessagePaging(request.pageIndex, request.pageSize, request.conversationId, request.userId)
                .enqueue(new LiveDataResponseCallback<>(messageAllCallback));
    }

    public static void CreateMessage(MessageCreateRequest request, ObjectCallback<BaseAPIResponse<MessageResponse>> messageCallback) {
        getDataAPIService().createMessage(request)
                .enqueue(new ApiResponseCallback<>(messageCallback));
    }

    public static void GetMessage(MessageGetRequest request, ObjectCallback<BaseAPIResponse<MessageResponse>> messageResponseCallback) {
        getDataAPIService().getMessage(request.getId(), request.getConversationId(), request.getUserId())
                .enqueue(new ApiResponseCallback<>(messageResponseCallback));
    }

    //participant
    public static void GetAllParticipant(ParticipantPagingRequest request, ObjectCallback<BaseAPIResponse<BasePagingResponse<Participant>>> participantCallback) {
        getDataAPIService().getParticipantPaging(request.pageIndex, request.pageSize, request.conversationId, request.userId)
                .enqueue(new ApiResponseCallback<>(participantCallback));
    }

    public static void AddParticipant(ParticipantAddRequest request, MutableLiveData<BaseAPIResponse<Participant>> updateParticipantCallback) {
        getDataAPIService().addParticipant(request).enqueue(new LiveDataResponseCallback<>(updateParticipantCallback));
    }

    public static void UpdateParticipant(ParticipantUpdateRequest request, MutableLiveData<BaseAPIResponse<Boolean>> updateParticipantCallback) {
        getDataAPIService().updateParticipant(request).enqueue(new LiveDataResponseCallback<>(updateParticipantCallback));
    }

    public static void DeleteParticipant(ParticipantDeleteRequest request, ObjectCallback<BaseAPIResponse<Boolean>> participantRemoveCallback) {
        getDataAPIService().deleteParticipant(request.getConversationId(), request.getUserId(), request.getLeaverId())
                .enqueue(new ApiResponseCallback<>(participantRemoveCallback));
    }

    //user
    public static void GetUserById(Integer userId, MutableLiveData<BaseAPIResponse<AppUser>> userCallback) {
        getDataAPIService().getUser(userId).enqueue(new LiveDataResponseCallback<>(userCallback));
    }

    public static void UpdateUser(UserUpdateRequest request, ObjectCallback<BaseAPIResponse<Boolean>> userUpdateCallback) {
        getDataAPIService().updateUser(request).enqueue(new ApiResponseCallback<>(userUpdateCallback));
    }

    public static void SearchUser(UserSearchRequest request, MutableLiveData<BaseAPIResponse<UserSearchPagingResponse>> userSearchLiveData) {
        getDataAPIService().searchUser(request.pageIndex, request.pageSize, request.keyword)
                .enqueue(new LiveDataResponseCallback<>(userSearchLiveData));
    }

    //upload image
    public static void GetUploadSignedUrl(SignedUrlGetRequest request, ObjectCallback<BaseAPIResponse<SignedUrl>> responseCallback) {
        getDataAPIService().getUploadSignedUrl(request.getFolderName(), request.getId(), request.getFileName())
                .enqueue(new ApiResponseCallback<>(responseCallback));
    }

    public static void UploadFileStorage(FileUploadRequest request, ObjectCallback<BaseAPIResponse<String>> bodyObjectCallback) {
        FileUploadService service = RetrofitClient.getClientUploadFile().create(FileUploadService.class);
        File uploadedFile = request.getFile(); //FileUtils.createCopyAndReturnFile(HLApplication.the(), fileUri);
        if (uploadedFile == null) {
            bodyObjectCallback.onVoid(new BaseAPIResponse<>(false, "Đã xảy ra lỗi."));
            return;
        }
        // create RequestBody instance from file
        String path = request.getSignedUrl();
        String contentType = getMimeType(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse(contentType), uploadedFile);
        service.uploadFile(contentType, path, requestFile).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    bodyObjectCallback.onVoid(new BaseAPIResponse<>(true, ""));
                } else {
                    try {
                        Log.d("HLTag", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    bodyObjectCallback.onVoid(new BaseAPIResponse<String>(false, "Đã xảy ra lỗi.", response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                bodyObjectCallback.onVoid(new BaseAPIResponse<>(false, t.getMessage()));
            }
        });
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
            mutableLiveData.setValue(new BaseAPIResponse<>(false, "Đã xảy ra lỗi!"));
        }
    }
}
