package javassist.bytecode;

static class OffsetShifter extends Walker
{
    int where;
    int gap;
    
    public OffsetShifter(final StackMapTable a1, final int a2, final int a3) {
        super(a1);
        this.where = a2;
        this.gap = a3;
    }
    
    @Override
    public void objectOrUninitialized(final int a1, final int a2, final int a3) {
        if (a1 == 8 && this.where <= a2) {
            ByteArray.write16bit(a2 + this.gap, this.info, a3);
        }
    }
}
