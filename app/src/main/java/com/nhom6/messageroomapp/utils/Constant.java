package com.nhom6.messageroomapp.utils;

public class Constant {
    public static final String BASE_URL = "http://messageroom.somee.com"; /*"http://10.0.2.2:5000";*/

    public static final String BASE_GOOGLE_URL = "https://storage.googleapis.com/";

    public static final String AUTH_ENDPOINT = "/account/login";

    public static final String Response_Key = "AuthResponse";
    public static final String Token_Key = "Token";

    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static String phonePattern = "(([03+[2-9]|05+[6|8|9]|07+[0|6|7|8|9]|08+[1-9]|09+[1-4|6-9]]){3})+[0-9]{7}\\b";

    public static int WRITE_STORAGE_REQUEST_CODE = 123;

    public static final int MAX_INPUT_NAME_LENGTH = 64;

    public static final String OUTPUT_DATA_TAG = "OutputData";

    //DateTime pattern
    public static final String ServerPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String TimePattern = "HH:mm";
    public static final String DatePattern = "dd/MM";
    public static final String FullDatePattern = "dd/MM/yyyy";
    public static final String FullDateServerPattern = "yyyy-MM-dd";
    public static final String DateTimePattern = "HH:mm dd/MM";
}
