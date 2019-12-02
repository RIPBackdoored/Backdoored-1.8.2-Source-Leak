package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import com.google.errorprone.annotations.concurrent.*;
import javax.annotation.*;
import com.google.errorprone.annotations.*;
import java.util.*;
import java.io.*;

@GwtCompatible
public abstract class Converter<A, B> implements Function<A, B>
{
    private final boolean handleNullAutomatically;
    @LazyInit
    private transient Converter<B, A> reverse;
    
    protected Converter() {
        this(true);
    }
    
    Converter(final boolean a1) {
        super();
        this.handleNullAutomatically = a1;
    }
    
    protected abstract B doForward(final A p0);
    
    protected abstract A doBackward(final B p0);
    
    @Nullable
    @CanIgnoreReturnValue
    public final B convert(@Nullable final A a1) {
        return this.correctedDoForward(a1);
    }
    
    @Nullable
    B correctedDoForward(@Nullable final A a1) {
        if (this.handleNullAutomatically) {
            return (a1 == null) ? null : Preconditions.checkNotNull(this.doForward(a1));
        }
        return this.doForward(a1);
    }
    
    @Nullable
    A correctedDoBackward(@Nullable final B a1) {
        if (this.handleNullAutomatically) {
            return (a1 == null) ? null : Preconditions.checkNotNull(this.doBackward(a1));
        }
        return this.doBackward(a1);
    }
    
    @CanIgnoreReturnValue
    public Iterable<B> convertAll(final Iterable<? extends A> a1) {
        Preconditions.checkNotNull(a1, (Object)"fromIterable");
        return new Iterable<B>() {
            final /* synthetic */ Iterable val$fromIterable;
            final /* synthetic */ Converter this$0;
            
            Converter$1() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public Iterator<B> iterator() {
                return new Iterator<B>() {
                    private final Iterator<? extends A> fromIterator = a1.iterator();
                    final /* synthetic */ Converter$1 this$1;
                    
                    Converter$1$1() {
                        this.this$1 = this$1;
                        super();
                    }
                    
                    @Override
                    public boolean hasNext() {
                        return this.fromIterator.hasNext();
                    }
                    
                    @Override
                    public B next() {
                        return this.this$1.this$0.convert(this.fromIterator.next());
                    }
                    
                    @Override
                    public void remove() {
                        this.fromIterator.remove();
                    }
                };
            }
        };
    }
    
    @CanIgnoreReturnValue
    public Converter<B, A> reverse() {
        final Converter<B, A> v1 = this.reverse;
        return (v1 == null) ? (this.reverse = (Converter<B, A>)new ReverseConverter((Converter<Object, Object>)this)) : v1;
    }
    
    public final <C> Converter<A, C> andThen(final Converter<B, C> a1) {
        return (Converter<A, C>)this.doAndThen((Converter<B, Object>)a1);
    }
    
     <C> Converter<A, C> doAndThen(final Converter<B, C> a1) {
        return (Converter<A, C>)new ConverterComposition((Converter<Object, Object>)this, (Converter<Object, Object>)Preconditions.checkNotNull(a1));
    }
    
    @Deprecated
    @Nullable
    @CanIgnoreReturnValue
    @Override
    public final B apply(@Nullable final A a1) {
        return this.convert(a1);
    }
    
    @Override
    public boolean equals(@Nullable final Object a1) {
        return super.equals(a1);
    }
    
    public static <A, B> Converter<A, B> from(final Function<? super A, ? extends B> a1, final Function<? super B, ? extends A> a2) {
        return new FunctionBasedConverter<A, B>((Function)a1, (Function)a2);
    }
    
    public static <T> Converter<T, T> identity() {
        return (Converter<T, T>)IdentityConverter.INSTANCE;
    }
    
    private static final class ReverseConverter<A, B> extends Converter<B, A> implements Serializable
    {
        final Converter<A, B> original;
        private static final long serialVersionUID = 0L;
        
        ReverseConverter(final Converter<A, B> a1) {
            super();
            this.original = a1;
        }
        
        @Override
        protected A doForward(final B a1) {
            throw new AssertionError();
        }
        
        @Override
        protected B doBackward(final A a1) {
            throw new AssertionError();
        }
        
        @Nullable
        @Override
        A correctedDoForward(@Nullable final B a1) {
            return this.original.correctedDoBackward(a1);
        }
        
