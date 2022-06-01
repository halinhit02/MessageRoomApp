package com.nhom6.messageroomapp.ui.main.view.fragments;

import static com.nhom6.messageroomapp.utils.AppUtils.showMessageResult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.participant.Participant;
import com.nhom6.messageroomapp.data.model.participant.ParticipantDeleteRequest;
import com.nhom6.messageroomapp.data.model.participant.ParticipantUpdateRequest;
import com.nhom6.messageroomapp.databinding.BottomSheetMenuParticipantBinding;
import com.nhom6.messageroomapp.databinding.FragmentDialogInputBinding;
import com.nhom6.messageroomapp.ui.main.viewmodel.ApplicationViewModel;
import com.nhom6.messageroomapp.ui.main.viewmodel.MenuParticipantViewModel;
import com.nhom6.messageroomapp.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetMenuParticipant extends BottomSheetDialogFragment implements View.OnClickListener {
    public static final String TAG = "BottomSheetMenuParticipant";

    private ApplicationViewModel appViewModel;
    private MenuParticipantViewModel viewModel;
    private BottomSheetMenuParticipantBinding binding;
    private AlertDialog inputDialog;
    private Participant mParticipant;
    private Participant participant;
    private String currentNickName;
    private ParticipantUpdateRequest request;
    private List<Participant> participantList;

    public static BottomSheetMenuParticipant newInstance(Participant currentParticipant, Participant participant) {

        Bundle args = new Bundle();
        args.putSerializable(TAG, currentParticipant);
        args.putSerializable("par", participant);
        BottomSheetMenuParticipant fragment = new BottomSheetMenuParticipant();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            this.dismiss();
        }
        mParticipant = (Participant) getArguments().getSerializable(TAG);
        participant = (Participant) getArguments().getSerializable("par");
        currentNickName = participant.getNickName() == null ? participant.getUser().getName() : participant.getNickName();
        appViewModel = ViewModelProviders.of(requireActivity()).get(ApplicationViewModel.class);
        viewModel = ViewModelProviders.of(this).get(MenuParticipantViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_menu_participant, container, false);
        binding.setOnClicked(this);
        binding.setViewModel(viewModel);
        binding.setParticipant(participant);
        viewModel.setParticipant(participant);
        binding.setMParticipant(mParticipant);
        viewModel.setNickName(currentNickName);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appViewModel.participantGetLiveData.observe(this, participants -> {
            if (participants == null || participants.size() < 1) return;
            participantList = new ArrayList<>();
            participantList.addAll(participants);
            for (Participant par : participantList) {
                if (par.getUser().getId().equals(mParticipant.getUser().getId())) {
                    mParticipant = par;
                    binding.setMParticipant(par);
                    return;
                }
            }
        });
        viewModel.updateLiveData.observe(getViewLifecycleOwner(), response -> {
            if (response == null) return;
            if (!response.isSucceeded) {
                showMessageResult(response.message);
                return;
            }
            showMessageResult("Thông tin đã được cập nhật");
            if (!request.getNickName().isEmpty()) {
                participant.setNickName(request.getNickName());
                currentNickName = request.getNickName();
            }
            participant.setAdmin(request.isAdmin());
            binding.setParticipant(participant);
            viewModel.setParticipant(participant);
            updateParticipantList(participant);
        });
        viewModel.deleteParticipantLiveData.observe(getViewLifecycleOwner(), response -> {
            if (response == null) return;
            if (!response.isSucceeded()) {
                showMessageResult(response.message);
                return;
            }
            if (participant.getUser().getId().equals(mParticipant.getUser().getId())) {
                showMessageResult("Bạn đã rời cuộc trò chuyện");
                this.dismiss();
                NavHostFragment.findNavController(this).navigate(R.id.action_participantFragment_to_navigation_conversation2);
                return;
            }
            showMessageResult("Đã xóa " + participant.getUser().getName() + " khỏi cuộc trò chuyện");
            removeParticipant(participant);
            this.dismiss();
        });
    }

    @Override
    public void onClick(View view) {
        @IdRes int viewId = view.getId();
        if (viewId == R.id.tv_add_nickName) {
            showInputDialog();
        } else if (viewId == R.id.btn_update) {
            if (inputDialog != null) {
                inputDialog.dismiss();
            }
            String newNickName = viewModel.newNickNameField.get();
            if (currentNickName.equals(newNickName)) {
                showMessageResult("Thông tin đã được cập nhật!");
                return;
            }
            DialogUtils.showLoadingDialog(requireContext());
            request = new ParticipantUpdateRequest(participant.getConversationId(), mParticipant.getUser().getId(), participant.getUser().getId(), newNickName, participant.isAdmin());
            viewModel.updateParticipant(request);
        } else if (viewId == R.id.tv_add_admin) {
            DialogUtils.showLoadingDialog(requireContext());
            request = new ParticipantUpdateRequest(participant.getConversationId(), mParticipant.getUser().getId(), participant.getUser().getId(), !participant.isAdmin());
            viewModel.updateParticipant(request);
        } else if (viewId == R.id.tv_remove) {
            DialogUtils.showLoadingDialog(requireContext());
            ParticipantDeleteRequest request = new ParticipantDeleteRequest(participant.getConversationId(), mParticipant.getUser().getId(), participant.getUser().getId());
            viewModel.deleteParticipant(request);
        }
    }

    private void showInputDialog() {
        FragmentDialogInputBinding binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_dialog_input, null, false);
        binding.setOnClicked(this);
        binding.setViewModel(viewModel);
        viewModel.setNickName(currentNickName);
        inputDialog = new AlertDialog.Builder(requireContext())
                .setView(binding.getRoot())
                .setCancelable(true)
                .create();
        inputDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        inputDialog.show();
    }

    private void updateParticipantList(Participant parUpdated) {
        for (int i = 0; i < participantList.size(); i++) {
            if (participantList.get(i).getUser().getId().equals(parUpdated.getUser().getId())) {
                participantList.set(i, parUpdated);
                appViewModel.setParticipants(participantList);
                return;
            }
        }
    }

    private void removeParticipant(Participant participant) {
        for (int i = 0; i < participantList.size(); i++) {
            if (participantList.get(i).getUser().getId().equals(participant.getUser().getId())) {
                participantList.remove(i);
                appViewModel.setParticipants(participantList);
                return;
            }
        }
    }
}
