package javassist.bytecode;

static class NewRemover extends SimpleCopy
{
    int posOfNew;
    
    public NewRemover(final byte[] a1, final int a2) {
        super(a1);
        this.posOfNew = a2;
    }
    
    @Override
    public void sameLocals(final int a1, final int a2, final int a3, final int a4) {
        if (a3 == 8 && a4 == this.posOfNew) {
            super.sameFrame(a1, a2);
        }
        else {
            super.sameLocals(a1, a2, a3, a4);
        }
    }
    
    @Override
    public void fullFrame(final int v1, final int v2, final int[] v3, final int[] v4, int[] v5, int[] v6) {
        for (int v7 = v5.length - 1, a5 = 0; a5 < v7; ++a5) {
            if (v5[a5] == 8 && v6[a5] == this.posOfNew && v5[a5 + 1] == 8 && v6[a5 + 1] == this.posOfNew) {
                final int[] a6 = new int[++v7 - 2];
                final int[] a7 = new int[v7 - 2];
                int a8 = 0;
                for (int a9 = 0; a9 < v7; ++a9) {
                    if (a9 == a5) {
                        ++a9;
                    }
                    else {
                        a6[a8] = v5[a9];
                        a7[a8++] = v6[a9];
                    }
                }
                v5 = a6;
                v6 = a7;
                break;
            }
        }
        super.fullFrame(v1, v2, v3, v4, v5, v6);
    }
}