        @Nullable
        @Override
        B correctedDoBackward(@Nullable final A a1) {
            return this.original.correctedDoForward(a1);
        }
        
        @Override
        public Converter<A, B> reverse() {
            return this.original;
        }
        
        @Override
        public boolean equals(@Nullable final Object v2) {
            if (v2 instanceof ReverseConverter) {
                final ReverseConverter<?, ?> a1 = (ReverseConverter<?, ?>)v2;
                return this.original.equals(a1.original);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return ~this.original.hashCode();
        }
        
        @Override
        public String toString() {
            return this.original + ".reverse()";
        }
    }
    
    private static final class ConverterComposition<A, B, C> extends Converter<A, C> implements Serializable
    {
        final Converter<A, B> first;
        final Converter<B, C> second;
        private static final long serialVersionUID = 0L;
        
        ConverterComposition(final Converter<A, B> a1, final Converter<B, C> a2) {
            super();
            this.first = a1;
            this.second = a2;
        }
        
        @Override
        protected C doForward(final A a1) {
            throw new AssertionError();
        }
        
        @Override
        protected A doBackward(final C a1) {
            throw new AssertionError();
        }
        
        @Nullable
        @Override
        C correctedDoForward(@Nullable final A a1) {
            return this.second.correctedDoForward(this.first.correctedDoForward(a1));
        }
        
        @Nullable
        @Override
        A correctedDoBackward(@Nullable final C a1) {
            return this.first.correctedDoBackward(this.second.correctedDoBackward(a1));
        }
        
        @Override
        public boolean equals(@Nullable final Object v2) {
            if (v2 instanceof ConverterComposition) {
                final ConverterComposition<?, ?, ?> a1 = (ConverterComposition<?, ?, ?>)v2;
                return this.first.equals(a1.first) && this.second.equals(a1.second);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return 31 * this.first.hashCode() + this.second.hashCode();
        }
        
        @Override
        public String toString() {
            return this.first + ".andThen(" + this.second + ")";
        }
    }
    
    private static final class FunctionBasedConverter<A, B> extends Converter<A, B> implements Serializable
    {
        private final Function<? super A, ? extends B> forwardFunction;
        private final Function<? super B, ? extends A> backwardFunction;
        
        private FunctionBasedConverter(final Function<? super A, ? extends B> a1, final Function<? super B, ? extends A> a2) {
            super();
            this.forwardFunction = Preconditions.checkNotNull(a1);
            this.backwardFunction = Preconditions.checkNotNull(a2);
        }
        
        @Override
        protected B doForward(final A a1) {
            return (B)this.forwardFunction.apply((Object)a1);
        }
        
        @Override
        protected A doBackward(final B a1) {
            return (A)this.backwardFunction.apply((Object)a1);
        }
        
        @Override
        public boolean equals(@Nullable final Object v2) {
            if (v2 instanceof FunctionBasedConverter) {
                final FunctionBasedConverter<?, ?> a1 = (FunctionBasedConverter<?, ?>)v2;
                return this.forwardFunction.equals(a1.forwardFunction) && this.backwardFunction.equals(a1.backwardFunction);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.forwardFunction.hashCode() * 31 + this.backwardFunction.hashCode();
        }
        
        @Override
        public String toString() {
            return "Converter.from(" + this.forwardFunction + ", " + this.backwardFunction + ")";
        }
        
        FunctionBasedConverter(final Function a1, final Function a2, final Converter$1 a3) {
            this(a1, a2);
        }
    }
    
    private static final class IdentityConverter<T> extends Converter<T, T> implements Serializable
    {
        static final IdentityConverter INSTANCE;
        private static final long serialVersionUID = 0L;
        
        private IdentityConverter() {
            super();
        }
        
        @Override
        protected T doForward(final T a1) {
            return a1;
        }
        
        @Override
        protected T doBackward(final T a1) {
            return a1;
        }
        
        @Override
        public IdentityConverter<T> reverse() {
            return this;
        }
        
        @Override
         <S> Converter<T, S> doAndThen(final Converter<T, S> a1) {
            return Preconditions.checkNotNull(a1, (Object)"otherConverter");
        }
        
        @Override
        public String toString() {
            return "Converter.identity()";
        }
        
        private Object readResolve() {
            return IdentityConverter.INSTANCE;
        }
        
        @Override
        public /* bridge */ Converter reverse() {
            return this.reverse();
        }
        
        static {
            INSTANCE = new IdentityConverter();
        }
    }
}
