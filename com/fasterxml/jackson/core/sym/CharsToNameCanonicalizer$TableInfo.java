package com.fasterxml.jackson.core.sym;

private static final class TableInfo
{
    final int size;
    final int longestCollisionList;
    final String[] symbols;
    final Bucket[] buckets;
    
    public TableInfo(final int a1, final int a2, final String[] a3, final Bucket[] a4) {
        super();
        this.size = a1;
        this.longestCollisionList = a2;
        this.symbols = a3;
        this.buckets = a4;
    }
    
    public TableInfo(final CharsToNameCanonicalizer a1) {
        super();
        this.size = CharsToNameCanonicalizer.access$000(a1);
        this.longestCollisionList = CharsToNameCanonicalizer.access$100(a1);
        this.symbols = CharsToNameCanonicalizer.access$200(a1);
        this.buckets = CharsToNameCanonicalizer.access$300(a1);
    }
    
    public static TableInfo createInitial(final int a1) {
        return new TableInfo(0, 0, new String[a1], new Bucket[a1 >> 1]);
    }
}
