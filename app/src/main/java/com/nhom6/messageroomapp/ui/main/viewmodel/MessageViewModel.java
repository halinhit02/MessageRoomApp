package com.nhom6.messageroomapp.ui.main.viewmodel;

import static com.nhom6.messageroomapp.utils.AppUtils.showMessageResult;

import android.net.Uri;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.WorkInfo;

import com.google.gson.Gson;
import com.nhom6.messageroomapp.MRApplication;
import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.base.BasePagingResponse;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.conversation.Conversation;
import com.nhom6.messageroomapp.data.model.message.ChatManager;
import com.nhom6.messageroomapp.data.model.message.MessageClient;
import com.nhom6.messageroomapp.data.model.message.MessageCreateRequest;
import com.nhom6.messageroomapp.data.model.message.MessageCreateResponse;
import com.nhom6.messageroomapp.data.model.message.MessageGetRequest;
import com.nhom6.messageroomapp.data.model.message.MessageGetResponse;
import com.nhom6.messageroomapp.data.model.message.MessagePagingRequest;
import com.nhom6.messageroomapp.data.model.message.MessageResponse;
import com.nhom6.messageroomapp.data.model.participant.ParticipantPagingRequest;
import com.nhom6.messageroomapp.data.model.participant.ParticipantPagingResponse;
import com.nhom6.messageroomapp.data.model.storage.FileUploadRequest;
import com.nhom6.messageroomapp.data.model.storage.FolderName;
import com.nhom6.messageroomapp.data.model.storage.SignedUrl;
import com.nhom6.messageroomapp.data.model.storage.SignedUrlGetRequest;
import com.nhom6.messageroomapp.data.model.storage.SignedUrlGetResponse;
import com.nhom6.messageroomapp.data.repository.APINetwork;
import com.nhom6.messageroomapp.data.workers.WorkerLiveData;
import com.nhom6.messageroomapp.ui.main.presenter.MyResultCallback;
import com.nhom6.messageroomapp.utils.Constant;
import com.nhom6.messageroomapp.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmChannel;
import io.agora.rtm.RtmChannelListener;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmMessage;
import io.agora.rtm.RtmStatusCode;

public class MessageViewModel extends ViewModel {
    public ObservableField<Conversation> conversationField = new ObservableField<>();
    public ObservableField<String> content = new ObservableField<>("");
    public ObservableField<Boolean> enableSend = new ObservableField<>();
    public ObservableField<Boolean> isLoading = new ObservableField<>(true);
    public ObservableField<Boolean> isMenuShowed = new ObservableField<>(false);

    public MutableLiveData<BaseAPIResponse<BasePagingResponse<MessageResponse>>> messagePagingLiveData;
    public MutableLiveData<MessageResponse> messageCreateLiveData;
    public MutableLiveData<ParticipantPagingResponse> participantLiveData;
    public MutableLiveData<MessageResponse> messageGetLiveData;

    private Conversation mConversation;
    private AppUser mUser;
    private RtmClient mRtmClient;
    private RtmChannel mRtmChannel;
    private RtmClientListener mClientListener;
    private MessageCreateRequest imageCreateRequest;
    private FragmentActivity context;
    private LifecycleOwner lifecycleOwner;

    public RtmClient getRtmClient() {
        return mRtmClient;
    }

    public void setClientListener(RtmClientListener mClientListener) {
        this.mClientListener = mClientListener;
    }

    public void setConversation(Conversation conversation) {
        conversationField.set(conversation);
        this.mConversation = conversation;
    }

