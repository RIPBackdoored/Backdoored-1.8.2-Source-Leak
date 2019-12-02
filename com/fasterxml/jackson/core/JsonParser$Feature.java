package com.fasterxml.jackson.core;

public enum Feature
{
    AUTO_CLOSE_SOURCE(true), 
    ALLOW_COMMENTS(false), 
    ALLOW_YAML_COMMENTS(false), 
    ALLOW_UNQUOTED_FIELD_NAMES(false), 
    ALLOW_SINGLE_QUOTES(false), 
    ALLOW_UNQUOTED_CONTROL_CHARS(false), 
    ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER(false), 
    ALLOW_NUMERIC_LEADING_ZEROS(false), 
    ALLOW_NON_NUMERIC_NUMBERS(false), 
    ALLOW_MISSING_VALUES(false), 
    ALLOW_TRAILING_COMMA(false), 
    STRICT_DUPLICATE_DETECTION(false), 
    IGNORE_UNDEFINED(false), 
    INCLUDE_SOURCE_IN_LOCATION(true);
    
    private final boolean _defaultState;
    private final int _mask;
    private static final /* synthetic */ Feature[] $VALUES;
    
    public static Feature[] values() {
        return Feature.$VALUES.clone();
    }
    
    public static Feature valueOf(final String a1) {
        return Enum.valueOf(Feature.class, a1);
    }
    
    public static int collectDefaults() {
        int n = 0;
        for (final Feature v2 : values()) {
            if (v2.enabledByDefault()) {
                n |= v2.getMask();
            }
        }
        return n;
    }
    
    private Feature(final boolean a1) {
        this._mask = 1 << this.ordinal();
        this._defaultState = a1;
    }
    
    public boolean enabledByDefault() {
        return this._defaultState;
    }
    
    public boolean enabledIn(final int a1) {
        return (a1 & this._mask) != 0x0;
    }
    
    public int getMask() {
        return this._mask;
    }
    
    static {
        $VALUES = new Feature[] { Feature.AUTO_CLOSE_SOURCE, Feature.ALLOW_COMMENTS, Feature.ALLOW_YAML_COMMENTS, Feature.ALLOW_UNQUOTED_FIELD_NAMES, Feature.ALLOW_SINGLE_QUOTES, Feature.ALLOW_UNQUOTED_CONTROL_CHARS, Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, Feature.ALLOW_NUMERIC_LEADING_ZEROS, Feature.ALLOW_NON_NUMERIC_NUMBERS, Feature.ALLOW_MISSING_VALUES, Feature.ALLOW_TRAILING_COMMA, Feature.STRICT_DUPLICATE_DETECTION, Feature.IGNORE_UNDEFINED, Feature.INCLUDE_SOURCE_IN_LOCATION };
    }
}
