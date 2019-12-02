package com.fasterxml.jackson.core.sym;

public abstract class Name
{
    protected final String _name;
    protected final int _hashCode;
    
    protected Name(final String a1, final int a2) {
        super();
        this._name = a1;
        this._hashCode = a2;
    }
    
    public String getName() {
        return this._name;
    }
    
    public abstract boolean equals(final int p0);
    
    public abstract boolean equals(final int p0, final int p1);
    
    public abstract boolean equals(final int p0, final int p1, final int p2);
    
    public abstract boolean equals(final int[] p0, final int p1);
    
    @Override
    public String toString() {
        return this._name;
    }
    
    @Override
    public final int hashCode() {
        return this._hashCode;
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 == this;
    }
}
