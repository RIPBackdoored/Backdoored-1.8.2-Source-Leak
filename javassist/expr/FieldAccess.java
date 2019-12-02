package javassist.expr;

import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.*;
import javassist.compiler.*;

public class FieldAccess extends Expr
{
    int opcode;
    
    protected FieldAccess(final int a1, final CodeIterator a2, final CtClass a3, final MethodInfo a4, final int a5) {
        super(a1, a2, a3, a4);
        this.opcode = a5;
    }
    
    @Override
    public CtBehavior where() {
        return super.where();
    }
    
    @Override
    public int getLineNumber() {
        return super.getLineNumber();
    }
    
    @Override
    public String getFileName() {
        return super.getFileName();
    }
    
    public boolean isStatic() {
        return isStatic(this.opcode);
    }
    
    static boolean isStatic(final int a1) {
        return a1 == 178 || a1 == 179;
    }
    
    public boolean isReader() {
        return this.opcode == 180 || this.opcode == 178;
    }
    
    public boolean isWriter() {
        return this.opcode == 181 || this.opcode == 179;
    }
    
    private CtClass getCtClass() throws NotFoundException {
        return this.thisClass.getClassPool().get(this.getClassName());
    }
    
    public String getClassName() {
        final int v1 = this.iterator.u16bitAt(this.currentPos + 1);
        return this.getConstPool().getFieldrefClassName(v1);
    }
    
    public String getFieldName() {
        final int v1 = this.iterator.u16bitAt(this.currentPos + 1);
        return this.getConstPool().getFieldrefName(v1);
    }
    
    public CtField getField() throws NotFoundException {
        final CtClass v1 = this.getCtClass();
        final int v2 = this.iterator.u16bitAt(this.currentPos + 1);
        final ConstPool v3 = this.getConstPool();
        return v1.getField(v3.getFieldrefName(v2), v3.getFieldrefType(v2));
    }
    
    @Override
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }
    
    public String getSignature() {
        final int v1 = this.iterator.u16bitAt(this.currentPos + 1);
        return this.getConstPool().getFieldrefType(v1);
    }
    
    @Override
    public void replace(final String v-6) throws CannotCompileException {
        this.thisClass.getClassFile();
        final ConstPool constPool = this.getConstPool();
        final int currentPos = this.currentPos;
        final int u16bit = this.iterator.u16bitAt(currentPos + 1);
        final Javac javac = new Javac(this.thisClass);
        final CodeAttribute value = this.iterator.get();
        try {
            final CtClass v2 = Descriptor.toCtClass(constPool.getFieldrefType(u16bit), this.thisClass.getClassPool());
            final boolean v3 = this.isReader();
            CtClass v4;
            CtClass[] v5 = null;
            if (v3) {
                final CtClass[] a1 = new CtClass[0];
                v4 = v2;
            }
            else {
                v5 = new CtClass[] { v2 };
                v4 = CtClass.voidType;
            }
            final int v6 = value.getMaxLocals();
            javac.recordParams(constPool.getFieldrefClassName(u16bit), v5, true, v6, this.withinStatic());
            boolean v7 = Expr.checkResultValue(v4, v-6);
            if (v3) {
                v7 = true;
            }
            final int v8 = javac.recordReturnType(v4, v7);
            if (v3) {
                javac.recordProceed(new ProceedForRead(v4, this.opcode, u16bit, v6));
            }
            else {
                javac.recordType(v2);
                javac.recordProceed(new ProceedForWrite(v5[0], this.opcode, u16bit, v6));
            }
            final Bytecode v9 = javac.getBytecode();
            Expr.storeStack(v5, this.isStatic(), v6, v9);
            javac.recordLocalVariables(value, currentPos);
            if (v7) {
                if (v4 == CtClass.voidType) {
                    v9.addOpcode(1);
                    v9.addAstore(v8);
                }
                else {
                    v9.addConstZero(v4);
                    v9.addStore(v8, v4);
                }
            }
            javac.compileStmnt(v-6);
            if (v3) {
                v9.addLoad(v8, v4);
            }
            this.replace0(currentPos, v9, 3);
        }
        catch (CompileError v10) {
            throw new CannotCompileException(v10);
        }
        catch (NotFoundException v11) {
            throw new CannotCompileException(v11);
        }
        catch (BadBytecode v12) {
            throw new CannotCompileException("broken method");
        }
    }
    
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
}
