package javassist.expr;

import javassist.*;
import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.compiler.*;

static class ProceedForNew implements ProceedHandler
{
    CtClass newType;
    int newIndex;
    int methodIndex;
    
    ProceedForNew(final CtClass a1, final int a2, final int a3) {
        super();
        this.newType = a1;
        this.newIndex = a2;
        this.methodIndex = a3;
    }
    
    @Override
    public void doit(final JvstCodeGen a1, final Bytecode a2, final ASTList a3) throws CompileError {
        a2.addOpcode(187);
        a2.addIndex(this.newIndex);
        a2.addOpcode(89);
        a1.atMethodCallCore(this.newType, "<init>", a3, false, true, -1, null);
        a1.setType(this.newType);
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker a1, final ASTList a2) throws CompileError {
        a1.atMethodCallCore(this.newType, "<init>", a2);
        a1.setType(this.newType);
    }
}
