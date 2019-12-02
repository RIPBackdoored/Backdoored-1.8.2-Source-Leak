package javassist.expr;

import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.*;
import javassist.compiler.*;

static class ProceedForInstanceof implements ProceedHandler
{
    int index;
    
    ProceedForInstanceof(final int a1) {
        super();
        this.index = a1;
    }
    
    @Override
    public void doit(final JvstCodeGen a1, final Bytecode a2, final ASTList a3) throws CompileError {
        if (a1.getMethodArgsLength(a3) != 1) {
            throw new CompileError("$proceed() cannot take more than one parameter for instanceof");
        }
        a1.atMethodArgs(a3, new int[1], new int[1], new String[1]);
        a2.addOpcode(193);
        a2.addIndex(this.index);
        a1.setType(CtClass.booleanType);
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker a1, final ASTList a2) throws CompileError {
        a1.atMethodArgs(a2, new int[1], new int[1], new String[1]);
        a1.setType(CtClass.booleanType);
    }
}
