package com.nhom6.messageroomapp.ui.main.view.fragments;

import static com.nhom6.messageroomapp.utils.AppUtils.showMessageResult;
import android.view.View;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.conversation.Conversation;
import com.nhom6.messageroomapp.data.model.participant.Participant;
import com.nhom6.messageroomapp.data.model.participant.ParticipantAddRequest;
import com.nhom6.messageroomapp.databinding.FragmentAddParticipantBinding;
import com.nhom6.messageroomapp.ui.base.BaseFragment;
import com.nhom6.messageroomapp.ui.base.BaseViewAdapter;
import com.nhom6.messageroomapp.ui.main.adapter.SingleTypeAdapter;
import com.nhom6.messageroomapp.ui.main.viewmodel.ApplicationViewModel;
import com.nhom6.messageroomapp.ui.main.viewmodel.ParticipantAddViewModel;
import com.nhom6.messageroomapp.utils.AppUtils;
import com.nhom6.messageroomapp.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class ParticipantAddFragment extends BaseFragment<FragmentAddParticipantBinding> implements View.OnClickListener, BaseViewAdapter.Presenter<AppUser> {

    private SingleTypeAdapter<AppUser> adapter;
    private ApplicationViewModel appViewModel;
    private ParticipantAddViewModel viewModel;
    private Conversation conversation;
    private AppUser mUser;
    private List<Participant> participantList;
    private List<AppUser> appUserList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_participant;
    }

    @Override
    public void initData() {
        mUser = AppUtils.getAppUser();
        participantList = new ArrayList<>();
        appViewModel = ViewModelProviders.of(requireActivity()).get(ApplicationViewModel.class);
        viewModel = ViewModelProviders.of(this).get(ParticipantAddViewModel.class);
    }

    @Override
    public void initWidget() {
        getDataBinding().setViewModel(viewModel);
        getDataBinding().setOnClicked(this);
        appViewModel.appUserLiveData.observe(getViewLifecycleOwner(), appUser -> {
            if (appUser == null) return;
            mUser = appUser;
        });
        appViewModel.conversationGetLiveData.observe(getViewLifecycleOwner(), conversation -> {
            if (conversation != null) {
                this.conversation = conversation;
            }
        });
        appViewModel.participantGetLiveData.observe(this, participants -> {
            if (participants == null || participants.size() < 1) return;
            participantList = participants;
        });
        initAdapter();
        getUserSearchPagingListener();
        viewModel.addParticipantLiveData.observe(getViewLifecycleOwner(), response -> {
            if (response == null) return;
            if (response.isSucceeded() && response.result != null) {
                if (!checkParticipant(response.result)) {
                    participantList.add(response.result);
                    showMessageResult("Đã thêm thành viên mới");
                    appViewModel.setParticipants(participantList);
                } else {
                    showMessageResult("Thành viên đã tồn tại");
                }
                return;
            }
            showMessageResult(response.message);
        });
    }

    private void initAdapter() {
        adapter = new SingleTypeAdapter<>(getContext(), R.layout.user_search_item_layout);
        adapter.setPresenter(this);
        getDataBinding().setLayoutManager(new LinearLayoutManager(requireContext()));
        getDataBinding().setAdapter(adapter);
    }

    public void getUserSearchPagingListener() {
        viewModel.userResponseLiveData.observe(getViewLifecycleOwner()
                , userPagingResponse -> {
                    if (userPagingResponse.isSucceeded && userPagingResponse.getResult() != null) {
                        adapter.clear();
                        appUserList = new ArrayList<>();
                        appUserList.addAll(userPagingResponse.getResult().getItems());
                        adapter.addAll(appUserList);
                        return;
                    }
                    showMessage(userPagingResponse.getMessage());
                });
    }

    private boolean checkParticipant(Participant participant) {
        if (participantList == null) return false;
        for (Participant par : participantList) {
            if (par.getUser().getId().equals(participant.getUser().getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void deInit() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            Navigation.findNavController(view).popBackStack();
            return;
        }
        if (view.getId() == R.id.btn_search) {
            String keyword = viewModel.getKeyword();
            if (keyword != null && !keyword.isEmpty()) {
                viewModel.searchUser(keyword);
            }
        }
    }

    @Override
    public void onItemClick(View v, AppUser data) {
        DialogUtils.showLoadingDialog(requireContext());
        ParticipantAddRequest request = new ParticipantAddRequest(conversation.getId(), mUser.getId(), data.getId());
        viewModel.addParticipant(request);
    }
}
