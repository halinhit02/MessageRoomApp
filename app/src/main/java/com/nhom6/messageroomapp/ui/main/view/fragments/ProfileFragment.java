package com.nhom6.messageroomapp.ui.main.view.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.databinding.FragmentProfileBinding;
import com.nhom6.messageroomapp.ui.base.BaseFragment;
import com.nhom6.messageroomapp.ui.main.viewmodel.ApplicationViewModel;
import com.nhom6.messageroomapp.ui.main.viewmodel.ProfileViewModel;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {

    private ProfileViewModel viewModel;
    private ApplicationViewModel appViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void initData() {
        if (getArguments() == null) {
            Navigation.findNavController(requireActivity(), R.id.btn_back).popBackStack();
        }
    }
    @Override
    public void initWidget() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        appViewModel = ViewModelProviders.of(requireActivity()).get(ApplicationViewModel.class);
        getDataBinding().setProfileVM(viewModel);
        appViewModel.appUserLiveData.observe(this, appUser -> {
            if (appUser == null) return;
            viewModel.init(requireActivity(), appUser);
        });
        ActivityResultLauncher<Intent> intentResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri resultUri = result.getData().getData();
                        if (resultUri == null) return;
                        viewModel.setNewAvatarUri(resultUri);
                    }
                });
        viewModel.setResultLauncher(intentResultLauncher);
    }

    @Override
    public void deInit() {
    }
}

