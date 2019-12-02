package com.google.api.client.googleapis.auth.clientlogin;

import com.google.api.client.util.*;

public static final class ErrorInfo
{
    @Key("Error")
    public String error;
    @Key("Url")
    public String url;
    @Key("CaptchaToken")
    public String captchaToken;
    @Key("CaptchaUrl")
    public String captchaUrl;
    
    public ErrorInfo() {
        super();
    }
}
