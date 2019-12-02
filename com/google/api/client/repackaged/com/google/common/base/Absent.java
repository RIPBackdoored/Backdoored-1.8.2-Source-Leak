package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
final class Absent<T> extends Optional<T>
{
    static final Absent<Object> INSTANCE;
    private static final long serialVersionUID = 0L;
    
    static <T> Optional<T> withType() {
        return (Optional<T>)Absent.INSTANCE;
    }
    
    private Absent() {
        super();
    }
    
    @Override
    public boolean isPresent() {
        return false;
    }
    
    @Override
    public T get() {
        throw new IllegalStateException("Optional.get() cannot be called on an absent value");
    }
    
    @Override
    public T or(final T a1) {
        return Preconditions.checkNotNull(a1, (Object)"use Optional.orNull() instead of Optional.or(null)");
    }
    
    @Override
    public Optional<T> or(final Optional<? extends T> a1) {
        return Preconditions.checkNotNull((Optional<T>)a1);
    }
    
    @Override
    public T or(final Supplier<? extends T> a1) {
        return Preconditions.checkNotNull((T)a1.get(), (Object)"use Optional.orNull() instead of a Supplier that returns null");
    }
    
    @Nullable
    @Override
    public T orNull() {
        return null;
    }
    
    @Override
    public Set<T> asSet() {
        return Collections.emptySet();
    }
    
    @Override
    public <V> Optional<V> transform(final Function<? super T, V> a1) {
        Preconditions.checkNotNull(a1);
        return Optional.absent();
    }
    
    @Override
    public boolean equals(@Nullable final Object a1) {
        return a1 == this;
    }
    
    @Override
    public int hashCode() {
        return 2040732332;
    }
    
    @Override
    public String toString() {
        return "Optional.absent()";
    }
    
    private Object readResolve() {
        return Absent.INSTANCE;
    }
    
    static {
        INSTANCE = new Absent<Object>();
    }
}
