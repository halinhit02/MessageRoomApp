package com.nhom6.messageroomapp.data.model.message;

import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.base.BasePagingResponse;

public class MessagePagingResponse extends BaseAPIResponse<BasePagingResponse<MessageResponse>> {
    public MessagePagingResponse(Boolean isSucceeded, String message) {
        super(isSucceeded, message);
    }

    public MessagePagingResponse(Boolean isSucceeded, String message, int code) {
        super(isSucceeded, message, code);
    }

    public MessagePagingResponse(Boolean isSucceeded, String message, BasePagingResponse<MessageResponse> result) {
        super(isSucceeded, message, result);
    }
}
