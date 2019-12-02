package com.google.api.client.repackaged.com.google.common.base;

import java.io.*;
import com.google.errorprone.annotations.*;
import javax.annotation.*;
import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

@GwtCompatible
public class Joiner
{
    private final String separator;
    
    public static Joiner on(final String a1) {
        return new Joiner(a1);
    }
    
    public static Joiner on(final char a1) {
        return new Joiner(String.valueOf(a1));
    }
    
    private Joiner(final String a1) {
        super();
        this.separator = Preconditions.checkNotNull(a1);
    }
    
    private Joiner(final Joiner a1) {
        super();
        this.separator = a1.separator;
    }
    
    @CanIgnoreReturnValue
    public <A extends java.lang.Object> A appendTo(final A a1, final Iterable<?> a2) throws IOException {
        return (A)this.appendTo((Appendable)a1, (Iterator)a2.iterator());
    }
    
    @CanIgnoreReturnValue
    public <A extends java.lang.Object> A appendTo(final A a1, final Iterator<?> a2) throws IOException {
        Preconditions.checkNotNull(a1);
        if (a2.hasNext()) {
            ((Appendable)a1).append(this.toString(a2.next()));
            while (a2.hasNext()) {
                ((Appendable)a1).append(this.separator);
                ((Appendable)a1).append(this.toString(a2.next()));
            }
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public final <A extends java.lang.Object> A appendTo(final A a1, final Object[] a2) throws IOException {
        return (A)this.appendTo((Appendable)a1, (Iterable)Arrays.asList(a2));
    }
    
    @CanIgnoreReturnValue
    public final <A extends java.lang.Object> A appendTo(final A a1, @Nullable final Object a2, @Nullable final Object a3, final Object... a4) throws IOException {
        return (A)this.appendTo((Appendable)a1, (Iterable)iterable(a2, a3, a4));
    }
    
    @CanIgnoreReturnValue
    public final StringBuilder appendTo(final StringBuilder a1, final Iterable<?> a2) {
        return this.appendTo(a1, a2.iterator());
    }
    
    @CanIgnoreReturnValue
    public final StringBuilder appendTo(final StringBuilder v1, final Iterator<?> v2) {
        try {
            this.appendTo((Appendable)v1, (Iterator)v2);
        }
        catch (IOException a1) {
            throw new AssertionError((Object)a1);
        }
        return v1;
    }
    
    @CanIgnoreReturnValue
    public final StringBuilder appendTo(final StringBuilder a1, final Object[] a2) {
        return this.appendTo(a1, (Iterable<?>)Arrays.asList(a2));
    }
    
    @CanIgnoreReturnValue
    public final StringBuilder appendTo(final StringBuilder a1, @Nullable final Object a2, @Nullable final Object a3, final Object... a4) {
        return this.appendTo(a1, (Iterable<?>)iterable(a2, a3, a4));
    }
    
    public final String join(final Iterable<?> a1) {
        return this.join(a1.iterator());
    }
    
    public final String join(final Iterator<?> a1) {
        return this.appendTo(new StringBuilder(), a1).toString();
    }
    
    public final String join(final Object[] a1) {
        return this.join(Arrays.asList(a1));
    }
    
    public final String join(@Nullable final Object a1, @Nullable final Object a2, final Object... a3) {
        return this.join(iterable(a1, a2, a3));
    }
    
    public Joiner useForNull(final String a1) {
        Preconditions.checkNotNull(a1);
        return new Joiner(this) {
            final /* synthetic */ String val$nullText;
            final /* synthetic */ Joiner this$0;
            
            Joiner$1(final Joiner a1) {
                this.this$0 = this$0;
                super(a1, null);
            }
            
            @Override
            CharSequence toString(@Nullable final Object a1) {
                return (a1 == null) ? a1 : this.this$0.toString(a1);
            }
            
            @Override
            public Joiner useForNull(final String a1) {
                throw new UnsupportedOperationException("already specified useForNull");
            }
            
            @Override
            public Joiner skipNulls() {
                throw new UnsupportedOperationException("already specified useForNull");
            }
        };
    }
    
    public Joiner skipNulls() {
        return new Joiner(this) {
            final /* synthetic */ Joiner this$0;
            
            Joiner$2(final Joiner a1) {
                this.this$0 = this$0;
                super(a1, null);
            }
            
            @Override
            public <A extends java.lang.Object> A appendTo(final A v2, final Iterator<?> v3) throws IOException {
                Preconditions.checkNotNull(v2, (Object)"appendable");
                Preconditions.checkNotNull(v3, (Object)"parts");
                while (v3.hasNext()) {
                    final Object a1 = v3.next();
                    if (a1 != null) {
                        ((Appendable)v2).append(this.this$0.toString(a1));
                        break;
                    }
                }
                while (v3.hasNext()) {
                    final Object a2 = v3.next();
                    if (a2 != null) {
                        ((Appendable)v2).append(this.this$0.separator);
                        ((Appendable)v2).append(this.this$0.toString(a2));
                    }
                }
                return v2;
            }
            
            @Override
            public Joiner useForNull(final String a1) {
                throw new UnsupportedOperationException("already specified skipNulls");
            }
            
            @Override
            public MapJoiner withKeyValueSeparator(final String a1) {
                throw new UnsupportedOperationException("can't use .skipNulls() with maps");
            }
        };
    }
    
    public MapJoiner withKeyValueSeparator(final char a1) {
        return this.withKeyValueSeparator(String.valueOf(a1));
    }
    
    public MapJoiner withKeyValueSeparator(final String a1) {
        return new MapJoiner(this, a1);
    }
    
    CharSequence toString(final Object a1) {
        Preconditions.checkNotNull(a1);
        return (a1 instanceof CharSequence) ? ((CharSequence)a1) : a1.toString();
    }
    
    private static Iterable<Object> iterable(final Object a1, final Object a2, final Object[] a3) {
        Preconditions.checkNotNull(a3);
        return new AbstractList<Object>() {
            final /* synthetic */ Object[] val$rest;
            final /* synthetic */ Object val$first;
            final /* synthetic */ Object val$second;
            
            Joiner$3() {
                super();
            }
            
            @Override
            public int size() {
                return a3.length + 2;
            }
            
            @Override
            public Object get(final int a1) {
                switch (a1) {
                    case 0: {
                        return a1;
                    }
                    case 1: {
                        return a2;
                    }
                    default: {
                        return a3[a1 - 2];
                    }
                }
            }
        };
    }
    
    Joiner(final Joiner a1, final Joiner$1 a2) {
        this(a1);
    }
    
    static /* synthetic */ String access$100(final Joiner a1) {
        return a1.separator;
    }
    
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
                    ((Appendable)v2).append(this.joiner.separator);
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
}
