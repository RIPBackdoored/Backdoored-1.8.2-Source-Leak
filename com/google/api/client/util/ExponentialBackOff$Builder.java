package com.google.api.client.util;

public static class Builder
{
    int initialIntervalMillis;
    double randomizationFactor;
    double multiplier;
    int maxIntervalMillis;
    int maxElapsedTimeMillis;
    NanoClock nanoClock;
    
    public Builder() {
        super();
        this.initialIntervalMillis = 500;
        this.randomizationFactor = 0.5;
        this.multiplier = 1.5;
        this.maxIntervalMillis = 60000;
        this.maxElapsedTimeMillis = 900000;
        this.nanoClock = NanoClock.SYSTEM;
    }
    
    public ExponentialBackOff build() {
        return new ExponentialBackOff(this);
    }
    
    public final int getInitialIntervalMillis() {
        return this.initialIntervalMillis;
    }
    
    public Builder setInitialIntervalMillis(final int a1) {
        this.initialIntervalMillis = a1;
        return this;
    }
    
    public final double getRandomizationFactor() {
        return this.randomizationFactor;
    }
    
    public Builder setRandomizationFactor(final double a1) {
        this.randomizationFactor = a1;
        return this;
    }
    
    public final double getMultiplier() {
        return this.multiplier;
    }
    
    public Builder setMultiplier(final double a1) {
        this.multiplier = a1;
        return this;
    }
    
    public final int getMaxIntervalMillis() {
        return this.maxIntervalMillis;
    }
    
    public Builder setMaxIntervalMillis(final int a1) {
        this.maxIntervalMillis = a1;
        return this;
    }
    
    public final int getMaxElapsedTimeMillis() {
        return this.maxElapsedTimeMillis;
    }
    
    public Builder setMaxElapsedTimeMillis(final int a1) {
        this.maxElapsedTimeMillis = a1;
        return this;
    }
    
    public final NanoClock getNanoClock() {
        return this.nanoClock;
    }
    
    public Builder setNanoClock(final NanoClock a1) {
        this.nanoClock = Preconditions.checkNotNull(a1);
        return this;
    }
}
