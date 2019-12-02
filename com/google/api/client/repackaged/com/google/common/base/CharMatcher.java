package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

@GwtCompatible(emulated = true)
public abstract class CharMatcher implements Predicate<Character>
{
    @Deprecated
    public static final CharMatcher WHITESPACE;
    @Deprecated
    public static final CharMatcher BREAKING_WHITESPACE;
    @Deprecated
    public static final CharMatcher ASCII;
    @Deprecated
    public static final CharMatcher DIGIT;
    @Deprecated
    public static final CharMatcher JAVA_DIGIT;
    @Deprecated
    public static final CharMatcher JAVA_LETTER;
    @Deprecated
    public static final CharMatcher JAVA_LETTER_OR_DIGIT;
    @Deprecated
    public static final CharMatcher JAVA_UPPER_CASE;
    @Deprecated
    public static final CharMatcher JAVA_LOWER_CASE;
    @Deprecated
    public static final CharMatcher JAVA_ISO_CONTROL;
    @Deprecated
    public static final CharMatcher INVISIBLE;
    @Deprecated
    public static final CharMatcher SINGLE_WIDTH;
    @Deprecated
    public static final CharMatcher ANY;
    @Deprecated
    public static final CharMatcher NONE;
    private static final int DISTINCT_CHARS = 65536;
    
    public static CharMatcher any() {
        return Any.INSTANCE;
    }
    
    public static CharMatcher none() {
        return None.INSTANCE;
    }
    
    public static CharMatcher whitespace() {
        return Whitespace.INSTANCE;
    }
    
    public static CharMatcher breakingWhitespace() {
        return BreakingWhitespace.INSTANCE;
    }
    
    public static CharMatcher ascii() {
        return Ascii.INSTANCE;
    }
    
    public static CharMatcher digit() {
        return Digit.INSTANCE;
    }
    
    public static CharMatcher javaDigit() {
        return JavaDigit.INSTANCE;
    }
    
    public static CharMatcher javaLetter() {
        return JavaLetter.INSTANCE;
    }
    
    public static CharMatcher javaLetterOrDigit() {
        return JavaLetterOrDigit.INSTANCE;
    }
    
    public static CharMatcher javaUpperCase() {
        return JavaUpperCase.INSTANCE;
    }
    
    public static CharMatcher javaLowerCase() {
        return JavaLowerCase.INSTANCE;
    }
    
    public static CharMatcher javaIsoControl() {
        return JavaIsoControl.INSTANCE;
    }
    
    public static CharMatcher invisible() {
        return Invisible.INSTANCE;
    }
    
    public static CharMatcher singleWidth() {
        return SingleWidth.INSTANCE;
    }
    
    public static CharMatcher is(final char a1) {
        return new Is(a1);
    }
    
    public static CharMatcher isNot(final char a1) {
        return new IsNot(a1);
    }
    
    public static CharMatcher anyOf(final CharSequence a1) {
        switch (a1.length()) {
            case 0: {
                return none();
            }
            case 1: {
                return is(a1.charAt(0));
            }
            case 2: {
                return isEither(a1.charAt(0), a1.charAt(1));
            }
            default: {
                return new AnyOf(a1);
            }
        }
    }
    
    public static CharMatcher noneOf(final CharSequence a1) {
        return anyOf(a1).negate();
    }
    
    public static CharMatcher inRange(final char a1, final char a2) {
        return new InRange(a1, a2);
    }
    
    public static CharMatcher forPredicate(final Predicate<? super Character> a1) {
        return (a1 instanceof CharMatcher) ? ((CharMatcher)a1) : new ForPredicate(a1);
    }
    
    protected CharMatcher() {
        super();
    }
    
    public abstract boolean matches(final char p0);
    
    public CharMatcher negate() {
        return new Negated(this);
    }
    
    public CharMatcher and(final CharMatcher a1) {
        return new And(this, a1);
    }
    
    public CharMatcher or(final CharMatcher a1) {
        return new Or(this, a1);
    }
    
    public CharMatcher precomputed() {
        return Platform.precomputeCharMatcher(this);
    }
    
    @GwtIncompatible
    CharMatcher precomputedInternal() {
        final BitSet v6 = new BitSet();
        this.setBits(v6);
        final int v0 = v6.cardinality();
        if (v0 * 2 <= 65536) {
            return precomputedPositive(v0, v6, this.toString());
        }
        v6.flip(0, 65536);
        final int v2 = 65536 - v0;
        final String v3 = ".negate()";
        final String v4 = this.toString();
        final String v5 = v4.endsWith(v3) ? v4.substring(0, v4.length() - v3.length()) : (v4 + v3);
        return new NegatedFastMatcher(precomputedPositive(v2, v6, v5)) {
            final /* synthetic */ String val$description;
            final /* synthetic */ CharMatcher this$0;
            
            CharMatcher$1(final CharMatcher a1) {
                this.this$0 = this$0;
                super(a1);
            }
            
            @Override
            public String toString() {
                return v4;
            }
        };
    }
    
