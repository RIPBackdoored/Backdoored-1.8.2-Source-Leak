package javassist.bytecode.stackmap;

public static class Maker extends BasicBlock.Maker
{
    public Maker() {
        super();
    }
    
    @Override
    protected BasicBlock makeBlock(final int a1) {
        return new TypedBlock(a1);
    }
    
    @Override
    protected BasicBlock[] makeArray(final int a1) {
        return new TypedBlock[a1];
    }
}
