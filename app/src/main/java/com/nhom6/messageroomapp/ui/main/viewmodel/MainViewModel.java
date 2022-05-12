package com.nhom6.messageroomapp.ui.main.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.repository.APINetwork;

public class MainViewModel extends ViewModel {
    public ObservableField<Integer> selectedId = new ObservableField<>();
    public ObservableField<NavController> navControllerField = new ObservableField<>();
    public MutableLiveData<BaseAPIResponse<AppUser>> appUserLiveData = new MutableLiveData<>();
    public ObservableField<Boolean> visibleNavigation = new ObservableField<>(true);

    public void init(int idUser) {
        getUser(idUser);
    }

    public void setSelectedId(int itemId) {
        selectedId.set(itemId);
    }

    public void setNavControllerField(NavController navController) {
        navControllerField.set(navController);
    }

    public void setVisibleNavigation(boolean isVisible) {
        visibleNavigation.set(isVisible);
    }


    public void getUser(int idUser) {
        APINetwork.GetUserById(idUser, appUserLiveData);
    }

}
