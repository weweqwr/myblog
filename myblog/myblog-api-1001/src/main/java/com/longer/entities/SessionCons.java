package com.longer.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public interface SessionCons {

    String LOGIN_USER_PERMISSIONS = "session_login_user_permissions";
    String LOGIN_USER_SESSION = "session_login_user";
    String TOKEN_PREFIX = "web_session_key-";
    String TOKEN_PREFIX_KEY = "web_session_key-*";
    String DEVICE_TYPE = "device_type";
}
