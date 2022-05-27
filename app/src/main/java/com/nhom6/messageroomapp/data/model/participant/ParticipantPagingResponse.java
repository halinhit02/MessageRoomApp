package com.nhom6.messageroomapp.data.model.participant;

import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.base.BasePagingResponse;

public class ParticipantPagingResponse extends BaseAPIResponse<BasePagingResponse<Participant>> {
    public ParticipantPagingResponse(Boolean isSucceeded, String message) {
        super(isSucceeded, message);
    }

    public ParticipantPagingResponse(Boolean isSucceeded, String message, int code) {
        super(isSucceeded, message, code);
    }

    public ParticipantPagingResponse(Boolean isSucceeded, String message, BasePagingResponse<Participant> result) {
        super(isSucceeded, message, result);
    }
}
