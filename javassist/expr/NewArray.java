package javassist.expr;

import javassist.*;
import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.compiler.*;

public class NewArray extends Expr
{
    int opcode;
    
    protected NewArray(final int a1, final CodeIterator a2, final CtClass a3, final MethodInfo a4, final int a5) {
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
    
    @Override
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }
    
    public CtClass getComponentType() throws NotFoundException {
        if (this.opcode == 188) {
            final int v1 = this.iterator.byteAt(this.currentPos + 1);
            return this.getPrimitiveType(v1);
        }
        if (this.opcode == 189 || this.opcode == 197) {
            final int v1 = this.iterator.u16bitAt(this.currentPos + 1);
            String v2 = this.getConstPool().getClassInfo(v1);
            final int v3 = Descriptor.arrayDimension(v2);
            v2 = Descriptor.toArrayComponent(v2, v3);
            return Descriptor.toCtClass(v2, this.thisClass.getClassPool());
        }
        throw new RuntimeException("bad opcode: " + this.opcode);
    }
    
    CtClass getPrimitiveType(final int a1) {
        switch (a1) {
            case 4: {
                return CtClass.booleanType;
            }
            case 5: {
                return CtClass.charType;
            }
            case 6: {
                return CtClass.floatType;
            }
            case 7: {
                return CtClass.doubleType;
            }
            case 8: {
                return CtClass.byteType;
            }
            case 9: {
                return CtClass.shortType;
            }
            case 10: {
                return CtClass.intType;
            }
            case 11: {
                return CtClass.longType;
            }
            default: {
                throw new RuntimeException("bad atype: " + a1);
            }
        }
    }
    
    public int getDimension() {
        if (this.opcode == 188) {
            return 1;
        }
        if (this.opcode == 189 || this.opcode == 197) {
            final int v1 = this.iterator.u16bitAt(this.currentPos + 1);
            final String v2 = this.getConstPool().getClassInfo(v1);
            return Descriptor.arrayDimension(v2) + ((this.opcode == 189) ? 1 : 0);
        }
        throw new RuntimeException("bad opcode: " + this.opcode);
    }
    
    public int getCreatedDimensions() {
        if (this.opcode == 197) {
            return this.iterator.byteAt(this.currentPos + 3);
        }
        return 1;
    }
    
    @Override
    public void replace(final String v0) throws CannotCompileException {
        try {
            this.replace2(v0);
        }
        catch (CompileError a1) {
            throw new CannotCompileException(a1);
        }
        catch (NotFoundException v) {
            throw new CannotCompileException(v);
        }
        catch (BadBytecode v2) {
            throw new CannotCompileException("broken method");
        }
    }
    
    private void replace2(final String v-3) throws CompileError, NotFoundException, BadBytecode, CannotCompileException {
        this.thisClass.getClassFile();
        final ConstPool constPool = this.getConstPool();
        final int currentPos = this.currentPos;
        int v2 = 0;
        int v3 = 1;
        String v4;
        int v5;
        if (this.opcode == 188) {
            v2 = this.iterator.byteAt(this.currentPos + 1);
            final CtPrimitiveType a1 = (CtPrimitiveType)this.getPrimitiveType(v2);
            v4 = "[" + a1.getDescriptor();
            v5 = 2;
        }
        else if (this.opcode == 189) {
            v2 = this.iterator.u16bitAt(currentPos + 1);
            v4 = constPool.getClassInfo(v2);
            if (v4.startsWith("[")) {
                v4 = "[" + v4;
            }
            else {
                v4 = "[L" + v4 + ";";
            }
            v5 = 3;
        }
        else {
            if (this.opcode != 197) {
                throw new RuntimeException("bad opcode: " + this.opcode);
            }
            v2 = this.iterator.u16bitAt(this.currentPos + 1);
            v4 = constPool.getClassInfo(v2);
            v3 = this.iterator.byteAt(this.currentPos + 3);
            v5 = 4;
        }
        final CtClass v6 = Descriptor.toCtClass(v4, this.thisClass.getClassPool());
        final Javac v7 = new Javac(this.thisClass);
        final CodeAttribute v8 = this.iterator.get();
        final CtClass[] v9 = new CtClass[v3];
        for (int v10 = 0; v10 < v3; ++v10) {
            v9[v10] = CtClass.intType;
        }
        int v10 = v8.getMaxLocals();
        v7.recordParams("java.lang.Object", v9, true, v10, this.withinStatic());
        Expr.checkResultValue(v6, v-3);
        final int v11 = v7.recordReturnType(v6, true);
        v7.recordProceed(new ProceedForArray(v6, this.opcode, v2, v3));
        final Bytecode v12 = v7.getBytecode();
        Expr.storeStack(v9, true, v10, v12);
        v7.recordLocalVariables(v8, currentPos);
        v12.addOpcode(1);
        v12.addAstore(v11);
        v7.compileStmnt(v-3);
        v12.addAload(v11);
        this.replace0(currentPos, v12, v5);
    }
    
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
}
