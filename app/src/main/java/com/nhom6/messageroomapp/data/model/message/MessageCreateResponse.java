package com.nhom6.messageroomapp.data.model.message;

import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;

public class MessageCreateResponse extends BaseAPIResponse<MessageResponse> {
    public MessageCreateResponse(Boolean isSucceeded, String message, MessageResponse result) {
        super(isSucceeded, message, result);
    }

    public MessageCreateResponse(Boolean isSucceeded, String message) {
        super(isSucceeded, message);
    }

    public MessageCreateResponse(Boolean isSucceeded, String message, int code) {
        super(isSucceeded, message, code);
    }
}
