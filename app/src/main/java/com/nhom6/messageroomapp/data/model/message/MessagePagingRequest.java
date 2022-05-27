package com.nhom6.messageroomapp.data.model.message;

import com.nhom6.messageroomapp.data.model.base.BaseGetPagingRequest;

public class MessagePagingRequest extends BaseGetPagingRequest {

    public Integer conversationId;

    public MessagePagingRequest(Integer pageIndex, Integer pageSize, Integer conversationId, Integer userId) {
        super(pageIndex, pageSize, userId);
        this.conversationId = conversationId;
    }
}
