package com.sun.jna;

private class StringMemory extends Memory
{
    final /* synthetic */ NativeString this$0;
    
    public StringMemory(final NativeString this$0, final long a1) {
        this.this$0 = this$0;
        super(a1);
    }
    
    @Override
    public String toString() {
        return this.this$0.toString();
    }
}
