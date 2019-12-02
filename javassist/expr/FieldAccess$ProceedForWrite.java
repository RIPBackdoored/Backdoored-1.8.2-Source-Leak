package javassist.expr;

import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.*;
import javassist.compiler.*;

static class ProceedForWrite implements ProceedHandler
{
    CtClass fieldType;
    int opcode;
    int targetVar;
    int index;
    
    ProceedForWrite(final CtClass a1, final int a2, final int a3, final int a4) {
        super();
        this.fieldType = a1;
        this.targetVar = a4;
        this.opcode = a2;
        this.index = a3;
    }
    
    @Override
    public void doit(final JvstCodeGen a3, final Bytecode v1, final ASTList v2) throws CompileError {
        if (a3.getMethodArgsLength(v2) != 1) {
            throw new CompileError("$proceed() cannot take more than one parameter for field writing");
        }
        int v3 = 0;
        if (FieldAccess.isStatic(this.opcode)) {
            final int a4 = 0;
        }
        else {
            v3 = -1;
            v1.addAload(this.targetVar);
        }
        a3.atMethodArgs(v2, new int[1], new int[1], new String[1]);
        a3.doNumCast(this.fieldType);
        if (this.fieldType instanceof CtPrimitiveType) {
            v3 -= ((CtPrimitiveType)this.fieldType).getDataSize();
        }
        else {
            --v3;
        }
        v1.add(this.opcode);
        v1.addIndex(this.index);
        v1.growStack(v3);
        a3.setType(CtClass.voidType);
        a3.addNullIfVoid();
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker a1, final ASTList a2) throws CompileError {
        a1.atMethodArgs(a2, new int[1], new int[1], new String[1]);
        a1.setType(CtClass.voidType);
        a1.addNullIfVoid();
    }
}
