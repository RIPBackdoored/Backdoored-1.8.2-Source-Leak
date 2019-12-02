package javassist.bytecode;

static class SwitchShifter extends Shifter
{
    SwitchShifter(final StackMapTable a1, final int a2, final int a3) {
        super(a1, a2, a3, false);
    }
    
    @Override
    void update(final int a4, final int v1, final int v2, final int v3) {
        final int v4 = this.position;
        this.position = v4 + v1 + ((v4 != 0) ? 1 : 0);
        int v5 = v1;
        if (this.where == this.position) {
            v5 = v1 - this.gap;
        }
        else {
            if (this.where != v4) {
                return;
            }
            v5 = v1 + this.gap;
        }
        if (v1 < 64) {
            if (v5 < 64) {
                this.info[a4] = (byte)(v5 + v2);
            }
            else {
                final byte[] a5 = Shifter.insertGap(this.info, a4, 2);
                a5[a4] = (byte)v3;
                ByteArray.write16bit(v5, a5, a4 + 1);
                this.updatedInfo = a5;
            }
        }
        else if (v5 < 64) {
            final byte[] a6 = deleteGap(this.info, a4, 2);
            a6[a4] = (byte)(v5 + v2);
            this.updatedInfo = a6;
        }
        else {
            ByteArray.write16bit(v5, this.info, a4 + 1);
        }
    }
    
    static byte[] deleteGap(final byte[] a2, int a3, final int v1) {
        a3 += v1;
        final int v2 = a2.length;
        final byte[] v3 = new byte[v2 - v1];
        for (int a4 = 0; a4 < v2; ++a4) {
            v3[a4 - ((a4 < a3) ? 0 : v1)] = a2[a4];
        }
        return v3;
    }
    
    @Override
    void update(final int a1, final int a2) {
        final int v1 = this.position;
        this.position = v1 + a2 + ((v1 != 0) ? 1 : 0);
        int v2 = a2;
        if (this.where == this.position) {
            v2 = a2 - this.gap;
        }
        else {
            if (this.where != v1) {
                return;
            }
            v2 = a2 + this.gap;
        }
        ByteArray.write16bit(v2, this.info, a1 + 1);
    }
}
