package com.fasterxml.jackson.core.util;

import java.io.*;

public class Separators implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final char objectFieldValueSeparator;
    private final char objectEntrySeparator;
    private final char arrayValueSeparator;
    
    public static Separators createDefaultInstance() {
        return new Separators();
    }
    
    public Separators() {
        this(':', ',', ',');
    }
    
    public Separators(final char a1, final char a2, final char a3) {
        super();
        this.objectFieldValueSeparator = a1;
        this.objectEntrySeparator = a2;
        this.arrayValueSeparator = a3;
    }
    
    public Separators withObjectFieldValueSeparator(final char a1) {
        return (this.objectFieldValueSeparator == a1) ? this : new Separators(a1, this.objectEntrySeparator, this.arrayValueSeparator);
    }
    
    public Separators withObjectEntrySeparator(final char a1) {
        return (this.objectEntrySeparator == a1) ? this : new Separators(this.objectFieldValueSeparator, a1, this.arrayValueSeparator);
    }
    
    public Separators withArrayValueSeparator(final char a1) {
        return (this.arrayValueSeparator == a1) ? this : new Separators(this.objectFieldValueSeparator, this.objectEntrySeparator, a1);
    }
    
    public char getObjectFieldValueSeparator() {
        return this.objectFieldValueSeparator;
    }
    
    public char getObjectEntrySeparator() {
        return this.objectEntrySeparator;
    }
    
    public char getArrayValueSeparator() {
        return this.arrayValueSeparator;
    }
}
