package javassist.compiler;

import javassist.bytecode.*;
import javassist.compiler.ast.*;

class Javac$1 implements ProceedHandler {
    final /* synthetic */ String val$m;
    final /* synthetic */ ASTree val$texpr;
    final /* synthetic */ Javac this$0;
    
    Javac$1(final Javac a1, final String val$m, final ASTree val$texpr) {
        this.this$0 = a1;
        this.val$m = val$m;
        this.val$texpr = val$texpr;
        super();
    }
    
    @Override
    public void doit(final JvstCodeGen a1, final Bytecode a2, final ASTList a3) throws CompileError {
        ASTree v1 = new Member(this.val$m);
        if (this.val$texpr != null) {
            v1 = Expr.make(46, this.val$texpr, v1);
        }
        v1 = CallExpr.makeCall(v1, a3);
        a1.compileExpr(v1);
        a1.addNullIfVoid();
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker a1, final ASTList a2) throws CompileError {
        ASTree v1 = new Member(this.val$m);
        if (this.val$texpr != null) {
            v1 = Expr.make(46, this.val$texpr, v1);
        }
        v1 = CallExpr.makeCall(v1, a2);
        v1.accept(a1);
        a1.addNullIfVoid();
    }
}