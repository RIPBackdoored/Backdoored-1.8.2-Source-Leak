package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.lang.ref.*;
import javax.annotation.*;
import java.util.*;
import java.util.logging.*;
import java.util.regex.*;

@GwtCompatible(emulated = true)
final class Platform
{
    private static final Logger logger;
    private static final PatternCompiler patternCompiler;
    
    private Platform() {
        super();
    }
    
    static long systemNanoTime() {
        return System.nanoTime();
    }
    
    static CharMatcher precomputeCharMatcher(final CharMatcher a1) {
        return a1.precomputedInternal();
    }
    
    static <T extends Enum<T>> Optional<T> getEnumIfPresent(final Class<T> a1, final String a2) {
        final WeakReference<? extends Enum<?>> v1 = Enums.getEnumConstants(a1).get(a2);
        return (v1 == null) ? Optional.absent() : Optional.of(a1.cast(v1.get()));
    }
    
    static String formatCompact4Digits(final double a1) {
        return String.format(Locale.ROOT, "%.4g", a1);
    }
    
    static boolean stringIsNullOrEmpty(@Nullable final String a1) {
        return a1 == null || a1.isEmpty();
    }
    
    static CommonPattern compilePattern(final String a1) {
        Preconditions.checkNotNull(a1);
        return Platform.patternCompiler.compile(a1);
    }
    
    static boolean usingJdkPatternCompiler() {
        return Platform.patternCompiler instanceof JdkPatternCompiler;
    }
    
    private static PatternCompiler loadPatternCompiler() {
        return new JdkPatternCompiler();
    }
    
    private static void logPatternCompilerError(final ServiceConfigurationError a1) {
        Platform.logger.log(Level.WARNING, "Error loading regex compiler, falling back to next option", a1);
    }
    
    static {
        logger = Logger.getLogger(Platform.class.getName());
        patternCompiler = loadPatternCompiler();
    }
    
    private static final class JdkPatternCompiler implements PatternCompiler
    {
        private JdkPatternCompiler() {
            super();
        }
        
        @Override
        public CommonPattern compile(final String a1) {
            return new JdkPattern(Pattern.compile(a1));
        }
        
        JdkPatternCompiler(final Platform$1 a1) {
            this();
        }
    }
}
