package com.nhom6.messageroomapp.ui.main.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.participant.Participant;
import com.nhom6.messageroomapp.data.model.participant.ParticipantAddRequest;
import com.nhom6.messageroomapp.data.model.user.UserSearchPagingResponse;
import com.nhom6.messageroomapp.data.model.user.UserSearchRequest;
import com.nhom6.messageroomapp.data.repository.APINetwork;

public class ParticipantAddViewModel extends ViewModel {

    public ObservableField<String> keywordField = new ObservableField<>("");
    public MutableLiveData<BaseAPIResponse<UserSearchPagingResponse>> userResponseLiveData = new MutableLiveData<>();
    public MutableLiveData<BaseAPIResponse<Participant>> addParticipantLiveData = new MutableLiveData<>();

    public String getKeyword() {
        return keywordField.get();
    }

    public void searchUser(String keyword) {
        UserSearchRequest request = new UserSearchRequest(keyword, 1, 20);
        APINetwork.SearchUser(request, userResponseLiveData);
    }

    public void addParticipant(ParticipantAddRequest request) {
        APINetwork.AddParticipant(request, addParticipantLiveData);
    }
}
