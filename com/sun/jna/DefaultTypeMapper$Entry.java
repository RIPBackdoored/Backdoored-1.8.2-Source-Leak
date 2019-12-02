package com.sun.jna;

private static class Entry
{
    public Class<?> type;
    public Object converter;
    
    public Entry(final Class<?> a1, final Object a2) {
        super();
        this.type = a1;
        this.converter = a2;
    }
}
