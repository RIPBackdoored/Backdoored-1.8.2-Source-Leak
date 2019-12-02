package javassist.runtime;

private static class Depth
{
    private int depth;
    
    Depth() {
        super();
        this.depth = 0;
    }
    
    int get() {
        return this.depth;
    }
    
    void inc() {
        ++this.depth;
    }
    
    void dec() {
        --this.depth;
    }
}
