package javassist.bytecode;

static class InsertLocal extends SimpleCopy
{
    private int varIndex;
    private int varTag;
    private int varData;
    
    public InsertLocal(final byte[] a1, final int a2, final int a3, final int a4) {
        super(a1);
        this.varIndex = a2;
        this.varTag = a3;
        this.varData = a4;
    }
    
    @Override
    public void fullFrame(final int a3, final int a4, final int[] a5, final int[] a6, final int[] v1, final int[] v2) {
        final int v3 = a5.length;
        if (v3 < this.varIndex) {
            super.fullFrame(a3, a4, a5, a6, v1, v2);
            return;
        }
        final int v4 = (this.varTag == 4 || this.varTag == 3) ? 2 : 1;
        final int[] v5 = new int[v3 + v4];
        final int[] v6 = new int[v3 + v4];
        final int v7 = this.varIndex;
        int v8 = 0;
        for (int a7 = 0; a7 < v3; ++a7) {
            if (v8 == v7) {
                v8 += v4;
            }
            v5[v8] = a5[a7];
            v6[v8++] = a6[a7];
        }
        v5[v7] = this.varTag;
        v6[v7] = this.varData;
        if (v4 > 1) {
            v6[v7 + 1] = (v5[v7 + 1] = 0);
        }
        super.fullFrame(a3, a4, v5, v6, v1, v2);
    }
}
