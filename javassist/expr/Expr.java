package javassist.expr;

import java.util.*;
import javassist.*;
import javassist.bytecode.*;

public abstract class Expr implements Opcode
{
    int currentPos;
    CodeIterator iterator;
    CtClass thisClass;
    MethodInfo thisMethod;
    boolean edited;
    int maxLocals;
    int maxStack;
    static final String javaLangObject = "java.lang.Object";
    
    protected Expr(final int a1, final CodeIterator a2, final CtClass a3, final MethodInfo a4) {
        super();
        this.currentPos = a1;
        this.iterator = a2;
        this.thisClass = a3;
        this.thisMethod = a4;
    }
    
    public CtClass getEnclosingClass() {
        return this.thisClass;
    }
    
    protected final ConstPool getConstPool() {
        return this.thisMethod.getConstPool();
    }
    
    protected final boolean edited() {
        return this.edited;
    }
    
    protected final int locals() {
        return this.maxLocals;
    }
    
    protected final int stack() {
        return this.maxStack;
    }
    
    protected final boolean withinStatic() {
        return (this.thisMethod.getAccessFlags() & 0x8) != 0x0;
    }
    
    public CtBehavior where() {
        final MethodInfo thisMethod = this.thisMethod;
        final CtBehavior[] v0 = this.thisClass.getDeclaredBehaviors();
        for (int v2 = v0.length - 1; v2 >= 0; --v2) {
            if (v0[v2].getMethodInfo2() == thisMethod) {
                return v0[v2];
            }
        }
        final CtConstructor v3 = this.thisClass.getClassInitializer();
        if (v3 != null && v3.getMethodInfo2() == thisMethod) {
            return v3;
        }
        for (int v4 = v0.length - 1; v4 >= 0; --v4) {
            if (this.thisMethod.getName().equals(v0[v4].getMethodInfo2().getName()) && this.thisMethod.getDescriptor().equals(v0[v4].getMethodInfo2().getDescriptor())) {
                return v0[v4];
            }
        }
        throw new RuntimeException("fatal: not found");
    }
    
    public CtClass[] mayThrow() {
        final ClassPool classPool = this.thisClass.getClassPool();
        final ConstPool constPool = this.thisMethod.getConstPool();
        final LinkedList list = new LinkedList();
        try {
            final CodeAttribute codeAttribute = this.thisMethod.getCodeAttribute();
            final ExceptionTable exceptionTable = codeAttribute.getExceptionTable();
            final int n = this.currentPos;
            for (int i = exceptionTable.size(), v0 = 0; v0 < i; ++v0) {
                if (exceptionTable.startPc(v0) <= n && n < exceptionTable.endPc(v0)) {
                    final int v2 = exceptionTable.catchType(v0);
                    if (v2 > 0) {
                        try {
                            addClass(list, classPool.get(constPool.getClassInfo(v2)));
                        }
                        catch (NotFoundException ex) {}
                    }
                }
            }
        }
        catch (NullPointerException ex2) {}
        final ExceptionsAttribute exceptionsAttribute = this.thisMethod.getExceptionsAttribute();
        if (exceptionsAttribute != null) {
            final String[] exceptions = exceptionsAttribute.getExceptions();
            if (exceptions != null) {
                for (int n = exceptions.length, i = 0; i < n; ++i) {
                    try {
                        addClass(list, classPool.get(exceptions[i]));
                    }
                    catch (NotFoundException ex3) {}
                }
            }
        }
        return list.toArray(new CtClass[list.size()]);
    }
    
    private static void addClass(final LinkedList a1, final CtClass a2) {
        final Iterator v1 = a1.iterator();
        while (v1.hasNext()) {
            if (v1.next() == a2) {
                return;
            }
        }
        a1.add(a2);
    }
    
    public int indexOfBytecode() {
        return this.currentPos;
    }
    
    public int getLineNumber() {
        return this.thisMethod.getLineNumber(this.currentPos);
    }
    
    public String getFileName() {
        final ClassFile v1 = this.thisClass.getClassFile2();
        if (v1 == null) {
            return null;
        }
        return v1.getSourceFile();
    }
    
    static final boolean checkResultValue(final CtClass a1, final String a2) throws CannotCompileException {
        final boolean v1 = a2.indexOf("$_") >= 0;
        if (!v1 && a1 != CtClass.voidType) {
            throw new CannotCompileException("the resulting value is not stored in $_");
        }
        return v1;
    }
    
    static final void storeStack(final CtClass[] a1, final boolean a2, final int a3, final Bytecode a4) {
        storeStack0(0, a1.length, a1, a3 + 1, a4);
        if (a2) {
            a4.addOpcode(1);
        }
        a4.addAstore(a3);
    }
    
    private static void storeStack0(final int a4, final int a5, final CtClass[] v1, final int v2, final Bytecode v3) {
        if (a4 >= a5) {
            return;
        }
        final CtClass a6 = v1[a4];
        int a8 = 0;
        if (a6 instanceof CtPrimitiveType) {
            final int a7 = ((CtPrimitiveType)a6).getDataSize();
        }
        else {
            a8 = 1;
        }
        storeStack0(a4 + 1, a5, v1, v2 + a8, v3);
        v3.addStore(v2, a6);
    }
    
    public abstract void replace(final String p0) throws CannotCompileException;
    
    public void replace(final String a1, final ExprEditor a2) throws CannotCompileException {
        this.replace(a1);
        if (a2 != null) {
            this.runEditor(a2, this.iterator);
        }
    }
    
    protected void replace0(int a3, final Bytecode v1, final int v2) throws BadBytecode {
        final byte[] v3 = v1.get();
        this.edited = true;
        final int v4 = v3.length - v2;
        for (int a4 = 0; a4 < v2; ++a4) {
            this.iterator.writeByte(0, a3 + a4);
        }
        if (v4 > 0) {
            a3 = this.iterator.insertGapAt(a3, v4, false).position;
        }
        this.iterator.write(v3, a3);
        this.iterator.insert(v1.getExceptionTable(), a3);
        this.maxLocals = v1.getMaxLocals();
        this.maxStack = v1.getMaxStack();
    }
    
    protected void runEditor(final ExprEditor a1, final CodeIterator a2) throws CannotCompileException {
        final CodeAttribute v1 = a2.get();
        final int v2 = v1.getMaxLocals();
        final int v3 = v1.getMaxStack();
        final int v4 = this.locals();
        v1.setMaxStack(this.stack());
        v1.setMaxLocals(v4);
        final ExprEditor.LoopContext v5 = new ExprEditor.LoopContext(v4);
        final int v6 = a2.getCodeLength();
        final int v7 = a2.lookAhead();
        a2.move(this.currentPos);
        if (a1.doit(this.thisClass, this.thisMethod, v5, a2, v7)) {
            this.edited = true;
        }
        a2.move(v7 + a2.getCodeLength() - v6);
        v1.setMaxLocals(v2);
        v1.setMaxStack(v3);
        this.maxLocals = v5.maxLocals;
        this.maxStack += v5.maxStack;
    }
}