    @GwtIncompatible
    private static CharMatcher precomputedPositive(final int a3, final BitSet v1, final String v2) {
        switch (a3) {
            case 0: {
                return none();
            }
            case 1: {
                return is((char)v1.nextSetBit(0));
            }
            case 2: {
                final char a4 = (char)v1.nextSetBit(0);
                final char a5 = (char)v1.nextSetBit(a4 + '\u0001');
                return isEither(a4, a5);
            }
            default: {
                return isSmall(a3, v1.length()) ? SmallCharMatcher.from(v1, v2) : new BitSetMatcher(v1, v2);
            }
        }
    }
    
    @GwtIncompatible
    private static boolean isSmall(final int a1, final int a2) {
        return a1 <= 1023 && a2 > a1 * 4 * 16;
    }
    
    @GwtIncompatible
    void setBits(final BitSet v2) {
        for (int a1 = 65535; a1 >= 0; --a1) {
            if (this.matches((char)a1)) {
                v2.set(a1);
            }
        }
    }
    
    public boolean matchesAnyOf(final CharSequence a1) {
        return !this.matchesNoneOf(a1);
    }
    
    public boolean matchesAllOf(final CharSequence v2) {
        for (int a1 = v2.length() - 1; a1 >= 0; --a1) {
            if (!this.matches(v2.charAt(a1))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean matchesNoneOf(final CharSequence a1) {
        return this.indexIn(a1) == -1;
    }
    
    public int indexIn(final CharSequence a1) {
        return this.indexIn(a1, 0);
    }
    
    public int indexIn(final CharSequence v1, final int v2) {
        final int v3 = v1.length();
        Preconditions.checkPositionIndex(v2, v3);
        for (int a1 = v2; a1 < v3; ++a1) {
            if (this.matches(v1.charAt(a1))) {
                return a1;
            }
        }
        return -1;
    }
    
    public int lastIndexIn(final CharSequence v2) {
        for (int a1 = v2.length() - 1; a1 >= 0; --a1) {
            if (this.matches(v2.charAt(a1))) {
                return a1;
            }
        }
        return -1;
    }
    
    public int countIn(final CharSequence v2) {
        int v3 = 0;
        for (int a1 = 0; a1 < v2.length(); ++a1) {
            if (this.matches(v2.charAt(a1))) {
                ++v3;
            }
        }
        return v3;
    }
    
    public String removeFrom(final CharSequence a1) {
        final String v1 = a1.toString();
        int v2 = this.indexIn(v1);
        if (v2 == -1) {
            return v1;
        }
        final char[] v3 = v1.toCharArray();
        int v4 = 1;
    Label_0029:
        while (true) {
            ++v2;
            while (v2 != v3.length) {
                if (this.matches(v3[v2])) {
                    ++v4;
                    continue Label_0029;
                }
                v3[v2 - v4] = v3[v2];
                ++v2;
            }
            break;
        }
        return new String(v3, 0, v2 - v4);
    }
    
    public String retainFrom(final CharSequence a1) {
        return this.negate().removeFrom(a1);
    }
    
    public String replaceFrom(final CharSequence v1, final char v2) {
        final String v3 = v1.toString();
        final int v4 = this.indexIn(v3);
        if (v4 == -1) {
            return v3;
        }
        final char[] v5 = v3.toCharArray();
        v5[v4] = v2;
        for (int a1 = v4 + 1; a1 < v5.length; ++a1) {
            if (this.matches(v5[a1])) {
                v5[a1] = v2;
            }
        }
        return new String(v5);
    }
    
    public String replaceFrom(final CharSequence a1, final CharSequence a2) {
        final int v1 = a2.length();
        if (v1 == 0) {
            return this.removeFrom(a1);
        }
        if (v1 == 1) {
            return this.replaceFrom(a1, a2.charAt(0));
        }
        final String v2 = a1.toString();
        int v3 = this.indexIn(v2);
        if (v3 == -1) {
            return v2;
        }
        final int v4 = v2.length();
        final StringBuilder v5 = new StringBuilder(v4 * 3 / 2 + 16);
        int v6 = 0;
        do {
            v5.append(v2, v6, v3);
            v5.append(a2);
            v6 = v3 + 1;
            v3 = this.indexIn(v2, v6);
        } while (v3 != -1);
        v5.append(v2, v6, v4);
        return v5.toString();
    }
    
    public String trimFrom(final CharSequence a1) {
        int v1;
        int v2;
        for (v1 = a1.length(), v2 = 0; v2 < v1 && this.matches(a1.charAt(v2)); ++v2) {}
        int v3;
        for (v3 = v1 - 1; v3 > v2 && this.matches(a1.charAt(v3)); --v3) {}
        return a1.subSequence(v2, v3 + 1).toString();
    }
    
    public String trimLeadingFrom(final CharSequence v2) {
        for (int v3 = v2.length(), a1 = 0; a1 < v3; ++a1) {
            if (!this.matches(v2.charAt(a1))) {
                return v2.subSequence(a1, v3).toString();
            }
        }
        return "";
    }
    
    public String trimTrailingFrom(final CharSequence v2) {
        final int v3 = v2.length();
        for (int a1 = v3 - 1; a1 >= 0; --a1) {
            if (!this.matches(v2.charAt(a1))) {
                return v2.subSequence(0, a1 + 1).toString();
            }
        }
        return "";
    }
    
    public String collapseFrom(final CharSequence v-2, final char v-1) {
        for (int v0 = v-2.length(), v2 = 0; v2 < v0; ++v2) {
            final char a2 = v-2.charAt(v2);
            if (this.matches(a2)) {
                if (a2 != v-1 || (v2 != v0 - 1 && this.matches(v-2.charAt(v2 + 1)))) {
                    final StringBuilder a3 = new StringBuilder(v0).append(v-2, 0, v2).append(v-1);
                    return this.finishCollapseFrom(v-2, v2 + 1, v0, v-1, a3, true);
                }
                ++v2;
            }
        }
        return v-2.toString();
    }
    
    public String trimAndCollapseFrom(final CharSequence a1, final char a2) {
        final int v1 = a1.length();
        int v2 = 0;
        int v3 = v1 - 1;
        while (v2 < v1 && this.matches(a1.charAt(v2))) {
            ++v2;
        }
        while (v3 > v2 && this.matches(a1.charAt(v3))) {
            --v3;
        }
        return (v2 == 0 && v3 == v1 - 1) ? this.collapseFrom(a1, a2) : this.finishCollapseFrom(a1, v2, v3 + 1, a2, new StringBuilder(v3 + 1 - v2), false);
    }
    
    private String finishCollapseFrom(final CharSequence a4, final int a5, final int a6, final char v1, final StringBuilder v2, boolean v3) {
        for (int a7 = a5; a7 < a6; ++a7) {
            final char a8 = a4.charAt(a7);
            if (this.matches(a8)) {
                if (!v3) {
                    v2.append(v1);
                    v3 = true;
                }
            }
            else {
                v2.append(a8);
                v3 = false;
            }
        }
        return v2.toString();
    }
    
    @Deprecated
    @Override
    public boolean apply(final Character a1) {
        return this.matches(a1);
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    private static String showCharacter(char v1) {
        final String v2 = "0123456789ABCDEF";
        final char[] v3 = { '\\', 'u', '\0', '\0', '\0', '\0' };
        for (int a1 = 0; a1 < 4; ++a1) {
            v3[5 - a1] = v2.charAt(v1 & '\u000f');
            v1 >>= 4;
        }
        return String.copyValueOf(v3);
    }
    
    private static IsEither isEither(final char a1, final char a2) {
        return new IsEither(a1, a2);
    }
    
    @Override
    public /* bridge */ boolean apply(final Object a1) {
        return this.apply((Character)a1);
    }
    
    static /* bridge */ String access$100(final char a1) {
        return showCharacter(a1);
    }
    
    static {
        WHITESPACE = whitespace();
        BREAKING_WHITESPACE = breakingWhitespace();
        ASCII = ascii();
        DIGIT = digit();
        JAVA_DIGIT = javaDigit();
        JAVA_LETTER = javaLetter();
        JAVA_LETTER_OR_DIGIT = javaLetterOrDigit();
        JAVA_UPPER_CASE = javaUpperCase();
        JAVA_LOWER_CASE = javaLowerCase();
        JAVA_ISO_CONTROL = javaIsoControl();
        INVISIBLE = invisible();
        SINGLE_WIDTH = singleWidth();
        ANY = any();
        NONE = none();
    }
    
    abstract static class FastMatcher extends CharMatcher
    {
        FastMatcher() {
            super();
        }
        
        @Override
        public final CharMatcher precomputed() {
            return this;
        }
        
        @Override
        public CharMatcher negate() {
            return new NegatedFastMatcher(this);
        }
        
        @Override
        public /* bridge */ boolean apply(final Object a1) {
            return super.apply((Character)a1);
        }
    }
    
    abstract static class NamedFastMatcher extends FastMatcher
    {
        private final String description;
        
        NamedFastMatcher(final String a1) {
            super();
            this.description = Preconditions.checkNotNull(a1);
        }
        
        @Override
        public final String toString() {
            return this.description;
        }
    }
    
    static class NegatedFastMatcher extends Negated
    {
        NegatedFastMatcher(final CharMatcher a1) {
            super(a1);
        }
        
        @Override
        public final CharMatcher precomputed() {
            return this;
        }
    }
    
    @GwtIncompatible
    private static final class BitSetMatcher extends NamedFastMatcher
    {
        private final BitSet table;
        
        private BitSetMatcher(BitSet a1, final String a2) {
            super(a2);
            if (a1.length() + 64 < a1.size()) {
                a1 = (BitSet)a1.clone();
            }
            this.table = a1;
        }
        
        @Override
        public boolean matches(final char a1) {
            return this.table.get(a1);
        }
        
        @Override
        void setBits(final BitSet a1) {
            a1.or(this.table);
        }
        
        BitSetMatcher(final BitSet a1, final String a2, final CharMatcher$1 a3) {
            this(a1, a2);
        }
    }
    
    private static final class Any extends NamedFastMatcher
    {
        static final Any INSTANCE;
        
        private Any() {
            super("CharMatcher.any()");
        }
        
        @Override
        public boolean matches(final char a1) {
            return true;
        }
        
        @Override
        public int indexIn(final CharSequence a1) {
            return (a1.length() == 0) ? -1 : 0;
        }
        
        @Override
        public int indexIn(final CharSequence a1, final int a2) {
            final int v1 = a1.length();
            Preconditions.checkPositionIndex(a2, v1);
            return (a2 == v1) ? -1 : a2;
        }
        
        @Override
        public int lastIndexIn(final CharSequence a1) {
            return a1.length() - 1;
        }
        
        @Override
        public boolean matchesAllOf(final CharSequence a1) {
            Preconditions.checkNotNull(a1);
            return true;
        }
        
        @Override
        public boolean matchesNoneOf(final CharSequence a1) {
            return a1.length() == 0;
        }
        
        @Override
        public String removeFrom(final CharSequence a1) {
            Preconditions.checkNotNull(a1);
            return "";
        }
        
        @Override
        public String replaceFrom(final CharSequence a1, final char a2) {
            final char[] v1 = new char[a1.length()];
            Arrays.fill(v1, a2);
            return new String(v1);
        }
        
        @Override
        public String replaceFrom(final CharSequence v1, final CharSequence v2) {
            final StringBuilder v3 = new StringBuilder(v1.length() * v2.length());
            for (int a1 = 0; a1 < v1.length(); ++a1) {
                v3.append(v2);
            }
            return v3.toString();
        }
        
        @Override
        public String collapseFrom(final CharSequence a1, final char a2) {
            return (a1.length() == 0) ? "" : String.valueOf(a2);
        }
        
        @Override
        public String trimFrom(final CharSequence a1) {
            Preconditions.checkNotNull(a1);
            return "";
        }
        
        @Override
        public int countIn(final CharSequence a1) {
            return a1.length();
        }
        
        @Override
        public CharMatcher and(final CharMatcher a1) {
            return Preconditions.checkNotNull(a1);
        }
        
        @Override
        public CharMatcher or(final CharMatcher a1) {
            Preconditions.checkNotNull(a1);
            return this;
        }
        
        @Override
        public CharMatcher negate() {
            return CharMatcher.none();
        }
        
        static {
            INSTANCE = new Any();
        }
    }
    
    private static final class None extends NamedFastMatcher
    {
        static final None INSTANCE;
        
        private None() {
            super("CharMatcher.none()");
        }
        
        @Override
        public boolean matches(final char a1) {
            return false;
        }
        
        @Override
        public int indexIn(final CharSequence a1) {
            Preconditions.checkNotNull(a1);
            return -1;
        }
        
        @Override
        public int indexIn(final CharSequence a1, final int a2) {
            final int v1 = a1.length();
            Preconditions.checkPositionIndex(a2, v1);
            return -1;
        }
        
        @Override
        public int lastIndexIn(final CharSequence a1) {
            Preconditions.checkNotNull(a1);
            return -1;
        }
        
        @Override
        public boolean matchesAllOf(final CharSequence a1) {
            return a1.length() == 0;
        }
        
        @Override
        public boolean matchesNoneOf(final CharSequence a1) {
            Preconditions.checkNotNull(a1);
            return true;
        }
        
        @Override
        public String removeFrom(final CharSequence a1) {
            return a1.toString();
        }
        
        @Override
        public String replaceFrom(final CharSequence a1, final char a2) {
            return a1.toString();
        }
        
        @Override
        public String replaceFrom(final CharSequence a1, final CharSequence a2) {
            Preconditions.checkNotNull(a2);
            return a1.toString();
        }
        
        @Override
        public String collapseFrom(final CharSequence a1, final char a2) {
            return a1.toString();
        }
        
        @Override
        public String trimFrom(final CharSequence a1) {
            return a1.toString();
        }
        
        @Override
        public String trimLeadingFrom(final CharSequence a1) {
            return a1.toString();
        }
        
        @Override
        public String trimTrailingFrom(final CharSequence a1) {
            return a1.toString();
        }
        
        @Override
        public int countIn(final CharSequence a1) {
            Preconditions.checkNotNull(a1);
            return 0;
        }
        
        @Override
        public CharMatcher and(final CharMatcher a1) {
            Preconditions.checkNotNull(a1);
            return this;
        }
        
        @Override
        public CharMatcher or(final CharMatcher a1) {
            return Preconditions.checkNotNull(a1);
        }
        
        @Override
        public CharMatcher negate() {
            return CharMatcher.any();
        }
        
        static {
            INSTANCE = new None();
        }
    }
    
    @VisibleForTesting
    static final class Whitespace extends NamedFastMatcher
    {
        static final String TABLE = "\u2002\u3000\r\u0085\u200a\u2005\u2000\u3000\u2029\u000b\u3000\u2008\u2003\u205f\u3000\u1680\t \u2006\u2001\u202f \f\u2009\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000";
        static final int MULTIPLIER = 1682554634;
        static final int SHIFT;
        static final Whitespace INSTANCE;
        
        Whitespace() {
            super("CharMatcher.whitespace()");
        }
        
        @Override
        public boolean matches(final char a1) {
            return "\u2002\u3000\r\u0085\u200a\u2005\u2000\u3000\u2029\u000b\u3000\u2008\u2003\u205f\u3000\u1680\t \u2006\u2001\u202f \f\u2009\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000".charAt(1682554634 * a1 >>> Whitespace.SHIFT) == a1;
        }
        
        @GwtIncompatible
        @Override
        void setBits(final BitSet v2) {
            for (int a1 = 0; a1 < "\u2002\u3000\r\u0085\u200a\u2005\u2000\u3000\u2029\u000b\u3000\u2008\u2003\u205f\u3000\u1680\t \u2006\u2001\u202f \f\u2009\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000".length(); ++a1) {
                v2.set("\u2002\u3000\r\u0085\u200a\u2005\u2000\u3000\u2029\u000b\u3000\u2008\u2003\u205f\u3000\u1680\t \u2006\u2001\u202f \f\u2009\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000".charAt(a1));
            }
        }
        
        static {
            SHIFT = Integer.numberOfLeadingZeros("\u2002\u3000\r\u0085\u200a\u2005\u2000\u3000\u2029\u000b\u3000\u2008\u2003\u205f\u3000\u1680\t \u2006\u2001\u202f \f\u2009\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000".length() - 1);
            INSTANCE = new Whitespace();
        }
    }
    
    private static final class BreakingWhitespace extends CharMatcher
    {
        static final CharMatcher INSTANCE;
        
        private BreakingWhitespace() {
            super();
        }
        
        @Override
        public boolean matches(final char a1) {
            switch (a1) {
                case '\t':
                case '\n':
                case '\u000b':
                case '\f':
                case '\r':
                case ' ':
                case '\u0085':
                case '\u1680':
                case '\u2028':
                case '\u2029':
                case '\u205f':
                case '\u3000': {
                    return true;
                }
                case '\u2007': {
                    return false;
                }
                default: {
                    return a1 >= '\u2000' && a1 <= '\u200a';
                }
            }
        }
        
        @Override
        public String toString() {
            return "CharMatcher.breakingWhitespace()";
        }
        
        @Override
        public /* bridge */ boolean apply(final Object a1) {
            return super.apply((Character)a1);
        }
        
        static {
            INSTANCE = new BreakingWhitespace();
        }
    }
    
    private static final class Ascii extends NamedFastMatcher
    {
        static final Ascii INSTANCE;
        
        Ascii() {
            super("CharMatcher.ascii()");
        }
        
        @Override
        public boolean matches(final char a1) {
            return a1 <= '\u007f';
        }
        
        static {
            INSTANCE = new Ascii();
        }
    }
    
    private static class RangesMatcher extends CharMatcher
    {
        private final String description;
        private final char[] rangeStarts;
        private final char[] rangeEnds;
        
        RangesMatcher(final String a3, final char[] v1, final char[] v2) {
            super();
            this.description = a3;
            this.rangeStarts = v1;
            this.rangeEnds = v2;
            Preconditions.checkArgument(v1.length == v2.length);
            for (int a4 = 0; a4 < v1.length; ++a4) {
                Preconditions.checkArgument(v1[a4] <= v2[a4]);
                if (a4 + 1 < v1.length) {
                    Preconditions.checkArgument(v2[a4] < v1[a4 + 1]);
                }
            }
        }
        
        @Override
        public boolean matches(final char a1) {
            int v1 = Arrays.binarySearch(this.rangeStarts, a1);
            if (v1 >= 0) {
                return true;
            }
            v1 = ~v1 - 1;
            return v1 >= 0 && a1 <= this.rangeEnds[v1];
        }
        
        @Override
        public String toString() {
            return this.description;
        }
        
        @Override
        public /* bridge */ boolean apply(final Object a1) {
            return super.apply((Character)a1);
        }
    }
    
    private static final class Digit extends RangesMatcher
    {
        private static final String ZEROES = "0\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10";
        static final Digit INSTANCE;
        
        private static char[] zeroes() {
            return "0\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10".toCharArray();
        }
        
        private static char[] nines() {
            final char[] v0 = new char["0\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10".length()];
            for (int v2 = 0; v2 < "0\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10".length(); ++v2) {
                v0[v2] = (char)("0\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10".charAt(v2) + '\t');
            }
            return v0;
        }
        
        private Digit() {
            super("CharMatcher.digit()", zeroes(), nines());
        }
        
        static {
            INSTANCE = new Digit();
        }
    }
    
    private static final class JavaDigit extends CharMatcher
    {
        static final JavaDigit INSTANCE;
        
        private JavaDigit() {
            super();
        }
        
        @Override
        public boolean matches(final char a1) {
            return Character.isDigit(a1);
        }
        
        @Override
        public String toString() {
            return "CharMatcher.javaDigit()";
        }
        
        @Override
        public /* bridge */ boolean apply(final Object a1) {
            return super.apply((Character)a1);
        }
        
        static {
            INSTANCE = new JavaDigit();
        }
    }
    
    private static final class JavaLetter extends CharMatcher
    {
        static final JavaLetter INSTANCE;
        
        private JavaLetter() {
            super();
        }
        
        @Override
        public boolean matches(final char a1) {
            return Character.isLetter(a1);
        }
        
        @Override
        public String toString() {
            return "CharMatcher.javaLetter()";
        }
        
        @Override
        public /* bridge */ boolean apply(final Object a1) {
            return super.apply((Character)a1);
        }
        
        static {
            INSTANCE = new JavaLetter();
        }
    }
    
    private static final class JavaLetterOrDigit extends CharMatcher
    {
        static final JavaLetterOrDigit INSTANCE;
        
        private JavaLetterOrDigit() {
            super();
        }
        
        @Override
        public boolean matches(final char a1) {
            return Character.isLetterOrDigit(a1);
        }
        
        @Override
        public String toString() {
            return "CharMatcher.javaLetterOrDigit()";
        }
        
        @Override
        public /* bridge */ boolean apply(final Object a1) {
            return super.apply((Character)a1);
        }
        
        static {
            INSTANCE = new JavaLetterOrDigit();
        }
    }
    
    private static final class JavaUpperCase extends CharMatcher
    {
        static final JavaUpperCase INSTANCE;
        
        private JavaUpperCase() {
            super();
        }
        
        @Override
        public boolean matches(final char a1) {
            return Character.isUpperCase(a1);
        }
        
        @Override
        public String toString() {
            return "CharMatcher.javaUpperCase()";
        }
        
        @Override
        public /* bridge */ boolean apply(final Object a1) {
            return super.apply((Character)a1);
        }
        
        static {
            INSTANCE = new JavaUpperCase();
        }
    }
    
    private static final class JavaLowerCase extends CharMatcher
    {
        static final JavaLowerCase INSTANCE;
        
        private JavaLowerCase() {
            super();
        }
        
        @Override
        public boolean matches(final char a1) {
            return Character.isLowerCase(a1);
        }
        
        @Override
        public String toString() {
            return "CharMatcher.javaLowerCase()";
        }
        
        @Override
        public /* bridge */ boolean apply(final Object a1) {
            return super.apply((Character)a1);
        }
        
        static {
            INSTANCE = new JavaLowerCase();
        }
    }
    
    private static final class JavaIsoControl extends NamedFastMatcher
    {
        static final JavaIsoControl INSTANCE;
        
        private JavaIsoControl() {
            super("CharMatcher.javaIsoControl()");
        }
        
        @Override
        public boolean matches(final char a1) {
            return a1 <= '\u001f' || (a1 >= '\u007f' && a1 <= '\u009f');
        }
        
        static {
            INSTANCE = new JavaIsoControl();
        }
    }
    
    private static final class Invisible extends RangesMatcher
    {
        private static final String RANGE_STARTS = "\u0000\u007f\u00ad\u0600\u061c\u06dd\u070f\u1680\u180e\u2000\u2028\u205f\u2066\u2067\u2068\u2069\u206a\u3000\ud800\ufeff\ufff9\ufffa";
        private static final String RANGE_ENDS = "  \u00ad\u0604\u061c\u06dd\u070f\u1680\u180e\u200f\u202f\u2064\u2066\u2067\u2068\u2069\u206f\u3000\uf8ff\ufeff\ufff9\ufffb";
        static final Invisible INSTANCE;
        
        private Invisible() {
            super("CharMatcher.invisible()", "\u0000\u007f\u00ad\u0600\u061c\u06dd\u070f\u1680\u180e\u2000\u2028\u205f\u2066\u2067\u2068\u2069\u206a\u3000\ud800\ufeff\ufff9\ufffa".toCharArray(), "  \u00ad\u0604\u061c\u06dd\u070f\u1680\u180e\u200f\u202f\u2064\u2066\u2067\u2068\u2069\u206f\u3000\uf8ff\ufeff\ufff9\ufffb".toCharArray());
        }
        
        static {
            INSTANCE = new Invisible();
        }
    }
    
    private static final class SingleWidth extends RangesMatcher
    {
        static final SingleWidth INSTANCE;
        
        private SingleWidth() {
            super("CharMatcher.singleWidth()", "\u0000\u05be\u05d0\u05f3\u0600\u0750\u0e00\u1e00\u2100\ufb50\ufe70\uff61".toCharArray(), "\u04f9\u05be\u05ea\u05f4\u06ff\u077f\u0e7f\u20af\u213a\ufdff\ufeff\uffdc".toCharArray());
        }
        
        static {
            INSTANCE = new SingleWidth();
        }
    }
    
    private static class Negated extends CharMatcher
    {
        final CharMatcher original;
        
        Negated(final CharMatcher a1) {
            super();
            this.original = Preconditions.checkNotNull(a1);
        }
        
        @Override
        public boolean matches(final char a1) {
            return !this.original.matches(a1);
        }
        
        @Override
        public boolean matchesAllOf(final CharSequence a1) {
            return this.original.matchesNoneOf(a1);
        }
        
        @Override
        public boolean matchesNoneOf(final CharSequence a1) {
            return this.original.matchesAllOf(a1);
        }
        
        @Override
        public int countIn(final CharSequence a1) {
            return a1.length() - this.original.countIn(a1);
        }
        
        @GwtIncompatible
        @Override
        void setBits(final BitSet a1) {
            final BitSet v1 = new BitSet();
            this.original.setBits(v1);
            v1.flip(0, 65536);
            a1.or(v1);
        }
        
        @Override
        public CharMatcher negate() {
            return this.original;
        }
        
        @Override
        public String toString() {
            return this.original + ".negate()";
        }
        
        @Override
        public /* bridge */ boolean apply(final Object a1) {
            return super.apply((Character)a1);
        }
    }
    
    private static final class And extends CharMatcher
    {
        final CharMatcher first;
        final CharMatcher second;
        
        And(final CharMatcher a1, final CharMatcher a2) {
            super();
            this.first = Preconditions.checkNotNull(a1);
            this.second = Preconditions.checkNotNull(a2);
        }
        
        @Override
        public boolean matches(final char a1) {
            return this.first.matches(a1) && this.second.matches(a1);
        }
        
        @GwtIncompatible
        @Override
        void setBits(final BitSet a1) {
            final BitSet v1 = new BitSet();
            this.first.setBits(v1);
            final BitSet v2 = new BitSet();
            this.second.setBits(v2);
            v1.and(v2);
            a1.or(v1);
        }
        
        @Override
        public String toString() {
            return "CharMatcher.and(" + this.first + ", " + this.second + ")";
        }
        
        @Override
        public /* bridge */ boolean apply(final Object a1) {
            return super.apply((Character)a1);
        }
    }
    
    private static final class Or extends CharMatcher
    {
        final CharMatcher first;
        final CharMatcher second;
        
        Or(final CharMatcher a1, final CharMatcher a2) {
            super();
            this.first = Preconditions.checkNotNull(a1);
            this.second = Preconditions.checkNotNull(a2);
        }
        
        @GwtIncompatible
        @Override
        void setBits(final BitSet a1) {
            this.first.setBits(a1);
            this.second.setBits(a1);
        }
        
        @Override
        public boolean matches(final char a1) {
            return this.first.matches(a1) || this.second.matches(a1);
        }
        
        @Override
        public String toString() {
            return "CharMatcher.or(" + this.first + ", " + this.second + ")";
        }
        
        @Override
        public /* bridge */ boolean apply(final Object a1) {
            return super.apply((Character)a1);
        }
    }
    
    private static final class Is extends FastMatcher
    {
        private final char match;
        
        Is(final char a1) {
            super();
            this.match = a1;
        }
        
        @Override
        public boolean matches(final char a1) {
            return a1 == this.match;
        }
        
        @Override
        public String replaceFrom(final CharSequence a1, final char a2) {
            return a1.toString().replace(this.match, a2);
        }
        
        @Override
        public CharMatcher and(final CharMatcher a1) {
            return a1.matches(this.match) ? this : CharMatcher.none();
        }
        
        @Override
        public CharMatcher or(final CharMatcher a1) {
            return a1.matches(this.match) ? a1 : super.or(a1);
        }
        
        @Override
        public CharMatcher negate() {
            return CharMatcher.isNot(this.match);
        }
        
        @GwtIncompatible
        @Override
        void setBits(final BitSet a1) {
            a1.set(this.match);
        }
        
        @Override
        public String toString() {
            return "CharMatcher.is('" + showCharacter(this.match) + "')";
        }
    }
    
    private static final class IsNot extends FastMatcher
    {
        private final char match;
        
        IsNot(final char a1) {
            super();
            this.match = a1;
        }
        
        @Override
        public boolean matches(final char a1) {
            return a1 != this.match;
        }
        
        @Override
        public CharMatcher and(final CharMatcher a1) {
            return a1.matches(this.match) ? super.and(a1) : a1;
        }
        
        @Override
        public CharMatcher or(final CharMatcher a1) {
            return a1.matches(this.match) ? CharMatcher.any() : this;
        }
        
        @GwtIncompatible
        @Override
        void setBits(final BitSet a1) {
            a1.set(0, this.match);
            a1.set(this.match + '\u0001', 65536);
        }
        
        @Override
        public CharMatcher negate() {
            return CharMatcher.is(this.match);
        }
        
        @Override
        public String toString() {
            return "CharMatcher.isNot('" + showCharacter(this.match) + "')";
        }
    }
    
    private static final class IsEither extends FastMatcher
    {
        private final char match1;
        private final char match2;
        
        IsEither(final char a1, final char a2) {
            super();
            this.match1 = a1;
            this.match2 = a2;
        }
        
        @Override
        public boolean matches(final char a1) {
            return a1 == this.match1 || a1 == this.match2;
        }
        
        @GwtIncompatible
        @Override
        void setBits(final BitSet a1) {
            a1.set(this.match1);
            a1.set(this.match2);
        }
        
        @Override
        public String toString() {
            return "CharMatcher.anyOf(\"" + showCharacter(this.match1) + showCharacter(this.match2) + "\")";
        }
    }
    
    private static final class AnyOf extends CharMatcher
    {
        private final char[] chars;
        
        public AnyOf(final CharSequence a1) {
            super();
            Arrays.sort(this.chars = a1.toString().toCharArray());
        }
        
        @Override
        public boolean matches(final char a1) {
            return Arrays.binarySearch(this.chars, a1) >= 0;
        }
        
        @GwtIncompatible
        @Override
        void setBits(final BitSet v0) {
            for (final char a1 : this.chars) {
                v0.set(a1);
            }
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("CharMatcher.anyOf(\"");
            for (final char v2 : this.chars) {
                sb.append(showCharacter(v2));
            }
            sb.append("\")");
            return sb.toString();
        }
        
        @Override
        public /* bridge */ boolean apply(final Object a1) {
            return super.apply((Character)a1);
        }
    }
    
    private static final class InRange extends FastMatcher
    {
        private final char startInclusive;
        private final char endInclusive;
        
        InRange(final char a1, final char a2) {
            super();
            Preconditions.checkArgument(a2 >= a1);
            this.startInclusive = a1;
            this.endInclusive = a2;
        }
        
        @Override
        public boolean matches(final char a1) {
            return this.startInclusive <= a1 && a1 <= this.endInclusive;
        }
        
        @GwtIncompatible
        @Override
        void setBits(final BitSet a1) {
            a1.set(this.startInclusive, this.endInclusive + '\u0001');
        }
        
        @Override
        public String toString() {
            return "CharMatcher.inRange('" + showCharacter(this.startInclusive) + "', '" + showCharacter(this.endInclusive) + "')";
        }
    }
    
    private static final class ForPredicate extends CharMatcher
    {
        private final Predicate<? super Character> predicate;
        
        ForPredicate(final Predicate<? super Character> a1) {
            super();
            this.predicate = Preconditions.checkNotNull(a1);
        }
        
        @Override
        public boolean matches(final char a1) {
            return this.predicate.apply(a1);
        }
        
        @Override
        public boolean apply(final Character a1) {
            return this.predicate.apply(Preconditions.checkNotNull(a1));
        }
        
        @Override
        public String toString() {
            return "CharMatcher.forPredicate(" + this.predicate + ")";
        }
        
        @Override
        public /* bridge */ boolean apply(final Object a1) {
            return this.apply((Character)a1);
        }
    }
}
