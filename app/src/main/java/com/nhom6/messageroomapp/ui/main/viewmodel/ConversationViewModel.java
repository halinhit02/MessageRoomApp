package com.nhom6.messageroomapp.ui.main.viewmodel;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.work.WorkInfo;

import com.google.gson.Gson;
import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.base.BaseGetPagingRequest;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.conversation.ConversationPagingResponse;
import com.nhom6.messageroomapp.data.workers.WorkerLiveData;
import com.nhom6.messageroomapp.utils.Constant;

public class ConversationViewModel extends ViewModel implements View.OnClickListener {

    public MutableLiveData<ConversationPagingResponse> conversationData;
    public ObservableField<Boolean> isNoneField = new ObservableField<>(false);
    public ObservableField<Boolean> isLoading = new ObservableField<>(true);
    private AppUser mUser;
    private FragmentActivity context;

    public void init(FragmentActivity context, AppUser appUser) {
        this.context = context;
        mUser = appUser;
        conversationData = new MutableLiveData<>();
    }

    public void setAppUser(AppUser mUser) {
        this.mUser = mUser;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    public void getConversations() {
        setIsLoading(true);
        BaseGetPagingRequest request = new BaseGetPagingRequest(1, 20, mUser.getId());
        WorkerLiveData.AllConversationGet(context, request)
                .observe(context, workInfo -> {
                    ConversationPagingResponse response = null;
                    if (workInfo == null || workInfo.getState() == WorkInfo.State.BLOCKED || workInfo.getState() == WorkInfo.State.FAILED) {
                        response = new ConversationPagingResponse(false, "Đã xảy ra lỗi");
                    } else if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        String output = workInfo.getOutputData().getString(Constant.OUTPUT_DATA_TAG);
                        response = new Gson().fromJson(output, ConversationPagingResponse.class);
                    }
                    conversationData.postValue(response);
                });
    }

    @Override
    public void onClick(View view) {
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.conversationCreateFragment);
    }
}

