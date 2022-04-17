package com.nhom6.messageroomapp.ui.main.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.nhom6.messageroomapp.MRApplication;
import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.auth.AuthResult;
import com.nhom6.messageroomapp.data.model.auth.LoginRequest;
import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.repository.APINetwork;
import com.nhom6.messageroomapp.utils.DialogUtils;

public class AuthenticateViewModel extends ViewModel implements View.OnClickListener {

    public LoginRequest loginRequest = new LoginRequest("", "");
    public MutableLiveData<BaseAPIResponse<AuthResult>> authResponseCallback = new MutableLiveData<>();

    public void init() {
        authResponseCallback = new MutableLiveData<>();
    }

    @Override
    public void onClick(View view) {
        NavController controller = Navigation.findNavController(view);
        if (view.getId() == R.id.btn_register) {
            controller.navigate(R.id.action_authenticateFragment_to_registerFragment);
        } else if (view.getId() == R.id.btn_auth) {
            DialogUtils.showLoadingDialog(view.getContext());
            BaseAPIResponse<AuthResult> authResponse;
            if (!loginRequest.isUerNameValid()) {
                authResponse = new BaseAPIResponse<>(false, MRApplication.the().getString(R.string.username_invalid));
                authResponseCallback.setValue(authResponse);
                return;
            }
            if (!loginRequest.isPasswordValid()) {
                authResponse = new BaseAPIResponse<>(false, MRApplication.the().getString(R.string.password_invalid));
                authResponseCallback.setValue(authResponse);
                return;
            }
            APINetwork.login(loginRequest, authResponseCallback);
        }
    }
}

