package javassist.bytecode.analysis;

import javassist.bytecode.stackmap.*;

class ControlFlow$2 extends Access {
    final /* synthetic */ ControlFlow this$0;
    
    ControlFlow$2(final ControlFlow a1, final Node[] a2) {
        this.this$0 = a1;
        super(a2);
    }
    
    @Override
    BasicBlock[] exits(final Node a1) {
        return a1.block.getExit();
    }
    
    @Override
    BasicBlock[] entrances(final Node a1) {
        return a1.block.entrances;
    }
}