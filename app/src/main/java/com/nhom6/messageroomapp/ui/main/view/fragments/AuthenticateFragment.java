package com.nhom6.messageroomapp.ui.main.view.fragments;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.databinding.FragmentAuthenticateBinding;
import com.nhom6.messageroomapp.ui.base.BaseFragment;
import com.nhom6.messageroomapp.ui.main.viewmodel.AuthenticateViewModel;
import com.nhom6.messageroomapp.utils.AppUtils;
import com.nhom6.messageroomapp.utils.Constant;
import com.nhom6.messageroomapp.utils.DialogUtils;

public class AuthenticateFragment extends BaseFragment<FragmentAuthenticateBinding> {

    private AuthenticateViewModel authVm;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_authenticate;
    }

    @Override
    public void initData() {
        authVm = ViewModelProviders.of(this).get(AuthenticateViewModel.class);
        authVm.init();
        getDataBinding().setAuthVM(authVm);
        setUpAuthenticateListener();
    }

    @Override
    public void initWidget() {

    }

    @Override
    public void deInit() {

    }

    private void setUpAuthenticateListener() {
        NavController controller = Navigation.findNavController(requireActivity(), R.id.authFrameLy);
        authVm.authResponseCallback.observe(this, authResponse -> {
            if (authResponse.isSucceeded()) {
                String token = authResponse.getResult().getToken();
                AppUtils.saveStringSharedRef(Constant.Token_Key, token);
                AppUtils.saveAppUser(authResponse.getResult().getData());
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    controller.navigate(R.id.action_authenticateFragment_to_mainActivity);
                    requireActivity().finish();
                }, 500);
            } else {
                showMessage(authResponse.getMessage());
            }
            if (DialogUtils.alertDialog != null)
                DialogUtils.alertDialog.dismiss();
        });
    }
}
