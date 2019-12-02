package javassist.bytecode.analysis;

import javassist.bytecode.stackmap.*;

public static class Catcher
{
    private Block node;
    private int typeIndex;
    
    Catcher(final BasicBlock.Catch a1) {
        super();
        this.node = (Block)a1.body;
        this.typeIndex = a1.typeIndex;
    }
    
    public Block block() {
        return this.node;
    }
    
    public String type() {
        if (this.typeIndex == 0) {
            return "java.lang.Throwable";
        }
        return this.node.method.getConstPool().getClassInfo(this.typeIndex);
    }
    
    static /* synthetic */ Block access$100(final Catcher a1) {
        return a1.node;
    }
}
