package javassist.compiler;

import javassist.bytecode.*;
import javassist.compiler.ast.*;

class Javac$2 implements ProceedHandler {
    final /* synthetic */ String val$c;
    final /* synthetic */ String val$m;
    final /* synthetic */ Javac this$0;
    
    Javac$2(final Javac a1, final String val$c, final String val$m) {
        this.this$0 = a1;
        this.val$c = val$c;
        this.val$m = val$m;
        super();
    }
    
    @Override
    public void doit(final JvstCodeGen a1, final Bytecode a2, final ASTList a3) throws CompileError {
        Expr v1 = Expr.make(35, new Symbol(this.val$c), new Member(this.val$m));
        v1 = CallExpr.makeCall(v1, a3);
        a1.compileExpr(v1);
        a1.addNullIfVoid();
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker a1, final ASTList a2) throws CompileError {
        Expr v1 = Expr.make(35, new Symbol(this.val$c), new Member(this.val$m));
        v1 = CallExpr.makeCall(v1, a2);
        v1.accept(a1);
        a1.addNullIfVoid();
    }
}