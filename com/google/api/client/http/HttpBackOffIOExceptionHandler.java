package com.google.api.client.http;

import com.google.api.client.util.*;
import java.io.*;

@Beta
public class HttpBackOffIOExceptionHandler implements HttpIOExceptionHandler
{
    private final BackOff backOff;
    private Sleeper sleeper;
    
    public HttpBackOffIOExceptionHandler(final BackOff a1) {
        super();
        this.sleeper = Sleeper.DEFAULT;
        this.backOff = Preconditions.checkNotNull(a1);
    }
    
    public final BackOff getBackOff() {
        return this.backOff;
    }
    
    public final Sleeper getSleeper() {
        return this.sleeper;
    }
    
    public HttpBackOffIOExceptionHandler setSleeper(final Sleeper a1) {
        this.sleeper = Preconditions.checkNotNull(a1);
        return this;
    }
    
    @Override
    public boolean handleIOException(final HttpRequest v1, final boolean v2) throws IOException {
        if (!v2) {
            return false;
        }
        try {
            return BackOffUtils.next(this.sleeper, this.backOff);
        }
        catch (InterruptedException a1) {
            return false;
        }
    }
}
