package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;

@GwtCompatible
final class Present<T> extends Optional<T>
{
    private final T reference;
    private static final long serialVersionUID = 0L;
    
    Present(final T a1) {
        super();
        this.reference = a1;
    }
    
    @Override
    public boolean isPresent() {
        return true;
    }
    
    @Override
    public T get() {
        return this.reference;
    }
    
    @Override
    public T or(final T a1) {
        Preconditions.checkNotNull(a1, (Object)"use Optional.orNull() instead of Optional.or(null)");
        return this.reference;
    }
    
    @Override
    public Optional<T> or(final Optional<? extends T> a1) {
        Preconditions.checkNotNull(a1);
        return this;
    }
    
    @Override
    public T or(final Supplier<? extends T> a1) {
        Preconditions.checkNotNull(a1);
        return this.reference;
    }
    
    @Override
    public T orNull() {
        return this.reference;
    }
    
    @Override
    public Set<T> asSet() {
        return Collections.singleton(this.reference);
    }
    
    @Override
    public <V> Optional<V> transform(final Function<? super T, V> a1) {
        return new Present<V>(Preconditions.checkNotNull(a1.apply((Object)this.reference), (Object)"the Function passed to Optional.transform() must not return null."));
    }
    
    @Override
    public boolean equals(@Nullable final Object v2) {
        if (v2 instanceof Present) {
            final Present<?> a1 = (Present<?>)v2;
            return this.reference.equals(a1.reference);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 1502476572 + this.reference.hashCode();
    }
    
    @Override
    public String toString() {
        return "Optional.of(" + this.reference + ")";
    }
}
