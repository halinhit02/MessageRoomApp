package com.nhom6.messageroomapp.data.model.storage;

import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;

public class SignedUrlGetResponse extends BaseAPIResponse<SignedUrl> {
    public SignedUrlGetResponse(Boolean isSucceeded, String message) {
        super(isSucceeded, message);
    }

    public SignedUrlGetResponse(Boolean isSucceeded, String message, int code) {
        super(isSucceeded, message, code);
    }

    public SignedUrlGetResponse(Boolean isSucceeded, String message, SignedUrl result) {
        super(isSucceeded, message, result);
    }
}

