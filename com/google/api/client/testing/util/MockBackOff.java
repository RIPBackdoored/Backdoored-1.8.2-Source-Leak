package com.google.api.client.testing.util;

import java.io.*;
import com.google.api.client.util.*;

@Beta
public class MockBackOff implements BackOff
{
    private long backOffMillis;
    private int maxTries;
    private int numTries;
    
    public MockBackOff() {
        super();
        this.maxTries = 10;
    }
    
    @Override
    public void reset() throws IOException {
        this.numTries = 0;
    }
    
    @Override
    public long nextBackOffMillis() throws IOException {
        if (this.numTries >= this.maxTries || this.backOffMillis == -1L) {
            return -1L;
        }
        ++this.numTries;
        return this.backOffMillis;
    }
    
    public MockBackOff setBackOffMillis(final long a1) {
        Preconditions.checkArgument(a1 == -1L || a1 >= 0L);
        this.backOffMillis = a1;
        return this;
    }
    
    public MockBackOff setMaxTries(final int a1) {
        Preconditions.checkArgument(a1 >= 0);
        this.maxTries = a1;
        return this;
    }
    
    public final int getMaxTries() {
        return this.numTries;
    }
    
    public final int getNumberOfTries() {
        return this.numTries;
    }
}
