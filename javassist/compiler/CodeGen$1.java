package javassist.compiler;

import javassist.bytecode.*;

class CodeGen$1 extends ReturnHook {
    final /* synthetic */ int val$var;
    final /* synthetic */ CodeGen this$0;
    
    CodeGen$1(final CodeGen a1, final CodeGen a2, final int val$var) {
        this.this$0 = a1;
        this.val$var = val$var;
        super(a2);
    }
    
    @Override
    protected boolean doit(final Bytecode a1, final int a2) {
        a1.addAload(this.val$var);
        a1.addOpcode(195);
        return false;
    }
}