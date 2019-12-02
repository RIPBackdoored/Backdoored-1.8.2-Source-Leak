package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import javax.annotation.*;
import com.google.errorprone.annotations.*;

@GwtCompatible
public final class Preconditions
{
    private Preconditions() {
        super();
    }
    
    public static void checkArgument(final boolean a1) {
        if (!a1) {
            throw new IllegalArgumentException();
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final Object a2) {
        if (!a1) {
            throw new IllegalArgumentException(String.valueOf(a2));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, @Nullable final Object... a3) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final char a3) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final int a3) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final long a3) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, @Nullable final Object a3) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final char a3, final char a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final char a3, final int a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final char a3, final long a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final char a3, @Nullable final Object a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final int a3, final char a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final int a3, final int a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final int a3, final long a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final int a3, @Nullable final Object a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final long a3, final char a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final long a3, final int a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final long a3, final long a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, final long a3, @Nullable final Object a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, @Nullable final Object a3, final char a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, @Nullable final Object a3, final int a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, @Nullable final Object a3, final long a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, @Nullable final Object a3, @Nullable final Object a4) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, @Nullable final Object a3, @Nullable final Object a4, @Nullable final Object a5) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4, a5));
        }
    }
    
    public static void checkArgument(final boolean a1, @Nullable final String a2, @Nullable final Object a3, @Nullable final Object a4, @Nullable final Object a5, @Nullable final Object a6) {
        if (!a1) {
            throw new IllegalArgumentException(format(a2, a3, a4, a5, a6));
        }
    }
    
    public static void checkState(final boolean a1) {
        if (!a1) {
            throw new IllegalStateException();
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final Object a2) {
        if (!a1) {
            throw new IllegalStateException(String.valueOf(a2));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, @Nullable final Object... a3) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final char a3) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final int a3) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final long a3) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, @Nullable final Object a3) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final char a3, final char a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final char a3, final int a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final char a3, final long a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final char a3, @Nullable final Object a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final int a3, final char a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final int a3, final int a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final int a3, final long a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final int a3, @Nullable final Object a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final long a3, final char a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final long a3, final int a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final long a3, final long a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, final long a3, @Nullable final Object a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, @Nullable final Object a3, final char a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, @Nullable final Object a3, final int a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, @Nullable final Object a3, final long a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, @Nullable final Object a3, @Nullable final Object a4) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, @Nullable final Object a3, @Nullable final Object a4, @Nullable final Object a5) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4, a5));
        }
    }
    
    public static void checkState(final boolean a1, @Nullable final String a2, @Nullable final Object a3, @Nullable final Object a4, @Nullable final Object a5, @Nullable final Object a6) {
        if (!a1) {
            throw new IllegalStateException(format(a2, a3, a4, a5, a6));
        }
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1) {
        if (a1 == null) {
            throw new NullPointerException();
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final Object a2) {
        if (a1 == null) {
            throw new NullPointerException(String.valueOf(a2));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, @Nullable final Object... a3) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final char a3) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final int a3) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final long a3) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, @Nullable final Object a3) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final char a3, final char a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final char a3, final int a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final char a3, final long a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final char a3, @Nullable final Object a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final int a3, final char a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final int a3, final int a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final int a3, final long a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final int a3, @Nullable final Object a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final long a3, final char a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final long a3, final int a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final long a3, final long a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, final long a3, @Nullable final Object a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, @Nullable final Object a3, final char a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, @Nullable final Object a3, final int a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, @Nullable final Object a3, final long a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, @Nullable final Object a3, @Nullable final Object a4) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, @Nullable final Object a3, @Nullable final Object a4, @Nullable final Object a5) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4, a5));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(final T a1, @Nullable final String a2, @Nullable final Object a3, @Nullable final Object a4, @Nullable final Object a5, @Nullable final Object a6) {
        if (a1 == null) {
            throw new NullPointerException(format(a2, a3, a4, a5, a6));
        }
        return a1;
    }
    
    @CanIgnoreReturnValue
    public static int checkElementIndex(final int a1, final int a2) {
        return checkElementIndex(a1, a2, "index");
    }
    
    @CanIgnoreReturnValue
    public static int checkElementIndex(final int a1, final int a2, @Nullable final String a3) {
        if (a1 < 0 || a1 >= a2) {
            throw new IndexOutOfBoundsException(badElementIndex(a1, a2, a3));
        }
        return a1;
    }
    
    private static String badElementIndex(final int a1, final int a2, final String a3) {
        if (a1 < 0) {
            return format("%s (%s) must not be negative", a3, a1);
        }
        if (a2 < 0) {
            throw new IllegalArgumentException("negative size: " + a2);
        }
        return format("%s (%s) must be less than size (%s)", a3, a1, a2);
    }
    
    @CanIgnoreReturnValue
    public static int checkPositionIndex(final int a1, final int a2) {
        return checkPositionIndex(a1, a2, "index");
    }
    
    @CanIgnoreReturnValue
    public static int checkPositionIndex(final int a1, final int a2, @Nullable final String a3) {
        if (a1 < 0 || a1 > a2) {
            throw new IndexOutOfBoundsException(badPositionIndex(a1, a2, a3));
        }
        return a1;
    }
    
    private static String badPositionIndex(final int a1, final int a2, final String a3) {
        if (a1 < 0) {
            return format("%s (%s) must not be negative", a3, a1);
        }
        if (a2 < 0) {
            throw new IllegalArgumentException("negative size: " + a2);
        }
        return format("%s (%s) must not be greater than size (%s)", a3, a1, a2);
    }
    
    public static void checkPositionIndexes(final int a1, final int a2, final int a3) {
        if (a1 < 0 || a2 < a1 || a2 > a3) {
            throw new IndexOutOfBoundsException(badPositionIndexes(a1, a2, a3));
        }
    }
    
    private static String badPositionIndexes(final int a1, final int a2, final int a3) {
        if (a1 < 0 || a1 > a3) {
            return badPositionIndex(a1, a3, "start index");
        }
        if (a2 < 0 || a2 > a3) {
            return badPositionIndex(a2, a3, "end index");
        }
        return format("end index (%s) must not be less than start index (%s)", a2, a1);
    }
    
    static String format(String a2, @Nullable final Object... v1) {
        a2 = String.valueOf(a2);
        final StringBuilder v2 = new StringBuilder(a2.length() + 16 * v1.length);
        int v3 = 0;
        int v4 = 0;
        while (v4 < v1.length) {
            final int a3 = a2.indexOf("%s", v3);
            if (a3 == -1) {
                break;
            }
            v2.append(a2, v3, a3);
            v2.append(v1[v4++]);
            v3 = a3 + 2;
        }
        v2.append(a2, v3, a2.length());
        if (v4 < v1.length) {
            v2.append(" [");
            v2.append(v1[v4++]);
            while (v4 < v1.length) {
                v2.append(", ");
                v2.append(v1[v4++]);
            }
            v2.append(']');
        }
        return v2.toString();
    }
}
