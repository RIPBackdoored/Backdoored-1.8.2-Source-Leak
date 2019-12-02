package com.fasterxml.jackson.core.sym;

private static final class TableInfo
{
    public final int size;
    public final int count;
    public final int tertiaryShift;
    public final int[] mainHash;
    public final String[] names;
    public final int spilloverEnd;
    public final int longNameOffset;
    
    public TableInfo(final int a1, final int a2, final int a3, final int[] a4, final String[] a5, final int a6, final int a7) {
        super();
        this.size = a1;
        this.count = a2;
        this.tertiaryShift = a3;
        this.mainHash = a4;
        this.names = a5;
        this.spilloverEnd = a6;
        this.longNameOffset = a7;
    }
    
    public TableInfo(final ByteQuadsCanonicalizer a1) {
        super();
        this.size = ByteQuadsCanonicalizer.access$000(a1);
        this.count = ByteQuadsCanonicalizer.access$100(a1);
        this.tertiaryShift = ByteQuadsCanonicalizer.access$200(a1);
        this.mainHash = ByteQuadsCanonicalizer.access$300(a1);
        this.names = ByteQuadsCanonicalizer.access$400(a1);
        this.spilloverEnd = ByteQuadsCanonicalizer.access$500(a1);
        this.longNameOffset = ByteQuadsCanonicalizer.access$600(a1);
    }
    
    public static TableInfo createInitial(final int a1) {
        final int v1 = a1 << 3;
        final int v2 = ByteQuadsCanonicalizer._calcTertiaryShift(a1);
        return new TableInfo(a1, 0, v2, new int[v1], new String[a1 << 1], v1 - a1, v1);
    }
}
