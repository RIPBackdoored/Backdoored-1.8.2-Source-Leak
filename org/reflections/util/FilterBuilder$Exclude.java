package org.reflections.util;

public static class Exclude extends Matcher
{
    public Exclude(final String a1) {
        super(a1);
    }
    
    @Override
    public boolean apply(final String a1) {
        return !this.pattern.matcher(a1).matches();
    }
    
    @Override
    public String toString() {
        return "-" + super.toString();
    }
    
    @Override
    public /* bridge */ boolean apply(final Object o) {
        return this.apply((String)o);
    }
}
