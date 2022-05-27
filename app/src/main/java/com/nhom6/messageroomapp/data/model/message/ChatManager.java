package com.nhom6.messageroomapp.data.model.message;

import android.content.Context;
import android.util.Log;

import com.nhom6.messageroomapp.utils.Constant;

import java.util.Map;

import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmFileMessage;
import io.agora.rtm.RtmImageMessage;
import io.agora.rtm.RtmMediaOperationProgress;
import io.agora.rtm.RtmMessage;

public class ChatManager {

    private final Context mContext;
    private RtmClient mRtmClient;
    private RtmClientListener rtmClientListener;

    public ChatManager(Context mContext) {
        this.mContext = mContext;
        init();
    }

    private void init() {
        try {
            mRtmClient = RtmClient.createInstance(mContext, Constant.APP_ID, new RtmClientListener() {
                @Override
                public void onConnectionStateChanged(int i, int i1) {
                    if (rtmClientListener != null) {
                        rtmClientListener.onConnectionStateChanged(i, i1);
                    }
                }

                @Override
                public void onMessageReceived(RtmMessage rtmMessage, String s) {
                    if (rtmClientListener != null) {
                        rtmClientListener.onMessageReceived(rtmMessage, s);
                    }
                }

                @Override
                public void onImageMessageReceivedFromPeer(RtmImageMessage rtmImageMessage, String s) {
                    if (rtmClientListener != null) {
                        rtmClientListener.onImageMessageReceivedFromPeer(rtmImageMessage, s);
                    }
                }

                @Override
                public void onFileMessageReceivedFromPeer(RtmFileMessage rtmFileMessage, String s) {
                    if (rtmClientListener != null) {
                        rtmClientListener.onFileMessageReceivedFromPeer(rtmFileMessage, s);
                    }
                }

                @Override
                public void onMediaUploadingProgress(RtmMediaOperationProgress rtmMediaOperationProgress, long l) {
                    if (rtmClientListener != null) {
                        rtmClientListener.onMediaUploadingProgress(rtmMediaOperationProgress, l);
                    }
                }

                @Override
                public void onMediaDownloadingProgress(RtmMediaOperationProgress rtmMediaOperationProgress, long l) {
                    if (rtmClientListener != null) {
                        rtmClientListener.onMediaDownloadingProgress(rtmMediaOperationProgress, l);
                    }
                }

                @Override
                public void onTokenExpired() {
                    if (rtmClientListener != null) {
                        rtmClientListener.onTokenExpired();
                    }
                }

                @Override
                public void onPeersOnlineStatusChanged(Map<String, Integer> map) {
                    if (rtmClientListener != null) {
                        rtmClientListener.onPeersOnlineStatusChanged(map);
                    }
                }
            });
            Log.d("channel", "created rtmclient");
        } catch (Exception e) {
            Log.e("ChatManager", Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtm sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    public RtmClient getRtmClient() {
        return mRtmClient;
    }

    public void setRtmClientListener(RtmClientListener rtmClientListener) {
        this.rtmClientListener = rtmClientListener;
    }
}
