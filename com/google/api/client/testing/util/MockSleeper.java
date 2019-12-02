package com.google.api.client.testing.util;

import com.google.api.client.util.*;

@Beta
public class MockSleeper implements Sleeper
{
    private int count;
    private long lastMillis;
    
    public MockSleeper() {
        super();
    }
    
    @Override
    public void sleep(final long a1) throws InterruptedException {
        ++this.count;
        this.lastMillis = a1;
    }
    
    public final int getCount() {
        return this.count;
    }
    
    public final long getLastMillis() {
        return this.lastMillis;
    }
}
