package javassist.bytecode.analysis;

import javassist.bytecode.stackmap.*;

class ControlFlow$1 extends BasicBlock.Maker {
    final /* synthetic */ ControlFlow this$0;
    
    ControlFlow$1(final ControlFlow a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    protected BasicBlock makeBlock(final int a1) {
        return new Block(a1, ControlFlow.access$000(this.this$0));
    }
    
    @Override
    protected BasicBlock[] makeArray(final int a1) {
        return new Block[a1];
    }
}