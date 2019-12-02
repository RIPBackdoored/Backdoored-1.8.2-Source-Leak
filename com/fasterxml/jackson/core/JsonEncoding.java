package com.fasterxml.jackson.core;

public enum JsonEncoding
{
    UTF8("UTF-8", false, 8), 
    UTF16_BE("UTF-16BE", true, 16), 
    UTF16_LE("UTF-16LE", false, 16), 
    UTF32_BE("UTF-32BE", true, 32), 
    UTF32_LE("UTF-32LE", false, 32);
    
    private final String _javaName;
    private final boolean _bigEndian;
    private final int _bits;
    private static final /* synthetic */ JsonEncoding[] $VALUES;
    
    public static JsonEncoding[] values() {
        return JsonEncoding.$VALUES.clone();
    }
    
    public static JsonEncoding valueOf(final String a1) {
        return Enum.valueOf(JsonEncoding.class, a1);
    }
    
    private JsonEncoding(final String a1, final boolean a2, final int a3) {
        this._javaName = a1;
        this._bigEndian = a2;
        this._bits = a3;
    }
    
    public String getJavaName() {
        return this._javaName;
    }
    
    public boolean isBigEndian() {
        return this._bigEndian;
    }
    
    public int bits() {
        return this._bits;
    }
    
    static {
        $VALUES = new JsonEncoding[] { JsonEncoding.UTF8, JsonEncoding.UTF16_BE, JsonEncoding.UTF16_LE, JsonEncoding.UTF32_BE, JsonEncoding.UTF32_LE };
    }
}
