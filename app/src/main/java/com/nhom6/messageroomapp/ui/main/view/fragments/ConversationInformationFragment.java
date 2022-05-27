package com.nhom6.messageroomapp.ui.main.view.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.conversation.Conversation;
import com.nhom6.messageroomapp.data.model.participant.Participant;
import com.nhom6.messageroomapp.data.model.participant.ParticipantDeleteRequest;
import com.nhom6.messageroomapp.databinding.FragmentConversationInformationBinding;
import com.nhom6.messageroomapp.ui.base.BaseFragment;
import com.nhom6.messageroomapp.ui.main.viewmodel.ApplicationViewModel;
import com.nhom6.messageroomapp.ui.main.viewmodel.ConversationInfoViewModel;
import com.nhom6.messageroomapp.utils.AppUtils;
import com.nhom6.messageroomapp.utils.Constant;
import com.nhom6.messageroomapp.utils.DialogUtils;

public class ConversationInformationFragment extends BaseFragment<FragmentConversationInformationBinding> {

    private ConversationInfoViewModel viewModel;
    private ApplicationViewModel appViewModel;
    private Conversation conversation;
    private AppUser mUser;
    private NavController navController;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_conversation_information;
    }

    @Override
    public void initData() {
        navController = NavHostFragment.findNavController(this);
        mUser = AppUtils.getAppUser();
        if (getArguments() != null && mUser != null) {
            conversation = getArguments().getParcelable(Constant.INTENT_EXTRA_CONVERSATION);
        } else {
            navController.popBackStack();
        }
        appViewModel = ViewModelProviders.of(requireActivity()).get(ApplicationViewModel.class);
        viewModel = ViewModelProviders.of(this).get(ConversationInfoViewModel.class);
    }

    @Override
    public void initWidget() {
        getDataBinding().setViewModel(viewModel);
        viewModel.init();
        viewModel.setConversation(conversation);
        getDataBinding().setOnClicked(this::onClick);
        getDeleteParticipantResponse();

        appViewModel.appUserLiveData.observe(this, appUser -> {
            if (appUser == null) return;
            mUser = appUser;
        });

        appViewModel.participantGetLiveData.observe(getViewLifecycleOwner(), participants -> {
            for (Participant participant : participants) {
                if (participant.getUser().getId().equals(mUser.getId())) {
                    viewModel.setCurrentParticipant(participant);
                    return;
                }
            }
        });

        appViewModel.conversationGetLiveData.observe(this, conversation -> {
            if (conversation == null) return;
            this.conversation = conversation;
            viewModel.setConversation(conversation);
        });
    }

    @Override
    public void deInit() {
    }

    public void onClick(View view) {
        navController = Navigation.findNavController(view);
        @IdRes
        int btnId = view.getId();
        if (btnId == R.id.btn_back) {
            navController.popBackStack();
        } else if (btnId == R.id.tv_participants) {
            //
        } else if (btnId == R.id.btn_edit) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.INTENT_EXTRA_CONVERSATION, conversation);
            navController.navigate(R.id.action_conversationInformationFragment_to_conversationEditFragment, bundle);
        } else if (btnId == R.id.tv_leave) {
            DialogUtils.showLoadingDialog(requireContext());
            ParticipantDeleteRequest request = new ParticipantDeleteRequest(conversation.getId(), mUser.getId(), mUser.getId());
            viewModel.deleteParticipant(request);
        }
    }

    private void getDeleteParticipantResponse() {
        viewModel.deleteParticipantLiveData.observe(this, response -> {
            DialogUtils.dismissDialog();
            if (!response.isSucceeded) {
                showMessage(response.getMessage());
                return;
            }
            navController.navigate(ConversationInformationFragmentDirections.actionConversationInformationFragmentToNavigationConversation());
        });
    }
}
