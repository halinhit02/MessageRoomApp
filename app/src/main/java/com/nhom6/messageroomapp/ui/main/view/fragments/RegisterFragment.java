package com.nhom6.messageroomapp.ui.main.view.fragments;

import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProviders;

import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.databinding.FragmentRegisterBinding;
import com.nhom6.messageroomapp.ui.base.BaseFragment;
import com.nhom6.messageroomapp.ui.main.viewmodel.RegisterViewModel;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> {
    public RegisterViewModel viewModel;
    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initWidget() {
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        viewModel.init(requireActivity());
        getDataBinding().setViewModel(viewModel);
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri avatarUri = result.getData().getData();
                        if (avatarUri == null) return;
                        viewModel.setAvatarUri(avatarUri);
                    }
                });
        viewModel.setIntentResultLauncher(resultLauncher);
    }

    @Override
    public void deInit() {
    }
}
