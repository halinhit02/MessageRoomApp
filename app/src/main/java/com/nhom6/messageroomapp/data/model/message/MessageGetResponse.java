package com.nhom6.messageroomapp.data.model.message;

import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;

public class MessageGetResponse extends BaseAPIResponse<MessageResponse> {
    public MessageGetResponse(Boolean isSucceeded, String message) {
        super(isSucceeded, message);
    }

    public MessageGetResponse(Boolean isSucceeded, String message, int code) {
        super(isSucceeded, message, code);
    }

    public MessageGetResponse(Boolean isSucceeded, String message, MessageResponse result) {
        super(isSucceeded, message, result);
    }
}
