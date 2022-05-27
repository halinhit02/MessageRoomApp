package com.nhom6.messageroomapp.ui.main.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.conversation.Conversation;
import com.nhom6.messageroomapp.data.model.participant.Participant;

import java.util.List;

public class ApplicationViewModel extends ViewModel {
    public MutableLiveData<Conversation> conversationGetLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Participant>> participantGetLiveData = new MutableLiveData<>();
    public MutableLiveData<AppUser> appUserLiveData = new MutableLiveData<>();

    public void setAppUser(AppUser appUser) {
        appUserLiveData.postValue(appUser);
    }

    public void setGlobalConversation(Conversation conversation) {
        conversationGetLiveData.postValue(conversation);
    }
    public void setParticipants(List<Participant> participantList) {
        participantGetLiveData.postValue(participantList);
    }
}
