package javassist.bytecode;

static class Jump16 extends Branch16
{
    Jump16(final int a1, final int a2) {
        super(a1, a2);
    }
    
    @Override
    int deltaSize() {
        return (this.state == 2) ? 2 : 0;
    }
    
    @Override
    void write32(final int a1, final byte[] a2, final int a3, final byte[] a4) {
        a4[a3] = (byte)(((a2[a1] & 0xFF) == 0xA7) ? 200 : 201);
        ByteArray.write32bit(this.offset, a4, a3 + 1);
    }
}
