package com.sun.jna;

public final class WString implements CharSequence, Comparable
{
    private String string;
    
    public WString(final String a1) {
        super();
        if (a1 == null) {
            throw new NullPointerException("String initializer must be non-null");
        }
        this.string = a1;
    }
    
    @Override
    public String toString() {
        return this.string;
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof WString && this.toString().equals(a1.toString());
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    @Override
    public int compareTo(final Object a1) {
        return this.toString().compareTo(a1.toString());
    }
    
    @Override
    public int length() {
        return this.toString().length();
    }
    
    @Override
    public char charAt(final int a1) {
        return this.toString().charAt(a1);
    }
    
    @Override
    public CharSequence subSequence(final int a1, final int a2) {
        return this.toString().subSequence(a1, a2);
    }
}
