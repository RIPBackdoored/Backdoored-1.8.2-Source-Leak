package com.google.api.client.http;

import com.google.api.client.util.*;

@Deprecated
@Beta
public static class Builder
{
    final ExponentialBackOff.Builder exponentialBackOffBuilder;
    
    protected Builder() {
        super();
        this.exponentialBackOffBuilder = new ExponentialBackOff.Builder();
    }
    
    public ExponentialBackOffPolicy build() {
        return new ExponentialBackOffPolicy(this);
    }
    
    public final int getInitialIntervalMillis() {
        return this.exponentialBackOffBuilder.getInitialIntervalMillis();
    }
    
    public Builder setInitialIntervalMillis(final int a1) {
        this.exponentialBackOffBuilder.setInitialIntervalMillis(a1);
        return this;
    }
    
    public final double getRandomizationFactor() {
        return this.exponentialBackOffBuilder.getRandomizationFactor();
    }
    
    public Builder setRandomizationFactor(final double a1) {
        this.exponentialBackOffBuilder.setRandomizationFactor(a1);
        return this;
    }
    
    public final double getMultiplier() {
        return this.exponentialBackOffBuilder.getMultiplier();
    }
    
    public Builder setMultiplier(final double a1) {
        this.exponentialBackOffBuilder.setMultiplier(a1);
        return this;
    }
    
    public final int getMaxIntervalMillis() {
        return this.exponentialBackOffBuilder.getMaxIntervalMillis();
    }
    
    public Builder setMaxIntervalMillis(final int a1) {
        this.exponentialBackOffBuilder.setMaxIntervalMillis(a1);
        return this;
    }
    
    public final int getMaxElapsedTimeMillis() {
        return this.exponentialBackOffBuilder.getMaxElapsedTimeMillis();
    }
    
    public Builder setMaxElapsedTimeMillis(final int a1) {
        this.exponentialBackOffBuilder.setMaxElapsedTimeMillis(a1);
        return this;
    }
    
    public final NanoClock getNanoClock() {
        return this.exponentialBackOffBuilder.getNanoClock();
    }
    
    public Builder setNanoClock(final NanoClock a1) {
        this.exponentialBackOffBuilder.setNanoClock(a1);
        return this;
    }
}
