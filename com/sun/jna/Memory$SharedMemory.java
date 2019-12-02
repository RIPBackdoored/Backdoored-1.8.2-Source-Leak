package com.sun.jna;

private class SharedMemory extends Memory
{
    final /* synthetic */ Memory this$0;
    
    public SharedMemory(final Memory this$0, final long a1, final long a2) {
        this.this$0 = this$0;
        super();
        this.size = a2;
        this.peer = this$0.peer + a1;
    }
    
    @Override
    protected void dispose() {
        this.peer = 0L;
    }
    
    @Override
    protected void boundsCheck(final long a1, final long a2) {
        this.this$0.boundsCheck(this.peer - this.this$0.peer + a1, a2);
    }
    
    @Override
    public String toString() {
        return super.toString() + " (shared from " + this.this$0.toString() + ")";
    }
}
