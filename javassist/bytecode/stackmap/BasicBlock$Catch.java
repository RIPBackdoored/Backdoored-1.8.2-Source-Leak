package javassist.bytecode.stackmap;

public static class Catch
{
    public Catch next;
    public BasicBlock body;
    public int typeIndex;
    
    Catch(final BasicBlock a1, final int a2, final Catch a3) {
        super();
        this.body = a1;
        this.typeIndex = a2;
        this.next = a3;
    }
}
