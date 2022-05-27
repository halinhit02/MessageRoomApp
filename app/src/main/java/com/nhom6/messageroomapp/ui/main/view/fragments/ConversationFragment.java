package com.nhom6.messageroomapp.ui.main.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.conversation.Conversation;
import com.nhom6.messageroomapp.databinding.FragmentConversationBinding;
import com.nhom6.messageroomapp.ui.base.BaseMainFragment;
import com.nhom6.messageroomapp.ui.base.BaseViewAdapter;
import com.nhom6.messageroomapp.ui.main.adapter.SingleTypeAdapter;
import com.nhom6.messageroomapp.ui.main.viewmodel.ApplicationViewModel;
import com.nhom6.messageroomapp.ui.main.viewmodel.ConversationViewModel;
import com.nhom6.messageroomapp.utils.AppUtils;
import com.nhom6.messageroomapp.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class ConversationFragment extends BaseMainFragment<FragmentConversationBinding> implements BaseViewAdapter.Presenter<Conversation> {

    public static final String TAG = "ChatFragment";

    private AppUser mUser;
    public ConversationViewModel viewModel;
    private static ApplicationViewModel appViewModel;
    private final List<Conversation> conversationList = new ArrayList<>();
    private SingleTypeAdapter<Conversation> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_conversation;
    }

    @Override
    public void initData() {
        mUser = AppUtils.getAppUser();
        viewModel = ViewModelProviders.of(this).get(ConversationViewModel.class);
        appViewModel = ViewModelProviders.of(requireActivity()).get(ApplicationViewModel.class);
    }

    @Override
    public void initWidget() {
        getDataBinding().setConversationViewModel(viewModel);
        initAdapter();
        viewModel.init(requireActivity(), mUser);
        getConversationPagingListener();
        appViewModel.appUserLiveData.observe(requireActivity(), appUser -> {
            if (appUser == null) return;
            mUser = appUser;
            viewModel.setAppUser(appUser);
        });
    }

    @Override
    public void deInit() {
    }

    public void onStart() {
        super.onStart();
        viewModel.getConversations();
    }

    private void getConversationPagingListener() {
        viewModel.conversationData.observe(getViewLifecycleOwner(), conversationPagingResponse -> {
            if (conversationPagingResponse == null) return;
            if (conversationPagingResponse.isSucceeded()) {
                List<Conversation> conversationServers = conversationPagingResponse.getResult().getItems();
                viewModel.isNoneField.set(conversationServers.size() < 1);
                adapter.clear();
                conversationList.clear();
                conversationList.addAll(conversationServers);
                adapter.addAll(conversationList);
            } else {
                showMessage(conversationPagingResponse.message);
            }
            Log.d("halinhit123", "load conversation successful");
            viewModel.setIsLoading(false);
        });
    }

    private void initAdapter() {
        adapter = new SingleTypeAdapter<>(getContext(), R.layout.conversation_item_layout);
        adapter.setPresenter(this);
        getDataBinding().setLayoutManager(new LinearLayoutManager(getContext()));
        getDataBinding().setAdapter(adapter);
    }

    @Override
    public void onItemClick(View v, Conversation conversation) {
        appViewModel.setGlobalConversation(conversation);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.INTENT_EXTRA_CONVERSATION, conversation);
        Navigation.findNavController(v).navigate(R.id.action_conversationFragment_to_messageFragment, bundle);
    }
}
