package com.nhom6.messageroomapp.utils;

public class Constant {
    public static final String BASE_URL = "http://messageroom.somee.com";

    public static final String BASE_GOOGLE_URL = "https://storage.googleapis.com/";

    public static final String AUTH_ENDPOINT = "/account/login";

    public static final String Response_Key = "AuthResponse";
    public static final String Token_Key = "Token";

    //Folder on Storage
    public static final String ConversationFolderName = "conversations";
    public static final String UserFolderName = "users";

    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static String phonePattern = "(([03+[2-9]|05+[6|8|9]|07+[0|6|7|8|9]|08+[1-9]|09+[1-4|6-9]]){3})+[0-9]{7}\\b";

    public static int WRITE_STORAGE_REQUEST_CODE = 123;

    public static final int MAX_INPUT_NAME_LENGTH = 64;

    public static final String INTENT_EXTRA_CONVERSATION = "CurrentConversation";
    public static final String INTENT_EXTRA_USER = "currentUser";

    public static String APP_ID = "889f6cc6463f48b385ffbdee610d40c5";
    public static String App_Certificate = "3e5e29e83ef442fcb38ef713326cba98";

    public static final String OUTPUT_DATA_TAG = "OutputData";

    //DateTime pattern
    public static final String ServerPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String TimePattern = "HH:mm";
    public static final String DatePattern = "dd/MM";
    public static final String FullDatePattern = "dd/MM/yyyy";
    public static final String FullDateServerPattern = "yyyy-MM-dd";
    public static final String DateTimePattern = "HH:mm dd/MM";
}