    public void setAppUser(AppUser mUser) {
        this.mUser = mUser;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    public void init(FragmentActivity context, LifecycleOwner lifecycleOwner, Conversation conversation, AppUser appUser) {
        setIsLoading(true);
        this.context = context;
        this.mUser = appUser;
        this.mConversation = conversation;
        this.lifecycleOwner = lifecycleOwner;
        conversationField.set(conversation);
        enableSend.set(true);
        participantLiveData = new MutableLiveData<>();
        messagePagingLiveData = new MutableLiveData<>();
        messageCreateLiveData = new MutableLiveData<>();
        messageGetLiveData = new MutableLiveData<>();
        ChatManager mChatManager = MRApplication.the().getChatManager();
        mChatManager.setRtmClientListener(mClientListener);
        mRtmClient = mChatManager.getRtmClient();
        getParticipants();
    }

    public void createAndJoinChannel(String channelId, RtmChannelListener listener, MyResultCallback<String> callback) {
        mRtmChannel = mRtmClient.createChannel(channelId, listener);
        if (mRtmChannel == null) {
            callback.onFailed(null, R.string.join_failed);
            return;
        }
        Log.e("channel", mRtmChannel + "");

        //join channel
        mRtmChannel.join(new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("channel", "join channel success");
                callback.onSuccess("join channel success");
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Log.e("channel", "join channel failed");
                callback.onFailed(null, R.string.join_failed);
            }
        });
    }

    private void getParticipants() {
        ParticipantPagingRequest request = new ParticipantPagingRequest(-1, 0, mConversation.getId(), mUser.getId());
        WorkerLiveData.AllParticipantGet(context, request)
                .observe(lifecycleOwner, workInfo -> {
                    if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        String output = workInfo.getOutputData().getString(Constant.OUTPUT_DATA_TAG);
                        ParticipantPagingResponse response = new Gson().fromJson(output, ParticipantPagingResponse.class);
                        participantLiveData.postValue(response);
                    }
                });
    }

    public void getAllMessages() {
        MessagePagingRequest request = new MessagePagingRequest(1, 20, mConversation.getId(), mUser.getId());
        APINetwork.GetAllMessage(request, messagePagingLiveData);
        /*WorkerLiveData.AllMessageGet(context, request)
                .observe(context, workInfo -> {
                    if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        String output = workInfo.getOutputData().getString(Constant.OUTPUT_DATA_TAG);
                        MessagePagingResponse response = new Gson().fromJson(output, MessagePagingResponse.class);

                    }
                });*/
    }

    public void getMessage(MessageGetRequest request) {
        WorkerLiveData.GetMessage(context, request)
                .observe(context, workInfo -> {
                    if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        String output = workInfo.getOutputData().getString(Constant.OUTPUT_DATA_TAG);
                        MessageGetResponse response = new Gson().fromJson(output, MessageGetResponse.class);
                        if (response.isSucceeded) {
                            messageGetLiveData.setValue(response.getResult());
                        } else {
                            showMessageResult(response.getMessage());
                        }
                    }
                });
    }

    public void sendMessage(int messageId, MyResultCallback<MessageClient> resultCallback) {
        RtmMessage rtmMessage = mRtmClient.createMessage();
        rtmMessage.setText(String.valueOf(messageId));
        sendChannelMessage(rtmMessage, resultCallback);
    }

    public void sendText() {
        String message = content.get();
        if (message != null && !message.isEmpty()) {
            MessageCreateRequest request = new MessageCreateRequest(mUser.getId(), mConversation.getId(), message, 0);
            createMessage(request);
        }
        content.set("");
    }

    public void sendImage(Uri resultUri) {
        File uploadedFile = new File(resultUri.getPath());
        getUploadSignedUrl(uploadedFile, 1);
    }

    public void sendFile(Uri resultUri) {
        final File uploadFile = FileUtils.createCopyAndReturnFile(context, resultUri);
        if (uploadFile == null) {
            showMessageResult("Đã xảy ra lỗi.");
            return;
        }
        getUploadSignedUrl(uploadFile, 4);
    }

    private void sendChannelMessage(RtmMessage rtmMessage, MyResultCallback<MessageClient> callback) {
        mRtmChannel.sendMessage(rtmMessage, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void unused) {
                callback.onSuccess(null);
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                final int errorCode = errorInfo.getErrorCode();
                switch (errorCode) {
                    case RtmStatusCode.ChannelMessageError.CHANNEL_MESSAGE_ERR_TIMEOUT:
                    case RtmStatusCode.ChannelMessageError.CHANNEL_MESSAGE_ERR_FAILURE:
                        callback.onFailed(null, R.string.send_msg_failed);
                        break;
                }
            }
        });
    }

    public void getUploadSignedUrl(File uploadFile, int messageType) {
        String fileName = uploadFile.getName();
        SignedUrlGetRequest request = new SignedUrlGetRequest(FolderName.conversations, mConversation.getId(), fileName);
        WorkerLiveData.SignedUrlGet(context, request)
                .observe(context, workInfo -> {
                    if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        SignedUrlGetResponse response = new Gson().fromJson(workInfo.getOutputData().getString(Constant.OUTPUT_DATA_TAG), SignedUrlGetResponse.class);
                        SignedUrl signedUrl = response.getResult();
                        if (response.isSucceeded() && response.getResult() != null) {
                            List<String> attachments = new ArrayList<>();
                            attachments.add(signedUrl.getFileUrl());
                            imageCreateRequest = new MessageCreateRequest(mUser.getId(), mConversation.getId(), messageType, attachments);
                            FileUploadRequest uploadRequest = new FileUploadRequest(signedUrl.getSignedUrl(), uploadFile);
                            uploadFile(uploadRequest);
                        } else {
                            showMessageResult(response.getMessage());
                        }
                    }
                });
    }

    private void uploadFile(FileUploadRequest request) {
        WorkerLiveData.FileUpload(context, request).observe(context, workInfo -> {
            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                BaseAPIResponse response = new Gson().fromJson(workInfo.getOutputData().getString(Constant.OUTPUT_DATA_TAG), BaseAPIResponse.class);
                if (response.isSucceeded) {
                    createMessage(imageCreateRequest);
                } else {
                    showMessageResult(response.getMessage());
                }
            }
        });
    }

    public void createMessage(MessageCreateRequest request) {
        WorkerLiveData.MessageCreate(request)
                .observe(lifecycleOwner, workInfo -> {
                    if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        String output = workInfo.getOutputData().getString(Constant.OUTPUT_DATA_TAG);
                        MessageCreateResponse response = new Gson().fromJson(output, MessageCreateResponse.class);
                        if (!response.isSucceeded) {
                            showMessageResult(response.getMessage());
                            return;
                        }
                        messageCreateLiveData.postValue(response.getResult());
                    }
                });
    }
}
