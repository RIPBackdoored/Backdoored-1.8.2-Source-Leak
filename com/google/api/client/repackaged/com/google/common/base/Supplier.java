package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import com.google.errorprone.annotations.*;

@GwtCompatible
public interface Supplier<T>
{
    @CanIgnoreReturnValue
    T get();
}
