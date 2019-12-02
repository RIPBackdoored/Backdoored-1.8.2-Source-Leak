package javassist.bytecode;

static class If16 extends Branch16
{
    If16(final int a1, final int a2) {
        super(a1, a2);
    }
    
    @Override
    int deltaSize() {
        return (this.state == 2) ? 5 : 0;
    }
    
    @Override
    void write32(final int a1, final byte[] a2, final int a3, final byte[] a4) {
        a4[a3] = (byte)this.opcode(a2[a1] & 0xFF);
        a4[a3 + 1] = 0;
        a4[a3 + 2] = 8;
        a4[a3 + 3] = -56;
        ByteArray.write32bit(this.offset - 3, a4, a3 + 4);
    }
    
    int opcode(final int a1) {
        if (a1 == 198) {
            return 199;
        }
        if (a1 == 199) {
            return 198;
        }
        if ((a1 - 153 & 0x1) == 0x0) {
            return a1 + 1;
        }
        return a1 - 1;
    }
}
