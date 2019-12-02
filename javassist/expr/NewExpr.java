package javassist.expr;

import javassist.*;
import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.compiler.*;

public class NewExpr extends Expr
{
    String newTypeName;
    int newPos;
    
    protected NewExpr(final int a1, final CodeIterator a2, final CtClass a3, final MethodInfo a4, final String a5, final int a6) {
        super(a1, a2, a3, a4);
        this.newTypeName = a5;
        this.newPos = a6;
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
    
    private CtClass getCtClass() throws NotFoundException {
        return this.thisClass.getClassPool().get(this.newTypeName);
    }
    
    public String getClassName() {
        return this.newTypeName;
    }
    
    public String getSignature() {
        final ConstPool v1 = this.getConstPool();
        final int v2 = this.iterator.u16bitAt(this.currentPos + 1);
        return v1.getMethodrefType(v2);
    }
    
    public CtConstructor getConstructor() throws NotFoundException {
        final ConstPool v1 = this.getConstPool();
        final int v2 = this.iterator.u16bitAt(this.currentPos + 1);
        final String v3 = v1.getMethodrefType(v2);
        return this.getCtClass().getConstructor(v3);
    }
    
    @Override
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }
    
    private int canReplace() throws CannotCompileException {
        final int v1 = this.iterator.byteAt(this.newPos + 3);
        if (v1 == 89) {
            return (this.iterator.byteAt(this.newPos + 4) == 94 && this.iterator.byteAt(this.newPos + 5) == 88) ? 6 : 4;
        }
        if (v1 == 90 && this.iterator.byteAt(this.newPos + 4) == 95) {
            return 5;
        }
        return 3;
    }
    
    @Override
    public void replace(final String v-11) throws CannotCompileException {
        this.thisClass.getClassFile();
        final int n = 3;
        int n2 = this.newPos;
        final int u16bit = this.iterator.u16bitAt(n2 + 1);
        final int canReplace = this.canReplace();
        for (int n3 = n2 + canReplace, a1 = n2; a1 < n3; ++a1) {
            this.iterator.writeByte(0, a1);
        }
        final ConstPool constPool = this.getConstPool();
        n2 = this.currentPos;
        final int u16bit2 = this.iterator.u16bitAt(n2 + 1);
        final String methodrefType = constPool.getMethodrefType(u16bit2);
        final Javac javac = new Javac(this.thisClass);
        final ClassPool classPool = this.thisClass.getClassPool();
        final CodeAttribute v0 = this.iterator.get();
        try {
            final CtClass[] v2 = Descriptor.getParameterTypes(methodrefType, classPool);
            final CtClass v3 = classPool.get(this.newTypeName);
            final int v4 = v0.getMaxLocals();
            javac.recordParams(this.newTypeName, v2, true, v4, this.withinStatic());
            final int v5 = javac.recordReturnType(v3, true);
            javac.recordProceed(new ProceedForNew(v3, u16bit, u16bit2));
            Expr.checkResultValue(v3, v-11);
            final Bytecode v6 = javac.getBytecode();
            Expr.storeStack(v2, true, v4, v6);
            javac.recordLocalVariables(v0, n2);
            v6.addConstZero(v3);
            v6.addStore(v5, v3);
            javac.compileStmnt(v-11);
            if (canReplace > 3) {
                v6.addAload(v5);
            }
            this.replace0(n2, v6, 3);
        }
        catch (CompileError v7) {
            throw new CannotCompileException(v7);
        }
        catch (NotFoundException v8) {
            throw new CannotCompileException(v8);
        }
        catch (BadBytecode v9) {
            throw new CannotCompileException("broken method");
        }
    }
    
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
}
