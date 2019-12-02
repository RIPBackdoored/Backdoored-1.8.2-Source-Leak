package javassist.expr;

import javassist.*;
import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.compiler.*;

static class ProceedForArray implements ProceedHandler
{
    CtClass arrayType;
    int opcode;
    int index;
    int dimension;
    
    ProceedForArray(final CtClass a1, final int a2, final int a3, final int a4) {
        super();
        this.arrayType = a1;
        this.opcode = a2;
        this.index = a3;
        this.dimension = a4;
    }
    
    @Override
    public void doit(final JvstCodeGen a1, final Bytecode a2, final ASTList a3) throws CompileError {
        final int v1 = a1.getMethodArgsLength(a3);
        if (v1 != this.dimension) {
            throw new CompileError("$proceed() with a wrong number of parameters");
        }
        a1.atMethodArgs(a3, new int[v1], new int[v1], new String[v1]);
        a2.addOpcode(this.opcode);
        if (this.opcode == 189) {
            a2.addIndex(this.index);
        }
        else if (this.opcode == 188) {
            a2.add(this.index);
        }
        else {
            a2.addIndex(this.index);
            a2.add(this.dimension);
            a2.growStack(1 - this.dimension);
        }
        a1.setType(this.arrayType);
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker a1, final ASTList a2) throws CompileError {
        a1.setType(this.arrayType);
    }
}
