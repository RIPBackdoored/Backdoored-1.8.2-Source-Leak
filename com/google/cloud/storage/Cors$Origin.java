package com.google.cloud.storage;

import java.io.*;
import com.google.common.base.*;
import java.net.*;

public static final class Origin implements Serializable
{
    private static final long serialVersionUID = -4447958124895577993L;
    private static final String ANY_URI = "*";
    private final String value;
    private static final Origin ANY;
    
    private Origin(final String a1) {
        super();
        this.value = Preconditions.checkNotNull(a1);
    }
    
    public static Origin any() {
        return Origin.ANY;
    }
    
    public static Origin of(final String a2, final String a3, final int v1) {
        try {
            return of(new URI(a2, null, a3, v1, null, null, null).toString());
        }
        catch (URISyntaxException a4) {
            throw new IllegalArgumentException(a4);
        }
    }
    
    public static Origin of(final String a1) {
        if ("*".equals(a1)) {
            return any();
        }
        return new Origin(a1);
    }
    
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof Origin && this.value.equals(((Origin)a1).value);
    }
    
    @Override
    public String toString() {
        return this.getValue();
    }
    
    public String getValue() {
        return this.value;
    }
    
    static {
        ANY = new Origin("*");
    }
}
