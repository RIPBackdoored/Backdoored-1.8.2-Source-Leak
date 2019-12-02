package com.google.api.client.googleapis.auth.clientlogin;

import com.google.api.client.util.*;
import java.io.*;
import com.google.api.client.http.*;

@Beta
public final class ClientLogin
{
    public HttpTransport transport;
    public GenericUrl serverUrl;
    @Key("source")
    public String applicationName;
    @Key("service")
    public String authTokenType;
    @Key("Email")
    public String username;
    @Key("Passwd")
    public String password;
    @Key
    public String accountType;
    @Key("logintoken")
    public String captchaToken;
    @Key("logincaptcha")
    public String captchaAnswer;
    
    public ClientLogin() {
        super();
        this.serverUrl = new GenericUrl("https://www.google.com");
    }
    
    public Response authenticate() throws IOException {
        final GenericUrl v1 = this.serverUrl.clone();
        v1.appendRawPath("/accounts/ClientLogin");
        final HttpRequest v2 = this.transport.createRequestFactory().buildPostRequest(v1, new UrlEncodedContent(this));
        v2.setParser(AuthKeyValueParser.INSTANCE);
        v2.setContentLoggingLimit(0);
        v2.setThrowExceptionOnExecuteError(false);
        final HttpResponse v3 = v2.execute();
        if (v3.isSuccessStatusCode()) {
            return v3.parseAs(Response.class);
        }
        final HttpResponseException.Builder v4 = new HttpResponseException.Builder(v3.getStatusCode(), v3.getStatusMessage(), v3.getHeaders());
        final ErrorInfo v5 = v3.parseAs(ErrorInfo.class);
        final String v6 = v5.toString();
        final StringBuilder v7 = HttpResponseException.computeMessageBuffer(v3);
        if (!Strings.isNullOrEmpty(v6)) {
            v7.append(StringUtils.LINE_SEPARATOR).append(v6);
            v4.setContent(v6);
        }
        v4.setMessage(v7.toString());
        throw new ClientLoginResponseException(v4, v5);
    }
    
    public static String getAuthorizationHeaderValue(final String a1) {
        final String s = "GoogleLogin auth=";
        final String value = String.valueOf(a1);
        return (value.length() != 0) ? s.concat(value) : new String(s);
    }
    
    public static final class Response implements HttpExecuteInterceptor, HttpRequestInitializer
    {
        @Key("Auth")
        public String auth;
        
        public Response() {
            super();
        }
        
        public String getAuthorizationHeaderValue() {
            return ClientLogin.getAuthorizationHeaderValue(this.auth);
        }
        
        public void initialize(final HttpRequest a1) {
            a1.setInterceptor(this);
        }
        
        public void intercept(final HttpRequest a1) {
            a1.getHeaders().setAuthorization(this.getAuthorizationHeaderValue());
        }
    }
    
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
}
