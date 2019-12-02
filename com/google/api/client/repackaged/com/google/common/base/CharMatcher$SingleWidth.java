package com.google.api.client.repackaged.com.google.common.base;

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
