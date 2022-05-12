package com.nhom6.messageroomapp.data.model.user;

import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;

public class UserUpdateResponse extends BaseAPIResponse<Boolean> {
    public UserUpdateResponse(Boolean isSucceeded, String message) {
        super(isSucceeded, message);
    }

    public UserUpdateResponse(Boolean isSucceeded, String message, int code) {
        super(isSucceeded, message, code);
    }

    public UserUpdateResponse(Boolean isSucceeded, String message, Boolean result) {
        super(isSucceeded, message, result);
    }
}
