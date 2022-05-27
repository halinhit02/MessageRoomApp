package com.nhom6.messageroomapp.ui.main.view.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.nhom6.messageroomapp.MRApplication;
import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.conversation.Conversation;
import com.nhom6.messageroomapp.databinding.FragmentEditConversationBinding;
import com.nhom6.messageroomapp.ui.base.BaseFragment;
import com.nhom6.messageroomapp.ui.main.viewmodel.ApplicationViewModel;
import com.nhom6.messageroomapp.ui.main.viewmodel.ConversationEditViewModel;
import com.nhom6.messageroomapp.utils.AppUtils;
import com.nhom6.messageroomapp.utils.Constant;
import com.nhom6.messageroomapp.utils.IntentUtils;

public class ConversationEditFragment extends BaseFragment<FragmentEditConversationBinding> {

    private ConversationEditViewModel viewModel;
    private ApplicationViewModel appViewModel;
    private Conversation conversation;
    private AppUser mUser;
    private NavController navController;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_edit_conversation;
    }

    @Override
    public void initData() {
        navController = NavHostFragment.findNavController(this);
        if (getArguments() != null) {
            conversation = getArguments().getParcelable(Constant.INTENT_EXTRA_CONVERSATION);
        } else {
            navController.popBackStack();
        }
    }

    @Override
    public void initWidget() {
        viewModel = ViewModelProviders.of(this).get(ConversationEditViewModel.class);
        appViewModel = ViewModelProviders.of(requireActivity()).get(ApplicationViewModel.class);
        getDataBinding().setViewModel(viewModel);
        getDataBinding().setOnClicked(this::onClick);
        viewModel.setContext(requireActivity());
        viewModel.setConversation(conversation);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri resultUri = result.getData().getData();
                if (resultUri == null) return;
                viewModel.setAvatar(resultUri);
            }
        });

        appViewModel.conversationGetLiveData.observe(this, conversation -> {
            if (conversation == null) return;
            this.conversation = conversation;
            viewModel.setConversation(conversation);
        });

        appViewModel.appUserLiveData.observe(this, appUser -> {
            if (appUser == null) return;
            mUser = appUser;
            viewModel.setAppUser(appUser);
        });

        viewModel.saveConversationResponse.observe(this, newConversation -> {
            appViewModel.setGlobalConversation(newConversation);
            MRApplication.showMessage("Thông tin đã được cập nhật.");
            navController.popBackStack();
        });

    }

    @Override
    public void deInit() {

    }

    public void onClick(View view) {
        navController = Navigation.findNavController(view);
        int btnId = view.getId();
        if (btnId == R.id.btn_back) {
            navController.popBackStack();
        } else if (btnId == R.id.ly_upload) {
            if (AppUtils.checkPermission(requireActivity())) {
                IntentUtils.galleryIntent(requireContext(), activityResultLauncher);
            }
        } else if (btnId == R.id.btn_done) {
            viewModel.saveConversation();
        }
    }
}
