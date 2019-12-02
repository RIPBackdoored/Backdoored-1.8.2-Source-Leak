package org.yaml.snakeyaml.external.com.google.gdata.util.common.base;

static final class UnicodeEscaper$2 extends ThreadLocal<char[]> {
    UnicodeEscaper$2() {
        super();
    }
    
    @Override
    protected char[] initialValue() {
        return new char[1024];
    }
    
    @Override
    protected /* bridge */ Object initialValue() {
        return this.initialValue();
    }
}