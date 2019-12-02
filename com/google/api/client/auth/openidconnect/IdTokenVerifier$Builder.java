package com.google.api.client.auth.openidconnect;

import com.google.api.client.util.*;
import java.util.*;

@Beta
public static class Builder
{
    Clock clock;
    long acceptableTimeSkewSeconds;
    Collection<String> issuers;
    Collection<String> audience;
    
    public Builder() {
        super();
        this.clock = Clock.SYSTEM;
        this.acceptableTimeSkewSeconds = 300L;
    }
    
    public IdTokenVerifier build() {
        return new IdTokenVerifier(this);
    }
    
    public final Clock getClock() {
        return this.clock;
    }
    
    public Builder setClock(final Clock a1) {
        this.clock = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final String getIssuer() {
        if (this.issuers == null) {
            return null;
        }
        return this.issuers.iterator().next();
    }
    
    public Builder setIssuer(final String a1) {
        if (a1 == null) {
            return this.setIssuers(null);
        }
        return this.setIssuers(Collections.singleton(a1));
    }
    
    public final Collection<String> getIssuers() {
        return this.issuers;
    }
    
    public Builder setIssuers(final Collection<String> a1) {
        Preconditions.checkArgument(a1 == null || !a1.isEmpty(), (Object)"Issuers must not be empty");
        this.issuers = a1;
        return this;
    }
    
    public final Collection<String> getAudience() {
        return this.audience;
    }
    
    public Builder setAudience(final Collection<String> a1) {
        this.audience = a1;
        return this;
    }
    
    public final long getAcceptableTimeSkewSeconds() {
        return this.acceptableTimeSkewSeconds;
    }
    
    public Builder setAcceptableTimeSkewSeconds(final long a1) {
        Preconditions.checkArgument(a1 >= 0L);
        this.acceptableTimeSkewSeconds = a1;
        return this;
    }
}
