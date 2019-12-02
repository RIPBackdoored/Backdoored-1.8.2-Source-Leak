package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import javax.annotation.*;
import com.google.errorprone.annotations.*;
import java.util.*;

@GwtCompatible
public final class MoreObjects
{
    public static <T> T firstNonNull(@Nullable final T a1, @Nullable final T a2) {
        return (a1 != null) ? a1 : Preconditions.checkNotNull(a2);
    }
    
    public static ToStringHelper toStringHelper(final Object a1) {
        return new ToStringHelper(a1.getClass().getSimpleName());
    }
    
    public static ToStringHelper toStringHelper(final Class<?> a1) {
        return new ToStringHelper(a1.getSimpleName());
    }
    
    public static ToStringHelper toStringHelper(final String a1) {
        return new ToStringHelper(a1);
    }
    
    private MoreObjects() {
        super();
    }
    
    public static final class ToStringHelper
    {
        private final String className;
        private final ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitNullValues;
        
        private ToStringHelper(final String a1) {
            super();
            this.holderHead = new ValueHolder();
            this.holderTail = this.holderHead;
            this.omitNullValues = false;
            this.className = Preconditions.checkNotNull(a1);
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper add(final String a1, @Nullable final Object a2) {
            return this.addHolder(a1, a2);
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper add(final String a1, final boolean a2) {
            return this.addHolder(a1, String.valueOf(a2));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper add(final String a1, final char a2) {
            return this.addHolder(a1, String.valueOf(a2));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper add(final String a1, final double a2) {
            return this.addHolder(a1, String.valueOf(a2));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper add(final String a1, final float a2) {
            return this.addHolder(a1, String.valueOf(a2));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper add(final String a1, final int a2) {
            return this.addHolder(a1, String.valueOf(a2));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper add(final String a1, final long a2) {
            return this.addHolder(a1, String.valueOf(a2));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper addValue(@Nullable final Object a1) {
            return this.addHolder(a1);
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper addValue(final boolean a1) {
            return this.addHolder(String.valueOf(a1));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper addValue(final char a1) {
            return this.addHolder(String.valueOf(a1));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper addValue(final double a1) {
            return this.addHolder(String.valueOf(a1));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper addValue(final float a1) {
            return this.addHolder(String.valueOf(a1));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper addValue(final int a1) {
            return this.addHolder(String.valueOf(a1));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper addValue(final long a1) {
            return this.addHolder(String.valueOf(a1));
        }
        
        @Override
        public String toString() {
            final boolean omitNullValues = this.omitNullValues;
            String s = "";
            final StringBuilder append = new StringBuilder(32).append(this.className).append('{');
            for (ValueHolder valueHolder = this.holderHead.next; valueHolder != null; valueHolder = valueHolder.next) {
                final Object v0 = valueHolder.value;
                if (!omitNullValues || v0 != null) {
                    append.append(s);
                    s = ", ";
                    if (valueHolder.name != null) {
                        append.append(valueHolder.name).append('=');
                    }
                    if (v0 != null && v0.getClass().isArray()) {
                        final Object[] v2 = { v0 };
                        final String v3 = Arrays.deepToString(v2);
                        append.append(v3, 1, v3.length() - 1);
                    }
                    else {
                        append.append(v0);
                    }
                }
            }
            return append.append('}').toString();
        }
        
        private ValueHolder addHolder() {
            final ValueHolder v1 = new ValueHolder();
            final ValueHolder holderTail = this.holderTail;
            final ValueHolder valueHolder = v1;
            holderTail.next = valueHolder;
            this.holderTail = valueHolder;
            return v1;
        }
        
        private ToStringHelper addHolder(@Nullable final Object a1) {
            final ValueHolder v1 = this.addHolder();
            v1.value = a1;
            return this;
        }
        
        private ToStringHelper addHolder(final String a1, @Nullable final Object a2) {
            final ValueHolder v1 = this.addHolder();
            v1.value = a2;
            v1.name = Preconditions.checkNotNull(a1);
            return this;
        }
        
        ToStringHelper(final String a1, final MoreObjects$1 a2) {
            this(a1);
        }
        
        private static final class ValueHolder
        {
            String name;
            Object value;
            ValueHolder next;
            
            private ValueHolder() {
                super();
            }
            
            ValueHolder(final MoreObjects$1 a1) {
                this();
            }
        }
    }
}
