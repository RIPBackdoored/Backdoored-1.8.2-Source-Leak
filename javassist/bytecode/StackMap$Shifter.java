package javassist.bytecode;

static class Shifter extends Walker
{
    private int where;
    private int gap;
    private boolean exclusive;
    
    public Shifter(final StackMap a1, final int a2, final int a3, final boolean a4) {
        super(a1);
        this.where = a2;
        this.gap = a3;
        this.exclusive = a4;
    }
    
    @Override
    public int locals(final int a1, final int a2, final int a3) {
        if (this.exclusive) {
            if (this.where > a2) {
                return super.locals(a1, a2, a3);
            }
        }
        else if (this.where >= a2) {
            return super.locals(a1, a2, a3);
        }
        ByteArray.write16bit(a2 + this.gap, this.info, a1 - 4);
        return super.locals(a1, a2, a3);
    }
    
    @Override
    public void uninitialized(final int a1, final int a2) {
        if (this.where <= a2) {
            ByteArray.write16bit(a2 + this.gap, this.info, a1 + 1);
        }
    }
}
