package com.nhom6.messageroomapp.ui.main.view.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.conversation.Conversation;
import com.nhom6.messageroomapp.databinding.FragmentCreateConversationBinding;
import com.nhom6.messageroomapp.ui.base.BaseFragment;
import com.nhom6.messageroomapp.ui.base.BaseViewAdapter;
import com.nhom6.messageroomapp.ui.main.adapter.SingleTypeAdapter;
import com.nhom6.messageroomapp.ui.main.viewmodel.ApplicationViewModel;
import com.nhom6.messageroomapp.ui.main.viewmodel.ConversationCreateViewModel;
import com.nhom6.messageroomapp.utils.Constant;
import com.nhom6.messageroomapp.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class ConversationCreateFragment extends BaseFragment<FragmentCreateConversationBinding> implements BaseViewAdapter.Presenter<AppUser>, View.OnClickListener {
    public static String CREATE_SUCCESSFUL = "CREATE_SUCCESSFUL";
    private ConversationCreateViewModel viewModel;
    private ApplicationViewModel appViewModel;
    private SingleTypeAdapter<AppUser> adapter, addedAdapter;
    private AppUser mUser;
    private List<AppUser> appUserList;
    private List<Integer> selectedUserList;
    private NavController navController;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_create_conversation;
    }

    @Override
    public void initData() {
        navController = NavHostFragment.findNavController(this);
        appViewModel = ViewModelProviders.of(requireActivity()).get(ApplicationViewModel.class);
        viewModel = ViewModelProviders.of(this).get(ConversationCreateViewModel.class);
    }

    @Override
    public void initWidget() {
        getDataBinding().setViewModel(viewModel);
        getDataBinding().setOnClick(this);
        initAdapter();
        getUserSearchPagingListener();
        conversationCreateListener();
        appViewModel.appUserLiveData.observe(this, appUser -> {
            if (appUser == null) return;
            mUser = appUser;
        });
    }

    @Override
    public void deInit() {

    }

    private void initAdapter() {
        selectedUserList = new ArrayList<>();
        adapter = new SingleTypeAdapter<>(getContext(), R.layout.user_search_item_layout);
        addedAdapter = new SingleTypeAdapter<>(getContext(), R.layout.user_item_layout);
        adapter.setPresenter(this);
        addedAdapter.setPresenter(this);
        getDataBinding().setAdapter(adapter);
        getDataBinding().setAddedAdapter(addedAdapter);
    }

    @Override
    public void onClick(View view) {
        navController = Navigation.findNavController(view);
        if (view.getId() == R.id.tv_cancel) {
            navController.popBackStack();
            return;
        }
        if (view.getId() == R.id.btn_search) {
            String keyword = viewModel.getKeyword();
            if (keyword != null && !keyword.isEmpty()) {
                viewModel.searchUser(keyword);
            }
            return;
        }
        if (view.getId() == R.id.tv_create) {
            if (selectedUserList.size() < 1) {
                showMessage("Cần ít nhất 2 người để tạo nhóm.");
                return;
            }
            selectedUserList.add(0, mUser.getId());
            DialogUtils.showLoadingDialog(getContext());
            viewModel.createConversation(selectedUserList);
        }
    }

    public void getUserSearchPagingListener() {
        viewModel.userResponseLiveData.observe(getViewLifecycleOwner()
                , userPagingResponse -> {
                    if (userPagingResponse.isSucceeded && userPagingResponse.getResult() != null) {
                        adapter.clear();
                        appUserList = new ArrayList<>();
                        appUserList.addAll(userPagingResponse.getResult().getItems());
                        for (int i = 0; i < appUserList.size(); i++) {
                            if (appUserList.get(i).getId().equals(mUser.getId())) {
                                appUserList.remove(i);
                            }
                        }
                        adapter.addAll(appUserList);
                        return;
                    }
                    showMessage(userPagingResponse.getMessage());
                });
    }

    public void conversationCreateListener() {
        viewModel.conversationResponseLiveDate.observe(getViewLifecycleOwner(), conversationResponse -> {
            DialogUtils.dismissDialog();
            if (!conversationResponse.isSucceeded) {
                showMessage(conversationResponse.getMessage());
                return;
            }
            Conversation newConversation = conversationResponse.getResult();
            appViewModel.setGlobalConversation(newConversation);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.INTENT_EXTRA_CONVERSATION, newConversation);
            navController.navigate(R.id.action_conversationCreateFragment_to_messageFragment, bundle);
        });
    }

    @Override
    public void onItemClick(View view, AppUser user) {
        if (view.getId() == R.id.btn_remove) {
            for (int i = 0; i < selectedUserList.size(); i++) {
                if (selectedUserList.get(i).equals(user.getId())) {
                    selectedUserList.remove(selectedUserList.get(i));
                    addedAdapter.remove(i);
                    break;
                }
            }
            return;
        }
        if (view.getId() == R.id.btn_add) {
            for (int i = 0; i < selectedUserList.size(); i++) {
                if (selectedUserList.get(i).equals(user.getId())) {
                    return;
                }
            }
            selectedUserList.add(user.getId());
            addedAdapter.add(user);
            return;
        }
        showMessage(user.getName());
    }
}
