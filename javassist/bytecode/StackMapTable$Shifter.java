package javassist.bytecode;

static class Shifter extends Walker
{
    private StackMapTable stackMap;
    int where;
    int gap;
    int position;
    byte[] updatedInfo;
    boolean exclusive;
    
    public Shifter(final StackMapTable a1, final int a2, final int a3, final boolean a4) {
        super(a1);
        this.stackMap = a1;
        this.where = a2;
        this.gap = a3;
        this.position = 0;
        this.updatedInfo = null;
        this.exclusive = a4;
    }
    
    public void doit() throws BadBytecode {
        this.parse();
        if (this.updatedInfo != null) {
            this.stackMap.set(this.updatedInfo);
        }
    }
    
    @Override
    public void sameFrame(final int a1, final int a2) {
        this.update(a1, a2, 0, 251);
    }
    
    @Override
    public void sameLocals(final int a1, final int a2, final int a3, final int a4) {
        this.update(a1, a2, 64, 247);
    }
    
    void update(final int v1, final int v2, final int v3, final int v4) {
        final int v5 = this.position;
        this.position = v5 + v2 + ((v5 != 0) ? 1 : 0);
        boolean v6 = false;
        if (this.exclusive) {
            final boolean a1 = v5 < this.where && this.where <= this.position;
        }
        else {
            v6 = (v5 <= this.where && this.where < this.position);
        }
        if (v6) {
            final int a2 = v2 + this.gap;
            this.position += this.gap;
            if (a2 < 64) {
                this.info[v1] = (byte)(a2 + v3);
            }
            else if (v2 < 64) {
                final byte[] a3 = insertGap(this.info, v1, 2);
                a3[v1] = (byte)v4;
                ByteArray.write16bit(a2, a3, v1 + 1);
                this.updatedInfo = a3;
            }
            else {
                ByteArray.write16bit(a2, this.info, v1 + 1);
            }
        }
    }
    
    static byte[] insertGap(final byte[] a2, final int a3, final int v1) {
        final int v2 = a2.length;
        final byte[] v3 = new byte[v2 + v1];
        for (int a4 = 0; a4 < v2; ++a4) {
            v3[a4 + ((a4 < a3) ? 0 : v1)] = a2[a4];
        }
        return v3;
    }
    
    @Override
    public void chopFrame(final int a1, final int a2, final int a3) {
        this.update(a1, a2);
    }
    
    @Override
    public void appendFrame(final int a1, final int a2, final int[] a3, final int[] a4) {
        this.update(a1, a2);
    }
    
    @Override
    public void fullFrame(final int a1, final int a2, final int[] a3, final int[] a4, final int[] a5, final int[] a6) {
        this.update(a1, a2);
    }
    
    void update(final int v2, final int v3) {
        final int v4 = this.position;
        this.position = v4 + v3 + ((v4 != 0) ? 1 : 0);
        boolean v5 = false;
        if (this.exclusive) {
            final boolean a1 = v4 < this.where && this.where <= this.position;
        }
        else {
            v5 = (v4 <= this.where && this.where < this.position);
        }
        if (v5) {
            final int a2 = v3 + this.gap;
            ByteArray.write16bit(a2, this.info, v2 + 1);
            this.position += this.gap;
        }
    }
}
