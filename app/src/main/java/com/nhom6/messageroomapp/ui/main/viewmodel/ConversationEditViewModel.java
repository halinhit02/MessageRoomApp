package com.nhom6.messageroomapp.ui.main.viewmodel;

import static com.nhom6.messageroomapp.MRApplication.showMessage;
import static com.nhom6.messageroomapp.utils.AppUtils.showMessageResult;

import android.app.Application;
import android.net.Uri;

import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.WorkInfo;

import com.google.gson.Gson;
import com.nhom6.messageroomapp.MRApplication;
import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.conversation.Conversation;
import com.nhom6.messageroomapp.data.model.conversation.ConversationEditRequest;
import com.nhom6.messageroomapp.data.model.storage.FileUploadRequest;
import com.nhom6.messageroomapp.data.model.storage.FolderName;
import com.nhom6.messageroomapp.data.model.storage.SignedUrl;
import com.nhom6.messageroomapp.data.model.storage.SignedUrlGetRequest;
import com.nhom6.messageroomapp.data.model.storage.SignedUrlGetResponse;
import com.nhom6.messageroomapp.data.repository.APINetwork;
import com.nhom6.messageroomapp.data.workers.WorkerLiveData;
import com.nhom6.messageroomapp.utils.Constant;
import com.nhom6.messageroomapp.utils.DialogUtils;
import com.nhom6.messageroomapp.utils.FileUtils;

import java.io.File;

public class ConversationEditViewModel extends ViewModel {
    public ObservableField<Conversation> conversationEditField = new ObservableField<>();
    public ObservableField<String> avatarUrlField = new ObservableField<>();
    public MutableLiveData<Conversation> saveConversationResponse = new MutableLiveData<>();
    private Conversation conversation;
    private AppUser appUser;
    private Uri newAvatarUri;
    private ConversationEditRequest conversationEditRequest;
    private FragmentActivity context;

    public void setContext(FragmentActivity context) {
        this.context = context;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
        conversationEditField.set(conversation);
        if (newAvatarUri == null) {
            avatarUrlField.set(conversation.getAvatar());
        }
    }

    public void setAvatar(Uri avatarUri) {
        this.newAvatarUri = avatarUri;
        avatarUrlField.set(avatarUri.toString());
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public void saveConversation() {
        DialogUtils.showLoadingDialog(context);
        conversationEditRequest = new ConversationEditRequest(conversation.getId(), appUser.getId(), conversation.getTitle(), conversation.getDescription(), conversation.getAvatar(), conversation.getBackground());
        if (newAvatarUri != null) {
            getUploadSignedUrl(newAvatarUri);
        } else {
            saveConversationChanged(conversationEditRequest);
        }
    }

    public void getUploadSignedUrl(Uri resultUri) {
        String fileName = FileUtils.getFileName(context, resultUri);
        File uploadFile = FileUtils.createCopyAndReturnFile(MRApplication.the(), resultUri);
        SignedUrlGetRequest request = new SignedUrlGetRequest(FolderName.conversations, conversation.getId(), fileName);
        WorkerLiveData.SignedUrlGet(context, request)
                .observe(context, workInfo -> {
                    if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        SignedUrlGetResponse response = new Gson().fromJson(workInfo.getOutputData().getString(Constant.OUTPUT_DATA_TAG), SignedUrlGetResponse.class);
                        SignedUrl signedUrl = response.getResult();
                        if (response.isSucceeded() && response.getResult() != null) {
                            conversationEditRequest.setAvatar(signedUrl.getFileUrl());
                            conversation.setAvatar(signedUrl.getFileUrl());
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
                    saveConversationChanged(conversationEditRequest);
                } else {
                    showMessageResult(response.getMessage());
                }
            }
        });
    }

    private void saveConversationChanged(ConversationEditRequest request) {
        APINetwork.EditConversation(request, apiResponse -> {
            DialogUtils.dismissDialog();
            if (!apiResponse.isSucceeded) {
                showMessage(apiResponse.getMessage());
                return;
            }
            saveConversationResponse.setValue(conversation);
        });
    }
}
