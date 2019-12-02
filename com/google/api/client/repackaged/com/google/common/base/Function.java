package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import javax.annotation.*;
import com.google.errorprone.annotations.*;

@GwtCompatible
public interface Function<F, T>
{
    @Nullable
    @CanIgnoreReturnValue
    T apply(@Nullable final F p0);
    
    boolean equals(@Nullable final Object p0);
}
