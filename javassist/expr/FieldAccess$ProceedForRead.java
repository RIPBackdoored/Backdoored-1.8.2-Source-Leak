package javassist.expr;

import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.*;
import javassist.compiler.*;

static class ProceedForRead implements ProceedHandler
{
    CtClass fieldType;
    int opcode;
    int targetVar;
    int index;
    
    ProceedForRead(final CtClass a1, final int a2, final int a3, final int a4) {
        super();
        this.fieldType = a1;
        this.targetVar = a4;
        this.opcode = a2;
        this.index = a3;
    }
    
    @Override
    public void doit(final JvstCodeGen a3, final Bytecode v1, final ASTList v2) throws CompileError {
        if (v2 != null && !a3.isParamListName(v2)) {
            throw new CompileError("$proceed() cannot take a parameter for field reading");
        }
        int v3 = 0;
        if (FieldAccess.isStatic(this.opcode)) {
            final int a4 = 0;
        }
        else {
            v3 = -1;
            v1.addAload(this.targetVar);
        }
        if (this.fieldType instanceof CtPrimitiveType) {
            v3 += ((CtPrimitiveType)this.fieldType).getDataSize();
        }
        else {
            ++v3;
        }
        v1.add(this.opcode);
        v1.addIndex(this.index);
        v1.growStack(v3);
        a3.setType(this.fieldType);
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker a1, final ASTList a2) throws CompileError {
        a1.setType(this.fieldType);
    }
}
