package com.google.api.client.repackaged.com.google.common.base;

import java.util.regex.*;

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
