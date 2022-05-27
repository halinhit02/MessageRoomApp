package com.nhom6.messageroomapp.ui.main.view.fragments;

import static android.app.Activity.RESULT_OK;

import static com.nhom6.messageroomapp.MRApplication.showMessage;
import static com.nhom6.messageroomapp.utils.AppUtils.buildToken;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nhom6.messageroomapp.BR;
import com.nhom6.messageroomapp.MRApplication;
import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.base.BasePagingResponse;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.conversation.Conversation;
import com.nhom6.messageroomapp.data.model.message.MessageClient;
import com.nhom6.messageroomapp.data.model.message.MessageGetRequest;
import com.nhom6.messageroomapp.data.model.message.MessageResponse;
import com.nhom6.messageroomapp.data.model.participant.Participant;
import com.nhom6.messageroomapp.databinding.FragmentMessageBinding;
import com.nhom6.messageroomapp.ui.base.BaseFragment;
import com.nhom6.messageroomapp.ui.base.BaseViewAdapter;
import com.nhom6.messageroomapp.ui.main.adapter.MultiTypeAdapter;
import com.nhom6.messageroomapp.ui.main.presenter.MyResultCallback;
import com.nhom6.messageroomapp.ui.main.viewmodel.ApplicationViewModel;
import com.nhom6.messageroomapp.ui.main.viewmodel.MessageViewModel;
import com.nhom6.messageroomapp.utils.AppUtils;
import com.nhom6.messageroomapp.utils.Constant;
import com.nhom6.messageroomapp.utils.DateTimeUtils;
import com.nhom6.messageroomapp.utils.IntentUtils;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmChannel;
import io.agora.rtm.RtmChannelAttribute;
import io.agora.rtm.RtmChannelListener;
import io.agora.rtm.RtmChannelMember;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmFileMessage;
import io.agora.rtm.RtmImageMessage;
import io.agora.rtm.RtmMediaOperationProgress;
import io.agora.rtm.RtmMessage;
import io.agora.rtm.RtmStatusCode;

public class MessageFragment extends BaseFragment<FragmentMessageBinding> implements View.OnClickListener, BaseViewAdapter.Presenter<MessageClient> {
    public static final String TAG = "MessageFragment";

    private MessageViewModel viewModel;
    private ApplicationViewModel appViewModel;

    private AppUser mUser;
    private Conversation conversation;
    private List<MessageClient> messageClientList;
    private List<MessageResponse> messageResponseList;
    private List<Participant> participantList;
    private MultiTypeAdapter<MessageClient> messageAdapter;
    private LinearLayoutManager layoutManager;
    private RtmClient mRtmClient;
    private RtmChannel mRtmChannel;
    private MessageClient latestMessageClient;
    private int currentPosition;
    private ActivityResultLauncher<Intent> intentResultLauncher;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initData() {
        NavController navController = NavHostFragment.findNavController(this);
        mUser = AppUtils.getAppUser();
        if (getArguments() != null && mUser != null) {
            conversation = getArguments().getParcelable(Constant.INTENT_EXTRA_CONVERSATION);
            mRtmClient = MRApplication.the().getChatManager().getRtmClient();
        } else {
            navController.popBackStack();
        }
        viewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        appViewModel = ViewModelProviders.of(requireActivity()).get(ApplicationViewModel.class);
    }

