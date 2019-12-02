package com.google.api.client.repackaged.com.google.common.base;

import javax.annotation.*;
import com.google.errorprone.annotations.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

@GwtCompatible(emulated = true)
public final class Throwables
{
    @GwtIncompatible
    private static final String JAVA_LANG_ACCESS_CLASSNAME = "sun.misc.JavaLangAccess";
    @GwtIncompatible
    @VisibleForTesting
    static final String SHARED_SECRETS_CLASSNAME = "sun.misc.SharedSecrets";
    @Nullable
    @GwtIncompatible
    private static final Object jla;
    @Nullable
    @GwtIncompatible
    private static final Method getStackTraceElementMethod;
    @Nullable
    @GwtIncompatible
    private static final Method getStackTraceDepthMethod;
    
    private Throwables() {
        super();
    }
    
    @GwtIncompatible
    public static <X extends Throwable> void throwIfInstanceOf(final Throwable a1, final Class<X> a2) throws X, Throwable {
        Preconditions.checkNotNull(a1);
        if (a2.isInstance(a1)) {
            throw a2.cast(a1);
        }
    }
    
    @Deprecated
    @GwtIncompatible
    public static <X extends Throwable> void propagateIfInstanceOf(@Nullable final Throwable a1, final Class<X> a2) throws X, Throwable {
        if (a1 != null) {
            throwIfInstanceOf(a1, (Class<Throwable>)a2);
        }
    }
    
    public static void throwIfUnchecked(final Throwable a1) {
        Preconditions.checkNotNull(a1);
        if (a1 instanceof RuntimeException) {
            throw (RuntimeException)a1;
        }
        if (a1 instanceof Error) {
            throw (Error)a1;
        }
    }
    
    @Deprecated
    @GwtIncompatible
    public static void propagateIfPossible(@Nullable final Throwable a1) {
        if (a1 != null) {
            throwIfUnchecked(a1);
        }
    }
    
    @GwtIncompatible
    public static <X extends Throwable> void propagateIfPossible(@Nullable final Throwable a1, final Class<X> a2) throws X, Throwable {
        propagateIfInstanceOf(a1, (Class<Throwable>)a2);
        propagateIfPossible(a1);
    }
    
    @GwtIncompatible
    public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(@Nullable final Throwable a1, final Class<X1> a2, final Class<X2> a3) throws X1, X2, Throwable {
        Preconditions.checkNotNull(a3);
        propagateIfInstanceOf(a1, a2);
        propagateIfPossible(a1, a3);
    }
    
    @Deprecated
    @CanIgnoreReturnValue
    @GwtIncompatible
    public static RuntimeException propagate(final Throwable a1) {
        throwIfUnchecked(a1);
        throw new RuntimeException(a1);
    }
    
    public static Throwable getRootCause(Throwable a1) {
        Throwable v1;
        while ((v1 = a1.getCause()) != null) {
            a1 = v1;
        }
        return a1;
    }
    
    @Beta
    public static List<Throwable> getCausalChain(Throwable a1) {
        Preconditions.checkNotNull(a1);
        final List<Throwable> v1 = new ArrayList<Throwable>(4);
        while (a1 != null) {
            v1.add(a1);
            a1 = a1.getCause();
        }
        return Collections.unmodifiableList((List<? extends Throwable>)v1);
    }
    
    @GwtIncompatible
    public static String getStackTraceAsString(final Throwable a1) {
        final StringWriter v1 = new StringWriter();
        a1.printStackTrace(new PrintWriter(v1));
        return v1.toString();
    }
    
    @Beta
    @GwtIncompatible
    public static List<StackTraceElement> lazyStackTrace(final Throwable a1) {
        return lazyStackTraceIsLazy() ? jlaStackTrace(a1) : Collections.unmodifiableList((List<? extends StackTraceElement>)Arrays.asList((T[])a1.getStackTrace()));
    }
    
    @Beta
    @GwtIncompatible
    public static boolean lazyStackTraceIsLazy() {
        return Throwables.getStackTraceElementMethod != null & Throwables.getStackTraceDepthMethod != null;
    }
    
    @GwtIncompatible
    private static List<StackTraceElement> jlaStackTrace(final Throwable a1) {
        Preconditions.checkNotNull(a1);
        return new AbstractList<StackTraceElement>() {
            final /* synthetic */ Throwable val$t;
            
            Throwables$1() {
                super();
            }
            
            @Override
            public StackTraceElement get(final int a1) {
                return (StackTraceElement)invokeAccessibleNonThrowingMethod(Throwables.getStackTraceElementMethod, Throwables.jla, new Object[] { a1, a1 });
            }
            
            @Override
            public int size() {
                return (int)invokeAccessibleNonThrowingMethod(Throwables.getStackTraceDepthMethod, Throwables.jla, new Object[] { a1 });
            }
            
            @Override
            public /* bridge */ Object get(final int a1) {
                return this.get(a1);
            }
        };
    }
    
    @GwtIncompatible
    private static Object invokeAccessibleNonThrowingMethod(final Method a3, final Object v1, final Object... v2) {
        try {
            return a3.invoke(v1, v2);
        }
        catch (IllegalAccessException a4) {
            throw new RuntimeException(a4);
        }
        catch (InvocationTargetException a5) {
            throw propagate(a5.getCause());
        }
    }
    
    @Nullable
    @GwtIncompatible
    private static Object getJLA() {
        try {
            final Class<?> v1 = Class.forName("sun.misc.SharedSecrets", false, null);
            final Method v2 = v1.getMethod("getJavaLangAccess", (Class<?>[])new Class[0]);
            return v2.invoke(null, new Object[0]);
        }
        catch (ThreadDeath v3) {
            throw v3;
        }
        catch (Throwable v4) {
            return null;
        }
    }
    
    @Nullable
    @GwtIncompatible
    private static Method getGetMethod() {
        return getJlaMethod("getStackTraceElement", Throwable.class, Integer.TYPE);
    }
    
    @Nullable
    @GwtIncompatible
    private static Method getSizeMethod() {
        return getJlaMethod("getStackTraceDepth", Throwable.class);
    }
    
    @Nullable
    @GwtIncompatible
    private static Method getJlaMethod(final String v1, final Class<?>... v2) throws ThreadDeath {
        try {
            return Class.forName("sun.misc.JavaLangAccess", false, null).getMethod(v1, v2);
        }
        catch (ThreadDeath a1) {
            throw a1;
        }
        catch (Throwable a2) {
            return null;
        }
    }
    
    static /* synthetic */ Method access$000() {
        return Throwables.getStackTraceElementMethod;
    }
    
    static /* synthetic */ Object access$100() {
        return Throwables.jla;
    }
    
    static /* bridge */ Object access$200(final Method a1, final Object a2, final Object[] a3) {
        return invokeAccessibleNonThrowingMethod(a1, a2, a3);
    }
    
    static /* synthetic */ Method access$300() {
        return Throwables.getStackTraceDepthMethod;
    }
    
    static {
        jla = getJLA();
        getStackTraceElementMethod = ((Throwables.jla == null) ? null : getGetMethod());
        getStackTraceDepthMethod = ((Throwables.jla == null) ? null : getSizeMethod());
    }
}
