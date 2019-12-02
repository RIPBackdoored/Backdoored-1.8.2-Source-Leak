package org.json;

private static final class Null
{
    private Null() {
        super();
    }
    
    @Override
    protected final Object clone() {
        return this;
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 == null || a1 == this;
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public String toString() {
        return "null";
    }
    
    Null(final JSONObject$1 a1) {
        this();
    }
}
