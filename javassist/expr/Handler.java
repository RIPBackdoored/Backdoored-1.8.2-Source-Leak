package javassist.expr;

import javassist.*;
import javassist.compiler.*;
import javassist.bytecode.*;

public class Handler extends Expr
{
    private static String EXCEPTION_NAME;
    private ExceptionTable etable;
    private int index;
    
    protected Handler(final ExceptionTable a1, final int a2, final CodeIterator a3, final CtClass a4, final MethodInfo a5) {
        super(a1.handlerPc(a2), a3, a4, a5);
        this.etable = a1;
        this.index = a2;
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
    
    public CtClass getType() throws NotFoundException {
        final int v0 = this.etable.catchType(this.index);
        if (v0 == 0) {
            return null;
        }
        final ConstPool v2 = this.getConstPool();
        final String v3 = v2.getClassInfo(v0);
        return this.thisClass.getClassPool().getCtClass(v3);
    }
    
    public boolean isFinally() {
        return this.etable.catchType(this.index) == 0;
    }
    
    @Override
    public void replace(final String a1) throws CannotCompileException {
        throw new RuntimeException("not implemented yet");
    }
    
    public void insertBefore(final String v-5) throws CannotCompileException {
        this.edited = true;
        final ConstPool constPool = this.getConstPool();
        final CodeAttribute value = this.iterator.get();
        final Javac javac = new Javac(this.thisClass);
        final Bytecode bytecode = javac.getBytecode();
        bytecode.setStackDepth(1);
        bytecode.setMaxLocals(value.getMaxLocals());
        try {
            final CtClass a1 = this.getType();
            final int v1 = javac.recordVariable(a1, Handler.EXCEPTION_NAME);
            javac.recordReturnType(a1, false);
            bytecode.addAstore(v1);
            javac.compileStmnt(v-5);
            bytecode.addAload(v1);
            final int v2 = this.etable.handlerPc(this.index);
            bytecode.addOpcode(167);
            bytecode.addIndex(v2 - this.iterator.getCodeLength() - bytecode.currentPc() + 1);
            this.maxStack = bytecode.getMaxStack();
            this.maxLocals = bytecode.getMaxLocals();
            final int v3 = this.iterator.append(bytecode.get());
            this.iterator.append(bytecode.getExceptionTable(), v3);
            this.etable.setHandlerPc(this.index, v3);
        }
        catch (NotFoundException v4) {
            throw new CannotCompileException(v4);
        }
        catch (CompileError v5) {
            throw new CannotCompileException(v5);
        }
    }
    
    static {
        Handler.EXCEPTION_NAME = "$1";
    }
}
