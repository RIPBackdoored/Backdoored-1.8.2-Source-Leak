package javassist.bytecode;

static class LdcW extends Branch
{
    int index;
    boolean state;
    
    LdcW(final int a1, final int a2) {
        super(a1);
        this.index = a2;
        this.state = true;
    }
    
    @Override
    boolean expanded() {
        if (this.state) {
            this.state = false;
            return true;
        }
        return false;
    }
    
    @Override
    int deltaSize() {
        return 1;
    }
    
    @Override
    int write(final int a1, final byte[] a2, final int a3, final byte[] a4) {
        a4[a3] = 19;
        ByteArray.write16bit(this.index, a4, a3 + 1);
        return 2;
    }
}
