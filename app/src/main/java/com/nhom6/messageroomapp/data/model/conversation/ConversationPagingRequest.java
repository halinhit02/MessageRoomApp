package com.nhom6.messageroomapp.data.model.conversation;

import com.nhom6.messageroomapp.data.model.base.BaseGetPagingRequest;

public class ConversationPagingRequest extends BaseGetPagingRequest {

    public ConversationPagingRequest(Integer pageIndex, Integer pageSize, Integer userId) {
        super(pageIndex, pageSize, userId);
    }
}
