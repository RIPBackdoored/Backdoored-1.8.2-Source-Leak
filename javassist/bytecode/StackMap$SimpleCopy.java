package javassist.bytecode;

static class SimpleCopy extends Walker
{
    Writer writer;
    
    SimpleCopy(final StackMap a1) {
        super(a1);
        this.writer = new Writer();
    }
    
    byte[] doit() {
        this.visit();
        return this.writer.toByteArray();
    }
    
    @Override
    public void visit() {
        final int v1 = ByteArray.readU16bit(this.info, 0);
        this.writer.write16bit(v1);
        super.visit();
    }
    
    @Override
    public int locals(final int a1, final int a2, final int a3) {
        this.writer.write16bit(a2);
        return super.locals(a1, a2, a3);
    }
    
    @Override
    public int typeInfoArray(final int a1, final int a2, final int a3, final boolean a4) {
        this.writer.write16bit(a3);
        return super.typeInfoArray(a1, a2, a3, a4);
    }
    
    @Override
    public void typeInfo(final int a1, final byte a2) {
        this.writer.writeVerifyTypeInfo(a2, 0);
    }
    
    @Override
    public void objectVariable(final int a1, final int a2) {
        this.writer.writeVerifyTypeInfo(7, a2);
    }
    
    @Override
    public void uninitialized(final int a1, final int a2) {
        this.writer.writeVerifyTypeInfo(8, a2);
    }
}
