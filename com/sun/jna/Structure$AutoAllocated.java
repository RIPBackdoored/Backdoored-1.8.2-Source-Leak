package com.sun.jna;

private static class AutoAllocated extends Memory
{
    public AutoAllocated(final int a1) {
        super(a1);
        super.clear();
    }
    
    @Override
    public String toString() {
        return "auto-" + super.toString();
    }
}
