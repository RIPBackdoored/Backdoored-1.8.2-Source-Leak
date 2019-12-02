package javassist.compiler;

import javassist.bytecode.*;
import javassist.compiler.ast.*;

class Javac$3 implements ProceedHandler {
    final /* synthetic */ ASTree val$texpr;
    final /* synthetic */ int val$methodIndex;
    final /* synthetic */ String val$descriptor;
    final /* synthetic */ String val$classname;
    final /* synthetic */ String val$methodname;
    final /* synthetic */ Javac this$0;
    
    Javac$3(final Javac a1, final ASTree val$texpr, final int val$methodIndex, final String val$descriptor, final String val$classname, final String val$methodname) {
        this.this$0 = a1;
        this.val$texpr = val$texpr;
        this.val$methodIndex = val$methodIndex;
        this.val$descriptor = val$descriptor;
        this.val$classname = val$classname;
        this.val$methodname = val$methodname;
        super();
    }
    
    @Override
    public void doit(final JvstCodeGen a1, final Bytecode a2, final ASTList a3) throws CompileError {
        a1.compileInvokeSpecial(this.val$texpr, this.val$methodIndex, this.val$descriptor, a3);
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker a1, final ASTList a2) throws CompileError {
        a1.compileInvokeSpecial(this.val$texpr, this.val$classname, this.val$methodname, this.val$descriptor, a2);
    }
}