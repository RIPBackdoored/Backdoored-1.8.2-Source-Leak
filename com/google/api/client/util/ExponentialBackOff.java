package com.google.api.client.util;

import java.io.*;

public class ExponentialBackOff implements BackOff
{
    public static final int DEFAULT_INITIAL_INTERVAL_MILLIS = 500;
    public static final double DEFAULT_RANDOMIZATION_FACTOR = 0.5;
    public static final double DEFAULT_MULTIPLIER = 1.5;
    public static final int DEFAULT_MAX_INTERVAL_MILLIS = 60000;
    public static final int DEFAULT_MAX_ELAPSED_TIME_MILLIS = 900000;
    private int currentIntervalMillis;
    private final int initialIntervalMillis;
    private final double randomizationFactor;
    private final double multiplier;
    private final int maxIntervalMillis;
    long startTimeNanos;
    private final int maxElapsedTimeMillis;
    private final NanoClock nanoClock;
    
    public ExponentialBackOff() {
        this(new Builder());
    }
    
    protected ExponentialBackOff(final Builder a1) {
        super();
        this.initialIntervalMillis = a1.initialIntervalMillis;
        this.randomizationFactor = a1.randomizationFactor;
        this.multiplier = a1.multiplier;
        this.maxIntervalMillis = a1.maxIntervalMillis;
        this.maxElapsedTimeMillis = a1.maxElapsedTimeMillis;
        this.nanoClock = a1.nanoClock;
        Preconditions.checkArgument(this.initialIntervalMillis > 0);
        Preconditions.checkArgument(0.0 <= this.randomizationFactor && this.randomizationFactor < 1.0);
        Preconditions.checkArgument(this.multiplier >= 1.0);
        Preconditions.checkArgument(this.maxIntervalMillis >= this.initialIntervalMillis);
        Preconditions.checkArgument(this.maxElapsedTimeMillis > 0);
        this.reset();
    }
    
    @Override
    public final void reset() {
        this.currentIntervalMillis = this.initialIntervalMillis;
        this.startTimeNanos = this.nanoClock.nanoTime();
    }
    
    @Override
    public long nextBackOffMillis() throws IOException {
        if (this.getElapsedTimeMillis() > this.maxElapsedTimeMillis) {
            return -1L;
        }
        final int v1 = getRandomValueFromInterval(this.randomizationFactor, Math.random(), this.currentIntervalMillis);
        this.incrementCurrentInterval();
        return v1;
    }
    
    static int getRandomValueFromInterval(final double a1, final double a2, final int a3) {
        final double v1 = a1 * a3;
        final double v2 = a3 - v1;
        final double v3 = a3 + v1;
        final int v4 = (int)(v2 + a2 * (v3 - v2 + 1.0));
        return v4;
    }
    
    public final int getInitialIntervalMillis() {
        return this.initialIntervalMillis;
    }
    
    public final double getRandomizationFactor() {
        return this.randomizationFactor;
    }
    
    public final int getCurrentIntervalMillis() {
        return this.currentIntervalMillis;
    }
    
    public final double getMultiplier() {
        return this.multiplier;
    }
    
    public final int getMaxIntervalMillis() {
        return this.maxIntervalMillis;
    }
    
    public final int getMaxElapsedTimeMillis() {
        return this.maxElapsedTimeMillis;
    }
    
    public final long getElapsedTimeMillis() {
        return (this.nanoClock.nanoTime() - this.startTimeNanos) / 1000000L;
    }
    
    private void incrementCurrentInterval() {
        if (this.currentIntervalMillis >= this.maxIntervalMillis / this.multiplier) {
            this.currentIntervalMillis = this.maxIntervalMillis;
        }
        else {
            this.currentIntervalMillis *= (int)this.multiplier;
        }
    }
    
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
}
