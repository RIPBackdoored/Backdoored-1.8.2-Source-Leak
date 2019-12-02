package javassist.bytecode;

static class SimpleCopy extends Walker
{
    private Writer writer;
    
    public SimpleCopy(final byte[] a1) {
        super(a1);
        this.writer = new Writer(a1.length);
    }
    
    public byte[] doit() throws BadBytecode {
        this.parse();
        return this.writer.toByteArray();
    }
    
    @Override
    public void sameFrame(final int a1, final int a2) {
        this.writer.sameFrame(a2);
    }
    
    @Override
    public void sameLocals(final int a1, final int a2, final int a3, final int a4) {
        this.writer.sameLocals(a2, a3, this.copyData(a3, a4));
    }
    
    @Override
    public void chopFrame(final int a1, final int a2, final int a3) {
        this.writer.chopFrame(a2, a3);
    }
    
    @Override
    public void appendFrame(final int a1, final int a2, final int[] a3, final int[] a4) {
        this.writer.appendFrame(a2, a3, this.copyData(a3, a4));
    }
    
    @Override
    public void fullFrame(final int a1, final int a2, final int[] a3, final int[] a4, final int[] a5, final int[] a6) {
        this.writer.fullFrame(a2, a3, this.copyData(a3, a4), a5, this.copyData(a5, a6));
    }
    
    protected int copyData(final int a1, final int a2) {
        return a2;
    }
    
    protected int[] copyData(final int[] a1, final int[] a2) {
        return a2;
    }
}
