package com.google.api.client.extensions.jetty.auth.oauth2;

public static final class Builder
{
    private String host;
    private int port;
    private String successLandingPageUrl;
    private String failureLandingPageUrl;
    private String callbackPath;
    
    public Builder() {
        super();
        this.host = "localhost";
        this.port = -1;
        this.callbackPath = "/Callback";
    }
    
    public LocalServerReceiver build() {
        return new LocalServerReceiver(this.host, this.port, this.callbackPath, this.successLandingPageUrl, this.failureLandingPageUrl);
    }
    
    public String getHost() {
        return this.host;
    }
    
    public Builder setHost(final String a1) {
        this.host = a1;
        return this;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public Builder setPort(final int a1) {
        this.port = a1;
        return this;
    }
    
    public String getCallbackPath() {
        return this.callbackPath;
    }
    
    public Builder setCallbackPath(final String a1) {
        this.callbackPath = a1;
        return this;
    }
    
    public Builder setLandingPages(final String a1, final String a2) {
        this.successLandingPageUrl = a1;
        this.failureLandingPageUrl = a2;
        return this;
    }
}
