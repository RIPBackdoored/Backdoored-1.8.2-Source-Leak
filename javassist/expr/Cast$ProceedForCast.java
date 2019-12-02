package javassist.expr;

import javassist.*;
import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.compiler.*;

static class ProceedForCast implements ProceedHandler
{
    int index;
    CtClass retType;
    
    ProceedForCast(final int a1, final CtClass a2) {
        super();
        this.index = a1;
        this.retType = a2;
    }
    
    @Override
    public void doit(final JvstCodeGen a1, final Bytecode a2, final ASTList a3) throws CompileError {
        if (a1.getMethodArgsLength(a3) != 1) {
            throw new CompileError("$proceed() cannot take more than one parameter for cast");
        }
        a1.atMethodArgs(a3, new int[1], new int[1], new String[1]);
        a2.addOpcode(192);
        a2.addIndex(this.index);
        a1.setType(this.retType);
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker a1, final ASTList a2) throws CompileError {
        a1.atMethodArgs(a2, new int[1], new int[1], new String[1]);
        a1.setType(this.retType);
    }
}
