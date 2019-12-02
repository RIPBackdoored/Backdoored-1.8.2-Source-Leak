package com.google.api.client.repackaged.com.google.common.base;

import java.io.*;
import com.google.errorprone.annotations.*;
import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

public static final class MapJoiner
{
    private final Joiner joiner;
    private final String keyValueSeparator;
    
    private MapJoiner(final Joiner a1, final String a2) {
        super();
        this.joiner = a1;
        this.keyValueSeparator = Preconditions.checkNotNull(a2);
    }
    
    @CanIgnoreReturnValue
    public <A extends java.lang.Object> A appendTo(final A a1, final Map<?, ?> a2) throws IOException {
        return (A)this.appendTo((Appendable)a1, (Iterable)a2.entrySet());
    }
    
    @CanIgnoreReturnValue
    public StringBuilder appendTo(final StringBuilder a1, final Map<?, ?> a2) {
        return this.appendTo(a1, (Iterable<? extends Map.Entry<?, ?>>)a2.entrySet());
    }
    
    public String join(final Map<?, ?> a1) {
        return this.join(a1.entrySet());
    }
    
    @Beta
    @CanIgnoreReturnValue
    public <A extends java.lang.Object> A appendTo(final A a1, final Iterable<? extends Map.Entry<?, ?>> a2) throws IOException {
        return (A)this.appendTo((Appendable)a1, (Iterator)a2.iterator());
    }
    
    @Beta
    @CanIgnoreReturnValue
    public <A extends java.lang.Object> A appendTo(final A v2, final Iterator<? extends Map.Entry<?, ?>> v3) throws IOException {
        Preconditions.checkNotNull(v2);
        if (v3.hasNext()) {
            final Map.Entry<?, ?> a2 = (Map.Entry<?, ?>)v3.next();
            ((Appendable)v2).append(this.joiner.toString(a2.getKey()));
            ((Appendable)v2).append(this.keyValueSeparator);
            ((Appendable)v2).append(this.joiner.toString(a2.getValue()));
            while (v3.hasNext()) {
                ((Appendable)v2).append(Joiner.access$100(this.joiner));
                final Map.Entry<?, ?> a3 = (Map.Entry<?, ?>)v3.next();
                ((Appendable)v2).append(this.joiner.toString(a3.getKey()));
                ((Appendable)v2).append(this.keyValueSeparator);
                ((Appendable)v2).append(this.joiner.toString(a3.getValue()));
            }
        }
        return v2;
    }
    
    @Beta
    @CanIgnoreReturnValue
    public StringBuilder appendTo(final StringBuilder a1, final Iterable<? extends Map.Entry<?, ?>> a2) {
        return this.appendTo(a1, a2.iterator());
    }
    
    @Beta
    @CanIgnoreReturnValue
    public StringBuilder appendTo(final StringBuilder v1, final Iterator<? extends Map.Entry<?, ?>> v2) {
        try {
            this.appendTo((Appendable)v1, (Iterator)v2);
        }
        catch (IOException a1) {
            throw new AssertionError((Object)a1);
        }
        return v1;
    }
    
    @Beta
    public String join(final Iterable<? extends Map.Entry<?, ?>> a1) {
        return this.join(a1.iterator());
    }
    
    @Beta
    public String join(final Iterator<? extends Map.Entry<?, ?>> a1) {
        return this.appendTo(new StringBuilder(), a1).toString();
    }
    
    public MapJoiner useForNull(final String a1) {
        return new MapJoiner(this.joiner.useForNull(a1), this.keyValueSeparator);
    }
    
    MapJoiner(final Joiner a1, final String a2, final Joiner$1 a3) {
        this(a1, a2);
    }
}
