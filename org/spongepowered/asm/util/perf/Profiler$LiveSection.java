package org.spongepowered.asm.util.perf;

import java.util.*;

class LiveSection extends Section
{
    private int cursor;
    private long[] times;
    private long start;
    private long time;
    private long markedTime;
    private int count;
    private int markedCount;
    final /* synthetic */ Profiler this$0;
    
    LiveSection(final Profiler a1, final String a2, final int a3) {
        this.this$0 = a1;
        a1.super(a2);
        this.cursor = 0;
        this.times = new long[0];
        this.start = 0L;
        this.cursor = a3;
    }
    
    @Override
    Section start() {
        this.start = System.currentTimeMillis();
        return this;
    }
    
    @Override
    protected Section stop() {
        if (this.start > 0L) {
            this.time += System.currentTimeMillis() - this.start;
        }
        this.start = 0L;
        ++this.count;
        return this;
    }
    
    @Override
    public Section end() {
        this.stop();
        if (!this.invalidated) {
            this.this$0.end(this);
        }
        return this;
    }
    
    @Override
    void mark() {
        if (this.cursor >= this.times.length) {
            this.times = Arrays.copyOf(this.times, this.cursor + 4);
        }
        this.times[this.cursor] = this.time;
        this.markedTime += this.time;
        this.markedCount += this.count;
        this.time = 0L;
        this.count = 0;
        ++this.cursor;
    }
    
    @Override
    public long getTime() {
        return this.time;
    }
    
    @Override
    public long getTotalTime() {
        return this.time + this.markedTime;
    }
    
    @Override
    public double getSeconds() {
        return this.time * 0.001;
    }
    
    @Override
    public double getTotalSeconds() {
        return (this.time + this.markedTime) * 0.001;
    }
    
    @Override
    public long[] getTimes() {
        final long[] v1 = new long[this.cursor + 1];
        System.arraycopy(this.times, 0, v1, 0, Math.min(this.times.length, this.cursor));
        v1[this.cursor] = this.time;
        return v1;
    }
    
    @Override
    public int getCount() {
        return this.count;
    }
    
    @Override
    public int getTotalCount() {
        return this.count + this.markedCount;
    }
    
    @Override
    public double getAverageTime() {
        return (this.count > 0) ? (this.time / (double)this.count) : 0.0;
    }
    
    @Override
    public double getTotalAverageTime() {
        return (this.count > 0) ? ((this.time + this.markedTime) / (double)(this.count + this.markedCount)) : 0.0;
    }
}
