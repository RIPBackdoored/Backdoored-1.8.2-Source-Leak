package com.google.api.client.testing.http;

import com.google.api.client.util.*;
import java.util.concurrent.atomic.*;

@Beta
public class FixedClock implements Clock
{
    private AtomicLong currentTime;
    
    public FixedClock() {
        this(0L);
    }
    
    public FixedClock(final long a1) {
        super();
        this.currentTime = new AtomicLong(a1);
    }
    
    public FixedClock setTime(final long a1) {
        this.currentTime.set(a1);
        return this;
    }
    
    @Override
    public long currentTimeMillis() {
        return this.currentTime.get();
    }
}
