package javassist.expr;

import javassist.*;
import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.compiler.*;

public class Cast extends Expr
{
    protected Cast(final int a1, final CodeIterator a2, final CtClass a3, final MethodInfo a4) {
        super(a1, a2, a3, a4);
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
    
    public CtClass getType() throws NotFoundException {
        final ConstPool v1 = this.getConstPool();
        final int v2 = this.currentPos;
        final int v3 = this.iterator.u16bitAt(v2 + 1);
        final String v4 = v1.getClassInfo(v3);
        return this.thisClass.getClassPool().getCtClass(v4);
    }
    
    @Override
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }
    
    @Override
    public void replace(final String v-7) throws CannotCompileException {
        this.thisClass.getClassFile();
        final ConstPool constPool = this.getConstPool();
        final int currentPos = this.currentPos;
        final int u16bit = this.iterator.u16bitAt(currentPos + 1);
        final Javac javac = new Javac(this.thisClass);
        final ClassPool classPool = this.thisClass.getClassPool();
        final CodeAttribute value = this.iterator.get();
        try {
            final CtClass[] a1 = { classPool.get("java.lang.Object") };
            final CtClass v1 = this.getType();
            final int v2 = value.getMaxLocals();
            javac.recordParams("java.lang.Object", a1, true, v2, this.withinStatic());
            final int v3 = javac.recordReturnType(v1, true);
            javac.recordProceed(new ProceedForCast(u16bit, v1));
            Expr.checkResultValue(v1, v-7);
            final Bytecode v4 = javac.getBytecode();
            Expr.storeStack(a1, true, v2, v4);
            javac.recordLocalVariables(value, currentPos);
            v4.addConstZero(v1);
            v4.addStore(v3, v1);
            javac.compileStmnt(v-7);
            v4.addLoad(v3, v1);
            this.replace0(currentPos, v4, 3);
        }
        catch (CompileError v5) {
            throw new CannotCompileException(v5);
        }
        catch (NotFoundException v6) {
            throw new CannotCompileException(v6);
        }
        catch (BadBytecode v7) {
            throw new CannotCompileException("broken method");
        }
    }
    
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
}
