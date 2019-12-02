package javassist.bytecode;

static class SwitchShifter extends Walker
{
    private int where;
    private int gap;
    
    public SwitchShifter(final StackMap a1, final int a2, final int a3) {
        super(a1);
        this.where = a2;
        this.gap = a3;
    }
    
    @Override
    public int locals(final int a1, final int a2, final int a3) {
        if (this.where == a1 + a2) {
            ByteArray.write16bit(a2 - this.gap, this.info, a1 - 4);
        }
        else if (this.where == a1) {
            ByteArray.write16bit(a2 + this.gap, this.info, a1 - 4);
        }
        return super.locals(a1, a2, a3);
    }
}
