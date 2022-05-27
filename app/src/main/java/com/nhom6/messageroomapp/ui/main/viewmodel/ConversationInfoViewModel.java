package com.nhom6.messageroomapp.ui.main.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.conversation.Conversation;
import com.nhom6.messageroomapp.data.model.participant.Participant;
import com.nhom6.messageroomapp.data.model.participant.ParticipantDeleteRequest;
import com.nhom6.messageroomapp.data.repository.APINetwork;

public class ConversationInfoViewModel extends ViewModel {
    public ObservableField<Conversation> conversationField = new ObservableField<>();
    public ObservableField<Participant> participantField = new ObservableField<>();
    public MutableLiveData<BaseAPIResponse<Boolean>> deleteParticipantLiveData;
    public void init() {
        deleteParticipantLiveData = new MutableLiveData<>();
    }

    public void setCurrentParticipant(Participant participant) {
        participantField.set(participant);
    }

    public void setConversation(Conversation conversation) {
        conversationField.set(conversation);
    }

    public void deleteParticipant(ParticipantDeleteRequest request) {
        APINetwork.DeleteParticipant(request, object -> deleteParticipantLiveData.setValue(object));
    }
}

