package com.nhom6.messageroomapp.ui.main.view.fragments;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.participant.Participant;
import com.nhom6.messageroomapp.databinding.FragmentParticipantBinding;
import com.nhom6.messageroomapp.ui.base.BaseFragment;
import com.nhom6.messageroomapp.ui.base.BaseViewAdapter;
import com.nhom6.messageroomapp.ui.main.adapter.SingleTypeAdapter;
import com.nhom6.messageroomapp.ui.main.viewmodel.ApplicationViewModel;
import com.nhom6.messageroomapp.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class ParticipantFragment extends BaseFragment<FragmentParticipantBinding> implements View.OnClickListener, BaseViewAdapter.Presenter<Participant> {

    private ApplicationViewModel appViewModel;
    private List<Participant> participantList;
    private AppUser mUser;
    private SingleTypeAdapter<Participant> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_participant;
    }

    @Override
    public void initData() {
        appViewModel = ViewModelProviders.of(requireActivity()).get(ApplicationViewModel.class);
        mUser = AppUtils.getAppUser();
        adapter = new SingleTypeAdapter<>(requireContext(), R.layout.participant_item_layout);
    }

    @Override
    public void initWidget() {
        getDataBinding().setOnClicked(this);
        getDataBinding().setLayoutManager(new LinearLayoutManager(requireContext()));
        getDataBinding().setAdapter(adapter);
        adapter.setPresenter(this);
        appViewModel.participantGetLiveData.observe(this, participants -> {
            if (participants == null || participants.size() < 1) return;
            participantList = new ArrayList<>();
            adapter.clear();
            participantList.addAll(participants);
            getDataBinding().setCurrentParticipant(getCurrentParticipant());
            adapter.addAll(participantList);
        });
        appViewModel.appUserLiveData.observe(this, appUser -> {
            if (appUser == null) return;
            mUser = appUser;
        });
    }

    @Override
    public void deInit() {

    }

    @Override
    public void onClick(View view) {
        NavController navController = Navigation.findNavController(view);
        @IdRes int viewId = view.getId();
        if (viewId == R.id.iv_back) {
            navController.popBackStack();
            return;
        }
        if (viewId == R.id.iv_add) {
            navController.navigate(R.id.action_participantFragment_to_participantAddFragment);
        }
    }

    @Override
    public void onItemClick(View v, Participant participant) {
        BottomSheetMenuParticipant menuParticipant = BottomSheetMenuParticipant.newInstance(getCurrentParticipant(), participant);
        menuParticipant.show(getParentFragmentManager(), BottomSheetMenuParticipant.TAG);
    }

    private Participant getCurrentParticipant() {
        for(Participant par : participantList) {
            if (par.getUser().getId().equals(mUser.getId())) {
                return par;
            }
        }
        return null;
    }
}
