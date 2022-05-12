package com.nhom6.messageroomapp.ui.main.viewmodel;

import android.net.Uri;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.conversation.Conversation;
import com.nhom6.messageroomapp.data.model.conversation.ConversationCreateRequest;
import com.nhom6.messageroomapp.data.model.user.UserSearchPagingResponse;
import com.nhom6.messageroomapp.data.model.user.UserSearchRequest;
import com.nhom6.messageroomapp.data.repository.APINetwork;

import java.util.List;

public class ConversationCreateViewModel extends ViewModel {
    public ObservableField<ConversationCreateRequest> createRequestField = new ObservableField<>(new ConversationCreateRequest());
    public ObservableField<String> avatarField = new ObservableField<>();
    public ObservableField<String> keywordField = new ObservableField<>("");

    public MutableLiveData<BaseAPIResponse<Conversation>> conversationResponseLiveDate = new MutableLiveData<>();
    public MutableLiveData<BaseAPIResponse<UserSearchPagingResponse>> userResponseLiveData = new MutableLiveData<>();
    private Uri avatarUri = null;

    public String getKeyword() {
        return keywordField.get();
    }

    public void createConversation(List<Integer> userIdList) {
        ConversationCreateRequest request = createRequestField.get();
        if (request == null) {
            request = new ConversationCreateRequest();
        }
        request.setUserIds(userIdList);
        APINetwork.CreateConversation(request, conversationResponseLiveDate);
    }

    public void searchUser(String keyword) {
        UserSearchRequest request = new UserSearchRequest(keyword, 1, 20);
        APINetwork.SearchUser(request, userResponseLiveData);
    }
}

