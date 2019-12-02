package com.google.api.client.http;

import com.google.api.client.util.*;
import java.io.*;

@Beta
public class HttpBackOffUnsuccessfulResponseHandler implements HttpUnsuccessfulResponseHandler
{
    private final BackOff backOff;
    private BackOffRequired backOffRequired;
    private Sleeper sleeper;
    
    public HttpBackOffUnsuccessfulResponseHandler(final BackOff a1) {
        super();
        this.backOffRequired = BackOffRequired.ON_SERVER_ERROR;
        this.sleeper = Sleeper.DEFAULT;
        this.backOff = Preconditions.checkNotNull(a1);
    }
    
    public final BackOff getBackOff() {
        return this.backOff;
    }
    
    public final BackOffRequired getBackOffRequired() {
        return this.backOffRequired;
    }
    
    public HttpBackOffUnsuccessfulResponseHandler setBackOffRequired(final BackOffRequired a1) {
        this.backOffRequired = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final Sleeper getSleeper() {
        return this.sleeper;
    }
    
    public HttpBackOffUnsuccessfulResponseHandler setSleeper(final Sleeper a1) {
        this.sleeper = Preconditions.checkNotNull(a1);
        return this;
    }
    
    @Override
    public final boolean handleResponse(final HttpRequest a1, final HttpResponse a2, final boolean a3) throws IOException {
        if (!a3) {
            return false;
        }
        if (this.backOffRequired.isRequired(a2)) {
            try {
                return BackOffUtils.next(this.sleeper, this.backOff);
            }
            catch (InterruptedException ex) {}
        }
        return false;
    }
    
    @Beta
    public interface BackOffRequired
    {
        public static final BackOffRequired ALWAYS = new BackOffRequired() {
            HttpBackOffUnsuccessfulResponseHandler$BackOffRequired$1() {
                super();
            }
            
            @Override
            public boolean isRequired(final HttpResponse a1) {
                return true;
            }
        };
        public static final BackOffRequired ON_SERVER_ERROR = new BackOffRequired() {
            HttpBackOffUnsuccessfulResponseHandler$BackOffRequired$2() {
                super();
            }
            
            @Override
            public boolean isRequired(final HttpResponse a1) {
                return a1.getStatusCode() / 100 == 5;
            }
        };
        
        boolean isRequired(final HttpResponse p0);
    }
}
