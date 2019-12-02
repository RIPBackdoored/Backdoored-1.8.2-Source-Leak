package com.backdoored.utils;

private enum b
{
    bce("dev", 0), 
    bcf("betaTester", 1), 
    bcg("owner", 2), 
    bch("none", 3);
    
    private static final /* synthetic */ b[] $VALUES;
    
    public static b[] values() {
        return b.$VALUES.clone();
    }
    
    public static b valueOf(final String s) {
        return Enum.valueOf(b.class, s);
    }
    
    private b(final String s, final int n) {
    }
    
    static {
        $VALUES = new b[] { b.bce, b.bcf, b.bcg, b.bch };
    }
}
