package javassist.expr;

static final class LoopContext
{
    NewOp newList;
    int maxLocals;
    int maxStack;
    
    LoopContext(final int a1) {
        super();
        this.maxLocals = a1;
        this.maxStack = 0;
        this.newList = null;
    }
    
    void updateMax(final int a1, final int a2) {
        if (this.maxLocals < a1) {
            this.maxLocals = a1;
        }
        if (this.maxStack < a2) {
            this.maxStack = a2;
        }
    }
}
