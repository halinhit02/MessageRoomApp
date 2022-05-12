package com.nhom6.messageroomapp.data.model.conversation;

import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.base.BasePagingResponse;

public class ConversationPagingResponse extends BaseAPIResponse<BasePagingResponse<Conversation>> {
    public ConversationPagingResponse(Boolean isSucceeded, String message) {
        super(isSucceeded, message);
    }

    public ConversationPagingResponse(Boolean isSucceeded, String message, int code) {
        super(isSucceeded, message, code);
    }

    public ConversationPagingResponse(Boolean isSucceeded, String message, BasePagingResponse<Conversation> result) {
        super(isSucceeded, message, result);
    }
}
