package com.nhom6.messageroomapp.ui.main.view.activities;

import androidx.lifecycle.ViewModelProviders;

import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.repository.APINetwork;
import com.nhom6.messageroomapp.databinding.ActivityAuthBinding;
import com.nhom6.messageroomapp.ui.base.BaseActivity;
import com.nhom6.messageroomapp.ui.main.viewmodel.AuthenticationViewModel;
import com.nhom6.messageroomapp.utils.AppUtils;

public class AuthActivity extends BaseActivity<ActivityAuthBinding> {

    @Override
    public void initData() {
        AppUtils.clearSharedPref();
    }

    @Override
    public void initWidget() {
        AuthenticationViewModel viewModel = ViewModelProviders.of(this).get(AuthenticationViewModel.class);
        getBinding().setAuthenticationVM(viewModel);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_auth;
    }

    @Override
    public void deInit() {
    }
}
