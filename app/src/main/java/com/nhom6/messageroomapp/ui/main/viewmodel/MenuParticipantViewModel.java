package com.nhom6.messageroomapp.ui.main.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.participant.Participant;
import com.nhom6.messageroomapp.data.model.participant.ParticipantDeleteRequest;
import com.nhom6.messageroomapp.data.model.participant.ParticipantUpdateRequest;
import com.nhom6.messageroomapp.data.repository.APINetwork;

public class MenuParticipantViewModel extends ViewModel {

    public ObservableField<String> newNickNameField = new ObservableField<>();
    public ObservableField<Participant> participantField = new ObservableField<>();
    public ObservableField<Participant> mParticipantField = new ObservableField<>();
    public MutableLiveData<BaseAPIResponse<Boolean>> updateLiveData = new MutableLiveData<>();
    public MutableLiveData<BaseAPIResponse<Boolean>> deleteParticipantLiveData = new MutableLiveData<>();

    public void setNickName(String nickName){
        newNickNameField.set(nickName);
    }

    public void setParticipant(Participant participant) {
        participantField.set(participant);
    }

    public void updateParticipant(ParticipantUpdateRequest request) {
        APINetwork.UpdateParticipant(request, updateLiveData);
    }

    public void deleteParticipant(ParticipantDeleteRequest request) {
        APINetwork.DeleteParticipant(request, object -> deleteParticipantLiveData.setValue(object));
    }
}
