package org.spongepowered.asm.util.perf;

public class Section
{
    static final String SEPARATOR_ROOT = " -> ";
    static final String SEPARATOR_CHILD = ".";
    private final String name;
    private boolean root;
    private boolean fine;
    protected boolean invalidated;
    private String info;
    final /* synthetic */ Profiler this$0;
    
    Section(final Profiler a1, final String a2) {
        this.this$0 = a1;
        super();
        this.name = a2;
        this.info = a2;
    }
    
    Section getDelegate() {
        return this;
    }
    
    Section invalidate() {
        this.invalidated = true;
        return this;
    }
    
    Section setRoot(final boolean a1) {
        this.root = a1;
        return this;
    }
    
    public boolean isRoot() {
        return this.root;
    }
    
    Section setFine(final boolean a1) {
        this.fine = a1;
        return this;
    }
    
    public boolean isFine() {
        return this.fine;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getBaseName() {
        return this.name;
    }
    
    public void setInfo(final String a1) {
        this.info = a1;
    }
    
    public String getInfo() {
        return this.info;
    }
    
    Section start() {
        return this;
    }
    
    protected Section stop() {
        return this;
    }
    
    public Section end() {
        if (!this.invalidated) {
            this.this$0.end(this);
        }
        return this;
    }
    
    public Section next(final String a1) {
        this.end();
        return this.this$0.begin(a1);
    }
    
    void mark() {
    }
    
    public long getTime() {
        return 0L;
    }
    
    public long getTotalTime() {
        return 0L;
    }
    
    public double getSeconds() {
        return 0.0;
    }
    
    public double getTotalSeconds() {
        return 0.0;
    }
    
    public long[] getTimes() {
        return new long[1];
    }
    
    public int getCount() {
        return 0;
    }
    
    public int getTotalCount() {
        return 0;
    }
    
    public double getAverageTime() {
        return 0.0;
    }
    
    public double getTotalAverageTime() {
        return 0.0;
    }
    
    @Override
    public final String toString() {
        return this.name;
    }
}
