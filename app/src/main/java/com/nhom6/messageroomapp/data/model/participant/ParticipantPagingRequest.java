package com.nhom6.messageroomapp.data.model.participant;

import com.nhom6.messageroomapp.data.model.base.BaseGetPagingRequest;

public class ParticipantPagingRequest extends BaseGetPagingRequest {

    public Integer conversationId;

    public ParticipantPagingRequest(Integer pageIndex, Integer pageSize, Integer conversationId, Integer userId) {
        super(pageIndex, pageSize, userId);
        this.conversationId = conversationId;
    }
}