    @Override
    public void initWidget() {
        getDataBinding().setViewModel(viewModel);
        getDataBinding().setVariable(BR.onClick, this);
        viewModel.setClientListener(new MyRTMClientListener());
        viewModel.init(requireActivity(), getViewLifecycleOwner(), conversation, mUser);
        mRtmClient = viewModel.getRtmClient();
        // login RtmChannel
        String mUserId = String.valueOf(mUser.getId());
        mRtmClient.login(buildToken(mUserId), mUserId, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void unused) {
                viewModel.createAndJoinChannel(conversation.getChannelId(), new MyChannelListener(), new MyResultCallback<String>() {
                    @Override
                    public void onSuccess(String value) {
                        //runOnUiThread(() -> showMessage("join channel success"));
                    }

                    @Override
                    public void onFailed(String message, int errorRes) {
                        showMessage(errorRes);
                        onBackFragment();
                    }
                });
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                if (errorInfo.getErrorCode() != 8) {
                    Log.i("MainActivity", "login failed: " + errorInfo.getErrorCode());
                    showMessage(getString(R.string.login_failed));
                    onBackFragment();
                }
            }
        });
        // create observe Live Data
        initAdapter();
        appViewModel.appUserLiveData.observe(this, appUser -> {
            if (appUser == null) return;
            mUser = appUser;
            viewModel.setAppUser(mUser);
        });
        appViewModel.conversationGetLiveData.observe(this, conversation -> {
            if (conversation == null) return;
            this.conversation = conversation;
            viewModel.setConversation(conversation);
        });
        viewModel.participantLiveData.observe(this, participantResponse -> {
            if (participantResponse == null) return;
            participantList = new ArrayList<>();
            if (participantResponse.isSucceeded && participantResponse.getResult() != null) {
                participantList.addAll(participantResponse.getResult().getItems());
                appViewModel.setParticipants(participantList);
            } else {
                showMessage(participantResponse.getMessage());
                onBackFragment();
            }
            viewModel.getAllMessages();
        });
        viewModel.messagePagingLiveData.observe(this, messagePagingResponse -> {
            if (messagePagingResponse == null) return;
            messageResponseList = new ArrayList<>();
            messageClientList = new ArrayList<>();
            messageAdapter.clear();
            latestMessageClient = null;
            BasePagingResponse<MessageResponse> response = messagePagingResponse.getResult();
            if (response == null) return;
            if (response.getItems() != null) {
                messageResponseList = response.getItems();
                currentPosition = messageResponseList.size();
                for (int i = 0; i < messageResponseList.size(); i++) {
                    MessageResponse messageResponse = messageResponseList.get(i);
                    boolean isMyself = messageResponse.getSenderId().equals(mUser.getId());
                    String dateTime = DateTimeUtils.convertDateTime(messageResponse.getCreatedAt(), Constant.ServerPattern, Constant.DateTimePattern);
                    MessageClient messageClient = new MessageClient(messageResponse, dateTime);
                    if (participantList.size() > 0) {
                        Participant participant = getParticipant(messageResponse.getSenderId());
                        if (participant == null) {
                            participant = new Participant(conversation.getId(), new AppUser(messageResponse.getSenderId()), "Người dùng");
                        }
                        messageClient.setParticipant(participant);
                    }
                    addMessageRecycler(messageClient, isMyself ? 1 : 0);
                }
                viewModel.setIsLoading(false);
            }
        });
        viewModel.messageCreateLiveData.observe(this, messageResponse -> {
            if (messageResponse == null) return;
            viewModel.sendMessage(messageResponse.getId(), new SendMessageResultCallBack());
            boolean isMyself = messageResponse.getSenderId().equals(mUser.getId());
            String dateTime = DateTimeUtils.convertDateTime(messageResponse.getCreatedAt(), Constant.ServerPattern, Constant.DateTimePattern);
            MessageClient messageClient = new MessageClient(messageResponse, dateTime);
            if (participantList.size() > 0) {
                Participant participant = getParticipant(messageResponse.getSenderId());
                if (participant != null) {
                    messageClient.setParticipant(participant);
                }
            }
            addMessageRecycler(messageClient, isMyself ? 1 : 0);
        });

        viewModel.messageGetLiveData.observe(this, messageResponse -> {
            if (messageResponse == null) return;
            boolean isMyself = messageResponse.getSenderId().equals(mUser.getId());
            String dateTime = DateTimeUtils.convertDateTime(messageResponse.getCreatedAt(), Constant.ServerPattern, Constant.DateTimePattern);
            MessageClient messageClient = new MessageClient(messageResponse, dateTime);
            if (participantList.size() > 0) {
                Participant participant = getParticipant(messageResponse.getSenderId());
                if (participant != null) {
                    messageClient.setParticipant(participant);
                }
            }
            addMessageRecycler(messageClient, isMyself ? 1 : 0);
        });

        intentResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        CropImage.ActivityResult resultCrop = CropImage.getActivityResult(result.getData());
                        if (resultCrop != null) {
                            Uri resultUri = resultCrop.getUri();
                            viewModel.sendImage(resultUri);
                            return;
                        }
                        //showMessage(result.getData().getData().getPath());
                        viewModel.sendFile(result.getData().getData());
                    }
                });
    }

    private void initAdapter() {
        messageAdapter = new MultiTypeAdapter<>(requireContext());
        messageAdapter.addItemLayoutMap(0, R.layout.message_left_item_layout);
        messageAdapter.addItemLayoutMap(1, R.layout.message_right_item_layout);
        messageAdapter.addItemLayoutMap(2, R.layout.message_horizontal_item_layout);
        messageAdapter.setPresenter(this);
        layoutManager = new LinearLayoutManager(getContext());
        getDataBinding().setLayoutManager(layoutManager);
        getDataBinding().setAdapter(messageAdapter);
    }

    private Participant getParticipant(int participantId) {
        for (Participant par : participantList) {
            if (par.getUser().getId().equals(participantId)) {
                return par;
            }
        }
        return null;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addMessageRecycler(MessageClient messageClient, int viewType) {
        String dateMessage = DateTimeUtils.convertDateTime(messageClient.getDate(), Constant.DateTimePattern, Constant.DatePattern);
        if (latestMessageClient == null || !DateTimeUtils.convertDateTime(latestMessageClient.getDate(), Constant.DateTimePattern, Constant.DatePattern).equals(dateMessage)) {
            RtmMessage rtmMessage = mRtmClient.createMessage();
            rtmMessage.setText(dateMessage);
            MessageClient dateMessageClient = new MessageClient(rtmMessage, "", true, true);
            messageClientList.add(dateMessageClient);
            messageAdapter.add(dateMessageClient, 2);
            currentPosition++;
        }
        if (latestMessageClient != null && latestMessageClient.getParticipant().getUser().getId().equals(messageClient.getParticipant().getUser().getId())) {
            messageClientList.get(messageClientList.size() - 1).setInfoDisplayed(false);
            messageAdapter.notifyDataSetChanged();
        }
        messageClientList.add(messageClient);
        messageAdapter.add(messageClient, viewType);
        layoutManager.scrollToPosition(messageClientList.size() - 1);
        latestMessageClient = messageClient;
    }

    @Override
    public void deInit() {
        appViewModel.setGlobalConversation(null);
        appViewModel.setParticipants(null);
        mRtmClient.logout(null);
        leaveAndReleaseChannel();
    }

    private void leaveAndReleaseChannel() {
        if (mRtmChannel != null) {
            mRtmChannel.leave(null);
            mRtmChannel.release();
            mRtmChannel = null;
        }
    }

    @Override
    public void onClick(View view) {
        NavController controller = Navigation.findNavController(view);
        int btnId = view.getId();
        if (btnId == R.id.btn_back) {
            controller.navigate(R.id.action_messageFragment_to_navigation_conversation);
        } else if (btnId == R.id.iv_avatar || btnId == R.id.ly_title) {
            viewModel.messageCreateLiveData.removeObservers(this);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.INTENT_EXTRA_CONVERSATION, conversation);
            controller.navigate(R.id.action_messageFragment_to_conversationInformationFragment, bundle);
        } else if (btnId == R.id.btn_send) {
            viewModel.sendText();
        } else if (btnId == R.id.iv_photo) {
            viewModel.isMenuShowed.set(!viewModel.isMenuShowed.get());
        } else if (btnId == R.id.ly_image || btnId == R.id.ly_camera) {
            if (AppUtils.checkPermission(requireActivity())) {
                intentResultLauncher.launch(CropImage.activity().getIntent(requireContext()));
            }
        } else if (btnId == R.id.ly_file) {
            if (AppUtils.checkPermission(requireActivity())) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/*");
                intentResultLauncher.launch(intent);
            }
        }
    }

    @Override
    public void onItemClick(View v, MessageClient data) {
        if (data.getServerMessage().getMessageType() == 1) {
            Bundle bundle = new Bundle();
            bundle.putString(PhotoViewerFragment.TAG, data.getServerMessage().getAttachments().get(0).getFileUrl());
            Navigation.findNavController(v).navigate(R.id.action_messageFragment_to_photoViewerFragment, bundle);
        } else if (data.getServerMessage().getMessageType() == 4) {
            String fileUrl = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + data.getServerMessage().getAttachments().get(0).getFileUrl();
            startActivity(IntentUtils.openFileOnWeb(fileUrl));
        }
    }

    class SendMessageResultCallBack implements MyResultCallback<MessageClient> {
        @Override
        public void onSuccess(MessageClient messageClient) {
            requireActivity().runOnUiThread(() -> {
                //addMessageRecycler(messageClient, 1);
                //showMessage(R.string.str_send);
            });
        }

        @Override
        public void onFailed(String message, int errorRes) {
            requireActivity().runOnUiThread(() -> showMessage(errorRes));
        }
    }

    class MyChannelListener implements RtmChannelListener {

        @Override
        public void onMemberCountUpdated(int i) {
        }

        @Override
        public void onAttributesUpdated(List<RtmChannelAttribute> list) {
        }

        @Override
        public void onMessageReceived(RtmMessage rtmMessage, RtmChannelMember fromMember) {
            Log.i("channel", "onMessageReceived account = " + fromMember.getUserId() + " msg = " + rtmMessage);
            requireActivity().runOnUiThread(() -> {
                MessageGetRequest request = new MessageGetRequest(Integer.parseInt(rtmMessage.getText()), conversation.getId(), mUser.getId());
                viewModel.getMessage(request);
                /*String dateTime = DateTimeUtils.convertDateTimeMillis(rtmMessage.getServerReceivedTs(), Constant.DateTimePattern);
                AppUser user = getParticipant(Integer.parseInt(fromMember.getUserId()));
                if (user != null) {
                    MessageClient messageClient = new MessageClient(rtmMessage, dateTime, true);
                    messageClient.setUser(user);
                    addMessageRecycler(messageClient, 0);
                }*/
            });
        }


        @Override
        public void onImageMessageReceived(RtmImageMessage rtmImageMessage, RtmChannelMember fromMember) {
            Log.i("channel", "onMessageReceived account = " + fromMember.getUserId() + " msg = " + rtmImageMessage);
            requireActivity().runOnUiThread(() -> {
                String dateTime = DateTimeUtils.convertDateTimeMillis(rtmImageMessage.getServerReceivedTs(), Constant.DateTimePattern);
                Participant participant = getParticipant(Integer.parseInt(fromMember.getUserId()));
                if (participant != null) {
                    MessageClient messageClient = new MessageClient(rtmImageMessage, dateTime, true);
                    messageClient.setParticipant(participant);
                    addMessageRecycler(messageClient, 0);
                }
            });
        }

        @Override
        public void onFileMessageReceived(RtmFileMessage rtmFileMessage, RtmChannelMember fromMember) {
            requireActivity().runOnUiThread(() -> {
                String dateTime = DateTimeUtils.convertDateTimeMillis(rtmFileMessage.getServerReceivedTs(), Constant.DateTimePattern);
                Participant participant = getParticipant(Integer.parseInt(fromMember.getUserId()));
                if (participant != null) {
                    MessageClient messageClient = new MessageClient(rtmFileMessage, dateTime, true);
                    messageClient.setParticipant(participant);
                    addMessageRecycler(messageClient, 0);
                }
            });
        }

        @Override
        public void onMemberJoined(RtmChannelMember rtmChannelMember) {
        }

        @Override
        public void onMemberLeft(RtmChannelMember rtmChannelMember) {
        }
    }

    class MyRTMClientListener implements RtmClientListener {
        @Override
        public void onConnectionStateChanged(int state, int reason) {
            Log.d("channel", state + "-" + reason);
            requireActivity().runOnUiThread(() -> {
                switch (state) {
                    case RtmStatusCode.ConnectionState.CONNECTION_STATE_CONNECTED:
                        //showMessage("Network connected");
                        break;
                    case RtmStatusCode.ConnectionState.CONNECTION_STATE_RECONNECTING:
                        showMessage(R.string.reconnecting);
                        break;
                    case RtmStatusCode.ConnectionState.CONNECTION_STATE_ABORTED:
                        showMessage(R.string.account_offline);
                        onBackFragment();
                        break;
                }
            });
        }

        @Override
        public void onMessageReceived(RtmMessage rtmMessage, String peerId) {
        }

        @Override
        public void onImageMessageReceivedFromPeer(RtmImageMessage rtmImageMessage, String peerId) {
        }

        @Override
        public void onFileMessageReceivedFromPeer(RtmFileMessage rtmFileMessage, String s) {

        }

        @Override
        public void onMediaUploadingProgress(RtmMediaOperationProgress rtmMediaOperationProgress, long l) {

        }

        @Override
        public void onMediaDownloadingProgress(RtmMediaOperationProgress rtmMediaOperationProgress, long l) {

        }

        @Override
        public void onTokenExpired() {
            Log.d("channel", "token expired");
        }

        @Override
        public void onPeersOnlineStatusChanged(Map<String, Integer> map) {

        }
    }
}
