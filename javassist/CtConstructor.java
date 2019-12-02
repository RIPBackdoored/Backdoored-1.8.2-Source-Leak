package javassist;

import javassist.bytecode.*;
import javassist.compiler.*;

public final class CtConstructor extends CtBehavior
{
    protected CtConstructor(final MethodInfo a1, final CtClass a2) {
        super(a2, a1);
    }
    
    public CtConstructor(final CtClass[] a1, final CtClass a2) {
        this((MethodInfo)null, a2);
        final ConstPool v1 = a2.getClassFile2().getConstPool();
        final String v2 = Descriptor.ofConstructor(a1);
        this.methodInfo = new MethodInfo(v1, "<init>", v2);
        this.setModifiers(1);
    }
    
    public CtConstructor(final CtConstructor a1, final CtClass a2, final ClassMap a3) throws CannotCompileException {
        this((MethodInfo)null, a2);
        this.copy(a1, true, a3);
    }
    
    public boolean isConstructor() {
        return this.methodInfo.isConstructor();
    }
    
    public boolean isClassInitializer() {
        return this.methodInfo.isStaticInitializer();
    }
    
    @Override
    public String getLongName() {
        return this.getDeclaringClass().getName() + (this.isConstructor() ? Descriptor.toString(this.getSignature()) : ".<clinit>()");
    }
    
    @Override
    public String getName() {
        if (this.methodInfo.isStaticInitializer()) {
            return "<clinit>";
        }
        return this.declaringClass.getSimpleName();
    }
    
    @Override
    public boolean isEmpty() {
        final CodeAttribute codeAttribute = this.getMethodInfo2().getCodeAttribute();
        if (codeAttribute == null) {
            return false;
        }
        final ConstPool constPool = codeAttribute.getConstPool();
        final CodeIterator v0 = codeAttribute.iterator();
        try {
            final int v2 = v0.byteAt(v0.next());
            final int v3;
            final int v4;
            return v2 == 177 || (v2 == 42 && v0.byteAt(v3 = v0.next()) == 183 && (v4 = constPool.isConstructor(this.getSuperclassName(), v0.u16bitAt(v3 + 1))) != 0 && "()V".equals(constPool.getUtf8Info(v4)) && v0.byteAt(v0.next()) == 177 && !v0.hasNext());
        }
        catch (BadBytecode badBytecode) {
            return false;
        }
    }
    
    private String getSuperclassName() {
        final ClassFile v1 = this.declaringClass.getClassFile2();
        return v1.getSuperclass();
    }
    
    public boolean callsSuper() throws CannotCompileException {
        final CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute != null) {
            final CodeIterator v0 = codeAttribute.iterator();
            try {
                final int v2 = v0.skipSuperConstructor();
                return v2 >= 0;
            }
            catch (BadBytecode v3) {
                throw new CannotCompileException(v3);
            }
        }
        return false;
    }
    
    @Override
    public void setBody(String a1) throws CannotCompileException {
        if (a1 == null) {
            if (this.isClassInitializer()) {
                a1 = ";";
            }
            else {
                a1 = "super();";
            }
        }
        super.setBody(a1);
    }
    
    public void setBody(final CtConstructor a1, final ClassMap a2) throws CannotCompileException {
        CtBehavior.setBody0(a1.declaringClass, a1.methodInfo, this.declaringClass, this.methodInfo, a2);
    }
    
    public void insertBeforeBody(final String v-5) throws CannotCompileException {
        final CtClass declaringClass = this.declaringClass;
        declaringClass.checkModify();
        if (this.isClassInitializer()) {
            throw new CannotCompileException("class initializer");
        }
        final CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        final CodeIterator iterator = codeAttribute.iterator();
        final Bytecode a2 = new Bytecode(this.methodInfo.getConstPool(), codeAttribute.getMaxStack(), codeAttribute.getMaxLocals());
        a2.setStackDepth(codeAttribute.getMaxStack());
        final Javac v0 = new Javac(a2, declaringClass);
        try {
            v0.recordParams(this.getParameterTypes(), false);
            v0.compileStmnt(v-5);
            codeAttribute.setMaxStack(a2.getMaxStack());
            codeAttribute.setMaxLocals(a2.getMaxLocals());
            iterator.skipConstructor();
            final int a1 = iterator.insertEx(a2.get());
            iterator.insert(a2.getExceptionTable(), a1);
            this.methodInfo.rebuildStackMapIf6(declaringClass.getClassPool(), declaringClass.getClassFile2());
        }
        catch (NotFoundException v2) {
            throw new CannotCompileException(v2);
        }
        catch (CompileError v3) {
            throw new CannotCompileException(v3);
        }
        catch (BadBytecode v4) {
            throw new CannotCompileException(v4);
        }
    }
    
    @Override
    int getStartPosOfBody(final CodeAttribute v2) throws CannotCompileException {
        final CodeIterator v3 = v2.iterator();
        try {
            v3.skipConstructor();
            return v3.next();
        }
        catch (BadBytecode a1) {
            throw new CannotCompileException(a1);
        }
    }
    
    public CtMethod toMethod(final String a1, final CtClass a2) throws CannotCompileException {
        return this.toMethod(a1, a2, null);
    }
    
    public CtMethod toMethod(final String v2, final CtClass v3, final ClassMap v4) throws CannotCompileException {
        final CtMethod v5 = new CtMethod(null, v3);
        v5.copy(this, false, v4);
        if (this.isConstructor()) {
            final MethodInfo a2 = v5.getMethodInfo2();
            final CodeAttribute a3 = a2.getCodeAttribute();
            if (a3 != null) {
                removeConsCall(a3);
                try {
                    this.methodInfo.rebuildStackMapIf6(v3.getClassPool(), v3.getClassFile2());
                }
                catch (BadBytecode a4) {
                    throw new CannotCompileException(a4);
                }
            }
        }
        v5.setName(v2);
        return v5;
    }
    
    private static void removeConsCall(final CodeAttribute v-3) throws CannotCompileException {
        final CodeIterator iterator = v-3.iterator();
        try {
            int n = iterator.skipConstructor();
            if (n >= 0) {
                final int a1 = iterator.u16bitAt(n + 1);
                final String v1 = v-3.getConstPool().getMethodrefType(a1);
                final int v2 = Descriptor.numOfParameters(v1) + 1;
                if (v2 > 3) {
                    n = iterator.insertGapAt(n, v2 - 3, false).position;
                }
                iterator.writeByte(87, n++);
                iterator.writeByte(0, n);
                iterator.writeByte(0, n + 1);
                final Descriptor.Iterator v3 = new Descriptor.Iterator(v1);
                while (true) {
                    v3.next();
                    if (!v3.isParameter()) {
                        break;
                    }
                    iterator.writeByte(v3.is2byte() ? 88 : 87, n++);
                }
            }
        }
        catch (BadBytecode a2) {
            throw new CannotCompileException(a2);
        }
    }
}
