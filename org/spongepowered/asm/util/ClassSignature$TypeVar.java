package org.spongepowered.asm.util;

static class TypeVar implements Comparable<TypeVar>
{
    private final String originalName;
    private String currentName;
    
    TypeVar(final String a1) {
        super();
        this.originalName = a1;
        this.currentName = a1;
    }
    
    @Override
    public int compareTo(final TypeVar a1) {
        return this.currentName.compareTo(a1.currentName);
    }
    
    @Override
    public String toString() {
        return this.currentName;
    }
    
    String getOriginalName() {
        return this.originalName;
    }
    
    void rename(final String a1) {
        this.currentName = a1;
    }
    
    public boolean matches(final String a1) {
        return this.originalName.equals(a1);
    }
    
    @Override
    public boolean equals(final Object a1) {
        return this.currentName.equals(a1);
    }
    
    @Override
    public int hashCode() {
        return this.currentName.hashCode();
    }
    
    @Override
    public /* bridge */ int compareTo(final Object o) {
        return this.compareTo((TypeVar)o);
    }
}
