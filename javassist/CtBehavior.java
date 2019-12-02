package javassist;

import java.util.*;
import javassist.compiler.*;
import javassist.expr.*;
import javassist.bytecode.*;

public abstract class CtBehavior extends CtMember
{
    protected MethodInfo methodInfo;
    
    protected CtBehavior(final CtClass a1, final MethodInfo a2) {
        super(a1);
        this.methodInfo = a2;
    }
    
    void copy(final CtBehavior v-8, final boolean v-7, ClassMap v-6) throws CannotCompileException {
        final CtClass declaringClass = this.declaringClass;
        final MethodInfo methodInfo = v-8.methodInfo;
        final CtClass declaringClass2 = v-8.getDeclaringClass();
        final ConstPool constPool = declaringClass.getClassFile2().getConstPool();
        v-6 = new ClassMap(v-6);
        v-6.put(declaringClass2.getName(), declaringClass.getName());
        try {
            boolean a2 = false;
            final CtClass a3 = declaringClass2.getSuperclass();
            final CtClass v1 = declaringClass.getSuperclass();
            String v2 = null;
            if (a3 != null && v1 != null) {
                final String a4 = a3.getName();
                v2 = v1.getName();
                if (!a4.equals(v2)) {
                    if (a4.equals("java.lang.Object")) {
                        a2 = true;
                    }
                    else {
                        v-6.putIfNone(a4, v2);
                    }
                }
            }
            this.methodInfo = new MethodInfo(constPool, methodInfo.getName(), methodInfo, v-6);
            if (v-7 && a2) {
                this.methodInfo.setSuperclass(v2);
            }
        }
        catch (NotFoundException a5) {
            throw new CannotCompileException(a5);
        }
        catch (BadBytecode a6) {
            throw new CannotCompileException(a6);
        }
    }
    
    @Override
    protected void extendToString(final StringBuffer a1) {
        a1.append(' ');
        a1.append(this.getName());
        a1.append(' ');
        a1.append(this.methodInfo.getDescriptor());
    }
    
    public abstract String getLongName();
    
    public MethodInfo getMethodInfo() {
        this.declaringClass.checkModify();
        return this.methodInfo;
    }
    
    public MethodInfo getMethodInfo2() {
        return this.methodInfo;
    }
    
    @Override
    public int getModifiers() {
        return AccessFlag.toModifier(this.methodInfo.getAccessFlags());
    }
    
    @Override
    public void setModifiers(final int a1) {
        this.declaringClass.checkModify();
        this.methodInfo.setAccessFlags(AccessFlag.of(a1));
    }
    
    @Override
    public boolean hasAnnotation(final String a1) {
        final MethodInfo v1 = this.getMethodInfo2();
        final AnnotationsAttribute v2 = (AnnotationsAttribute)v1.getAttribute("RuntimeInvisibleAnnotations");
        final AnnotationsAttribute v3 = (AnnotationsAttribute)v1.getAttribute("RuntimeVisibleAnnotations");
        return CtClassType.hasAnnotationType(a1, this.getDeclaringClass().getClassPool(), v2, v3);
    }
    
    @Override
    public Object getAnnotation(final Class a1) throws ClassNotFoundException {
        final MethodInfo v1 = this.getMethodInfo2();
        final AnnotationsAttribute v2 = (AnnotationsAttribute)v1.getAttribute("RuntimeInvisibleAnnotations");
        final AnnotationsAttribute v3 = (AnnotationsAttribute)v1.getAttribute("RuntimeVisibleAnnotations");
        return CtClassType.getAnnotationType(a1, this.getDeclaringClass().getClassPool(), v2, v3);
    }
    
    @Override
    public Object[] getAnnotations() throws ClassNotFoundException {
        return this.getAnnotations(false);
    }
    
    @Override
    public Object[] getAvailableAnnotations() {
        try {
            return this.getAnnotations(true);
        }
        catch (ClassNotFoundException v1) {
            throw new RuntimeException("Unexpected exception", v1);
        }
    }
    
    private Object[] getAnnotations(final boolean a1) throws ClassNotFoundException {
        final MethodInfo v1 = this.getMethodInfo2();
        final AnnotationsAttribute v2 = (AnnotationsAttribute)v1.getAttribute("RuntimeInvisibleAnnotations");
        final AnnotationsAttribute v3 = (AnnotationsAttribute)v1.getAttribute("RuntimeVisibleAnnotations");
        return CtClassType.toAnnotationType(a1, this.getDeclaringClass().getClassPool(), v2, v3);
    }
    
    public Object[][] getParameterAnnotations() throws ClassNotFoundException {
        return this.getParameterAnnotations(false);
    }
    
    public Object[][] getAvailableParameterAnnotations() {
        try {
            return this.getParameterAnnotations(true);
        }
        catch (ClassNotFoundException v1) {
            throw new RuntimeException("Unexpected exception", v1);
        }
    }
    
    Object[][] getParameterAnnotations(final boolean a1) throws ClassNotFoundException {
        final MethodInfo v1 = this.getMethodInfo2();
        final ParameterAnnotationsAttribute v2 = (ParameterAnnotationsAttribute)v1.getAttribute("RuntimeInvisibleParameterAnnotations");
        final ParameterAnnotationsAttribute v3 = (ParameterAnnotationsAttribute)v1.getAttribute("RuntimeVisibleParameterAnnotations");
        return CtClassType.toAnnotationType(a1, this.getDeclaringClass().getClassPool(), v2, v3, v1);
    }
    
    public CtClass[] getParameterTypes() throws NotFoundException {
        return Descriptor.getParameterTypes(this.methodInfo.getDescriptor(), this.declaringClass.getClassPool());
    }
    
    CtClass getReturnType0() throws NotFoundException {
        return Descriptor.getReturnType(this.methodInfo.getDescriptor(), this.declaringClass.getClassPool());
    }
    
    @Override
    public String getSignature() {
        return this.methodInfo.getDescriptor();
    }
    
    @Override
    public String getGenericSignature() {
        final SignatureAttribute v1 = (SignatureAttribute)this.methodInfo.getAttribute("Signature");
        return (v1 == null) ? null : v1.getSignature();
    }
    
    @Override
    public void setGenericSignature(final String a1) {
        this.declaringClass.checkModify();
        this.methodInfo.addAttribute(new SignatureAttribute(this.methodInfo.getConstPool(), a1));
    }
    
    public CtClass[] getExceptionTypes() throws NotFoundException {
        final ExceptionsAttribute v2 = this.methodInfo.getExceptionsAttribute();
        String[] v3;
        if (v2 == null) {
            v3 = null;
        }
        else {
            v3 = v2.getExceptions();
        }
        return this.declaringClass.getClassPool().get(v3);
    }
    
    public void setExceptionTypes(final CtClass[] v2) throws NotFoundException {
        this.declaringClass.checkModify();
        if (v2 == null || v2.length == 0) {
            this.methodInfo.removeExceptionsAttribute();
            return;
        }
        final String[] v3 = new String[v2.length];
        for (int a1 = 0; a1 < v2.length; ++a1) {
            v3[a1] = v2[a1].getName();
        }
        ExceptionsAttribute v4 = this.methodInfo.getExceptionsAttribute();
        if (v4 == null) {
            v4 = new ExceptionsAttribute(this.methodInfo.getConstPool());
            this.methodInfo.setExceptionsAttribute(v4);
        }
        v4.setExceptions(v3);
    }
    
    public abstract boolean isEmpty();
    
    public void setBody(final String a1) throws CannotCompileException {
        this.setBody(a1, null, null);
    }
    
    public void setBody(final String v-3, final String v-2, final String v-1) throws CannotCompileException {
        final CtClass v0 = this.declaringClass;
        v0.checkModify();
        try {
            final Javac a1 = new Javac(v0);
            if (v-1 != null) {
                a1.recordProceed(v-2, v-1);
            }
            final Bytecode a2 = a1.compileBody(this, v-3);
            this.methodInfo.setCodeAttribute(a2.toCodeAttribute());
            this.methodInfo.setAccessFlags(this.methodInfo.getAccessFlags() & 0xFFFFFBFF);
            this.methodInfo.rebuildStackMapIf6(v0.getClassPool(), v0.getClassFile2());
            this.declaringClass.rebuildClassFile();
        }
        catch (CompileError a3) {
            throw new CannotCompileException(a3);
        }
        catch (BadBytecode v2) {
            throw new CannotCompileException(v2);
        }
    }
    
    static void setBody0(final CtClass a5, final MethodInfo v1, final CtClass v2, final MethodInfo v3, ClassMap v4) throws CannotCompileException {
        v2.checkModify();
        v4 = new ClassMap(v4);
        v4.put(a5.getName(), v2.getName());
        try {
            final CodeAttribute a6 = v1.getCodeAttribute();
            if (a6 != null) {
                final ConstPool a7 = v3.getConstPool();
                final CodeAttribute a8 = (CodeAttribute)a6.copy(a7, v4);
                v3.setCodeAttribute(a8);
            }
        }
        catch (CodeAttribute.RuntimeCopyException a9) {
            throw new CannotCompileException(a9);
        }
        v3.setAccessFlags(v3.getAccessFlags() & 0xFFFFFBFF);
        v2.rebuildClassFile();
    }
    
    @Override
    public byte[] getAttribute(final String a1) {
        final AttributeInfo v1 = this.methodInfo.getAttribute(a1);
        if (v1 == null) {
            return null;
        }
        return v1.get();
    }
    
    @Override
    public void setAttribute(final String a1, final byte[] a2) {
        this.declaringClass.checkModify();
        this.methodInfo.addAttribute(new AttributeInfo(this.methodInfo.getConstPool(), a1, a2));
    }
    
    public void useCflow(final String v-4) throws CannotCompileException {
        final CtClass declaringClass = this.declaringClass;
        declaringClass.checkModify();
        final ClassPool classPool = declaringClass.getClassPool();
        int v0 = 0;
        while (true) {
            final String string = "_cflow$" + v0++;
            try {
                declaringClass.getDeclaredField(string);
            }
            catch (NotFoundException a1) {
                classPool.recordCflow(v-4, this.declaringClass.getName(), string);
                try {
                    final CtClass v2 = classPool.get("javassist.runtime.Cflow");
                    final CtField v3 = new CtField(v2, string, declaringClass);
                    v3.setModifiers(9);
                    declaringClass.addField(v3, CtField.Initializer.byNew(v2));
                    this.insertBefore(string + ".enter();", false);
                    final String v4 = string + ".exit();";
                    this.insertAfter(v4, true);
                }
                catch (NotFoundException v5) {
                    throw new CannotCompileException(v5);
                }
            }
        }
    }
    
    public void addLocalVariable(final String a1, final CtClass a2) throws CannotCompileException {
        this.declaringClass.checkModify();
        final ConstPool v1 = this.methodInfo.getConstPool();
        final CodeAttribute v2 = this.methodInfo.getCodeAttribute();
        if (v2 == null) {
            throw new CannotCompileException("no method body");
        }
        LocalVariableAttribute v3 = (LocalVariableAttribute)v2.getAttribute("LocalVariableTable");
        if (v3 == null) {
            v3 = new LocalVariableAttribute(v1);
            v2.getAttributes().add(v3);
        }
        final int v4 = v2.getMaxLocals();
        final String v5 = Descriptor.of(a2);
        v3.addEntry(0, v2.getCodeLength(), v1.addUtf8Info(a1), v1.addUtf8Info(v5), v4);
        v2.setMaxLocals(v4 + Descriptor.dataSize(v5));
    }
    
    public void insertParameter(final CtClass v2) throws CannotCompileException {
        this.declaringClass.checkModify();
        final String v3 = this.methodInfo.getDescriptor();
        final String v4 = Descriptor.insertParameter(v2, v3);
        try {
            this.addParameter2(Modifier.isStatic(this.getModifiers()) ? 0 : 1, v2, v3);
        }
        catch (BadBytecode a1) {
            throw new CannotCompileException(a1);
        }
        this.methodInfo.setDescriptor(v4);
    }
    
    public void addParameter(final CtClass v2) throws CannotCompileException {
        this.declaringClass.checkModify();
        final String v3 = this.methodInfo.getDescriptor();
        final String v4 = Descriptor.appendParameter(v2, v3);
        final int v5 = Modifier.isStatic(this.getModifiers()) ? 0 : 1;
        try {
            this.addParameter2(v5 + Descriptor.paramSize(v3), v2, v3);
        }
        catch (BadBytecode a1) {
            throw new CannotCompileException(a1);
        }
        this.methodInfo.setDescriptor(v4);
    }
    
    private void addParameter2(final int v-5, final CtClass v-4, final String v-3) throws BadBytecode {
        final CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute != null) {
            int a2 = 1;
            char a3 = 'L';
            int v1 = 0;
            if (v-4.isPrimitive()) {
                final CtPrimitiveType a4 = (CtPrimitiveType)v-4;
                a2 = a4.getDataSize();
                a3 = a4.getDescriptor();
            }
            else {
                v1 = this.methodInfo.getConstPool().addClassInfo(v-4);
            }
            codeAttribute.insertLocalVar(v-5, a2);
            final LocalVariableAttribute v2 = (LocalVariableAttribute)codeAttribute.getAttribute("LocalVariableTable");
            if (v2 != null) {
                v2.shiftIndex(v-5, a2);
            }
            final LocalVariableTypeAttribute v3 = (LocalVariableTypeAttribute)codeAttribute.getAttribute("LocalVariableTypeTable");
            if (v3 != null) {
                v3.shiftIndex(v-5, a2);
            }
            final StackMapTable v4 = (StackMapTable)codeAttribute.getAttribute("StackMapTable");
            if (v4 != null) {
                v4.insertLocal(v-5, StackMapTable.typeTagOf(a3), v1);
            }
            final StackMap v5 = (StackMap)codeAttribute.getAttribute("StackMap");
            if (v5 != null) {
                v5.insertLocal(v-5, StackMapTable.typeTagOf(a3), v1);
            }
        }
    }
    
    public void instrument(final CodeConverter a1) throws CannotCompileException {
        this.declaringClass.checkModify();
        final ConstPool v1 = this.methodInfo.getConstPool();
        a1.doit(this.getDeclaringClass(), this.methodInfo, v1);
    }
    
    public void instrument(final ExprEditor a1) throws CannotCompileException {
        if (this.declaringClass.isFrozen()) {
            this.declaringClass.checkModify();
        }
        if (a1.doit(this.declaringClass, this.methodInfo)) {
            this.declaringClass.checkModify();
        }
    }
    
    public void insertBefore(final String a1) throws CannotCompileException {
        this.insertBefore(a1, true);
    }
    
    private void insertBefore(final String v-7, final boolean v-6) throws CannotCompileException {
        final CtClass declaringClass = this.declaringClass;
        declaringClass.checkModify();
        final CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            throw new CannotCompileException("no method body");
        }
        final CodeIterator iterator = codeAttribute.iterator();
        final Javac javac = new Javac(declaringClass);
        try {
            final int a1 = javac.recordParams(this.getParameterTypes(), Modifier.isStatic(this.getModifiers()));
            javac.recordParamNames(codeAttribute, a1);
            javac.recordLocalVariables(codeAttribute, 0);
            javac.recordType(this.getReturnType0());
            javac.compileStmnt(v-7);
            final Bytecode a2 = javac.getBytecode();
            final int v1 = a2.getMaxStack();
            final int v2 = a2.getMaxLocals();
            if (v1 > codeAttribute.getMaxStack()) {
                codeAttribute.setMaxStack(v1);
            }
            if (v2 > codeAttribute.getMaxLocals()) {
                codeAttribute.setMaxLocals(v2);
            }
            final int v3 = iterator.insertEx(a2.get());
            iterator.insert(a2.getExceptionTable(), v3);
            if (v-6) {
                this.methodInfo.rebuildStackMapIf6(declaringClass.getClassPool(), declaringClass.getClassFile2());
            }
        }
        catch (NotFoundException a3) {
            throw new CannotCompileException(a3);
        }
        catch (CompileError a4) {
            throw new CannotCompileException(a4);
        }
        catch (BadBytecode a5) {
            throw new CannotCompileException(a5);
        }
    }
    
    public void insertAfter(final String a1) throws CannotCompileException {
        this.insertAfter(a1, false);
    }
    
    public void insertAfter(final String v-8, final boolean v-7) throws CannotCompileException {
        final CtClass declaringClass = this.declaringClass;
        declaringClass.checkModify();
        final ConstPool constPool = this.methodInfo.getConstPool();
        final CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            throw new CannotCompileException("no method body");
        }
        final CodeIterator iterator = codeAttribute.iterator();
        final int maxLocals = codeAttribute.getMaxLocals();
        final Bytecode a3 = new Bytecode(constPool, 0, maxLocals + 1);
        a3.setStackDepth(codeAttribute.getMaxStack() + 1);
        final Javac v0 = new Javac(a3, declaringClass);
        try {
            final int v2 = v0.recordParams(this.getParameterTypes(), Modifier.isStatic(this.getModifiers()));
            v0.recordParamNames(codeAttribute, v2);
            final CtClass v3 = this.getReturnType0();
            final int v4 = v0.recordReturnType(v3, true);
            v0.recordLocalVariables(codeAttribute, 0);
            int v5 = this.insertAfterHandler(v-7, a3, v3, v4, v0, v-8);
            int v6 = iterator.getCodeLength();
            if (v-7) {
                codeAttribute.getExceptionTable().add(this.getStartPosOfBody(codeAttribute), v6, v6, 0);
            }
            int v7 = 0;
            int v8 = 0;
            boolean v9 = true;
            while (iterator.hasNext()) {
                final int a1 = iterator.next();
                if (a1 >= v6) {
                    break;
                }
                final int a2 = iterator.byteAt(a1);
                if (a2 != 176 && a2 != 172 && a2 != 174 && a2 != 173 && a2 != 175 && a2 != 177) {
                    continue;
                }
                if (v9) {
                    v7 = this.insertAfterAdvice(a3, v0, v-8, constPool, v3, v4);
                    v6 = iterator.append(a3.get());
                    iterator.append(a3.getExceptionTable(), v6);
                    v8 = iterator.getCodeLength() - v7;
                    v5 = v8 - v6;
                    v9 = false;
                }
                this.insertGoto(iterator, v8, a1);
                v8 = iterator.getCodeLength() - v7;
                v6 = v8 - v5;
            }
            if (v9) {
                v6 = iterator.append(a3.get());
                iterator.append(a3.getExceptionTable(), v6);
            }
            codeAttribute.setMaxStack(a3.getMaxStack());
            codeAttribute.setMaxLocals(a3.getMaxLocals());
            this.methodInfo.rebuildStackMapIf6(declaringClass.getClassPool(), declaringClass.getClassFile2());
        }
        catch (NotFoundException v10) {
            throw new CannotCompileException(v10);
        }
        catch (CompileError v11) {
            throw new CannotCompileException(v11);
        }
        catch (BadBytecode v12) {
            throw new CannotCompileException(v12);
        }
    }
    
    private int insertAfterAdvice(final Bytecode a1, final Javac a2, final String a3, final ConstPool a4, final CtClass a5, final int a6) throws CompileError {
        final int v1 = a1.currentPc();
        if (a5 == CtClass.voidType) {
            a1.addOpcode(1);
            a1.addAstore(a6);
            a2.compileStmnt(a3);
            a1.addOpcode(177);
            if (a1.getMaxLocals() < 1) {
                a1.setMaxLocals(1);
            }
        }
        else {
            a1.addStore(a6, a5);
            a2.compileStmnt(a3);
            a1.addLoad(a6, a5);
            if (a5.isPrimitive()) {
                a1.addOpcode(((CtPrimitiveType)a5).getReturnOp());
            }
            else {
                a1.addOpcode(176);
            }
        }
        return a1.currentPc() - v1;
    }
    
    private void insertGoto(final CodeIterator a3, final int v1, int v2) throws BadBytecode {
        a3.setMark(v1);
        a3.writeByte(0, v2);
        final boolean v3 = v1 + 2 - v2 > 32767;
        final int v4 = v3 ? 4 : 2;
        final CodeIterator.Gap v5 = a3.insertGapAt(v2, v4, false);
        v2 = v5.position + v5.length - v4;
        final int v6 = a3.getMark() - v2;
        if (v3) {
            a3.writeByte(200, v2);
            a3.write32bit(v6, v2 + 1);
        }
        else if (v6 <= 32767) {
            a3.writeByte(167, v2);
            a3.write16bit(v6, v2 + 1);
        }
        else {
            if (v5.length < 4) {
                final CodeIterator.Gap a4 = a3.insertGapAt(v5.position, 2, false);
                v2 = a4.position + a4.length + v5.length - 4;
            }
            a3.writeByte(200, v2);
            a3.write32bit(a3.getMark() - v2, v2 + 1);
        }
    }
    
    private int insertAfterHandler(final boolean a3, final Bytecode a4, final CtClass a5, final int a6, final Javac v1, final String v2) throws CompileError {
        if (!a3) {
            return 0;
        }
        final int v3 = a4.getMaxLocals();
        a4.incMaxLocals(1);
        final int v4 = a4.currentPc();
        a4.addAstore(v3);
        if (a5.isPrimitive()) {
            final char a7 = ((CtPrimitiveType)a5).getDescriptor();
            if (a7 == 'D') {
                a4.addDconst(0.0);
                a4.addDstore(a6);
            }
            else if (a7 == 'F') {
                a4.addFconst(0.0f);
                a4.addFstore(a6);
            }
            else if (a7 == 'J') {
                a4.addLconst(0L);
                a4.addLstore(a6);
            }
            else if (a7 == 'V') {
                a4.addOpcode(1);
                a4.addAstore(a6);
            }
            else {
                a4.addIconst(0);
                a4.addIstore(a6);
            }
        }
        else {
            a4.addOpcode(1);
            a4.addAstore(a6);
        }
        v1.compileStmnt(v2);
        a4.addAload(v3);
        a4.addOpcode(191);
        return a4.currentPc() - v4;
    }
    
    public void addCatch(final String a1, final CtClass a2) throws CannotCompileException {
        this.addCatch(a1, a2, "$e");
    }
    
    public void addCatch(final String v-11, final CtClass v-10, final String v-9) throws CannotCompileException {
        final CtClass declaringClass = this.declaringClass;
        declaringClass.checkModify();
        final ConstPool constPool = this.methodInfo.getConstPool();
        final CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        final CodeIterator iterator = codeAttribute.iterator();
        final Bytecode a4 = new Bytecode(constPool, codeAttribute.getMaxStack(), codeAttribute.getMaxLocals());
        a4.setStackDepth(1);
        final Javac javac = new Javac(a4, declaringClass);
        try {
            javac.recordParams(this.getParameterTypes(), Modifier.isStatic(this.getModifiers()));
            final int a1 = javac.recordVariable(v-10, v-9);
            a4.addAstore(a1);
            javac.compileStmnt(v-11);
            final int a2 = a4.getMaxStack();
            final int a3 = a4.getMaxLocals();
            if (a2 > codeAttribute.getMaxStack()) {
                codeAttribute.setMaxStack(a2);
            }
            if (a3 > codeAttribute.getMaxLocals()) {
                codeAttribute.setMaxLocals(a3);
            }
            final int v1 = iterator.getCodeLength();
            final int v2 = iterator.append(a4.get());
            codeAttribute.getExceptionTable().add(this.getStartPosOfBody(codeAttribute), v1, v1, constPool.addClassInfo(v-10));
            iterator.append(a4.getExceptionTable(), v2);
            this.methodInfo.rebuildStackMapIf6(declaringClass.getClassPool(), declaringClass.getClassFile2());
        }
        catch (NotFoundException a5) {
            throw new CannotCompileException(a5);
        }
        catch (CompileError a6) {
            throw new CannotCompileException(a6);
        }
        catch (BadBytecode a7) {
            throw new CannotCompileException(a7);
        }
    }
    
    int getStartPosOfBody(final CodeAttribute a1) throws CannotCompileException {
        return 0;
    }
    
    public int insertAt(final int a1, final String a2) throws CannotCompileException {
        return this.insertAt(a1, true, a2);
    }
    
    public int insertAt(int v-9, final boolean v-8, final String v-7) throws CannotCompileException {
        final CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            throw new CannotCompileException("no method body");
        }
        final LineNumberAttribute lineNumberAttribute = (LineNumberAttribute)codeAttribute.getAttribute("LineNumberTable");
        if (lineNumberAttribute == null) {
            throw new CannotCompileException("no line number info");
        }
        final LineNumberAttribute.Pc nearPc = lineNumberAttribute.toNearPc(v-9);
        v-9 = nearPc.line;
        int a4 = nearPc.index;
        if (!v-8) {
            return v-9;
        }
        final CtClass declaringClass = this.declaringClass;
        declaringClass.checkModify();
        final CodeIterator iterator = codeAttribute.iterator();
        final Javac v0 = new Javac(declaringClass);
        try {
            v0.recordLocalVariables(codeAttribute, a4);
            v0.recordParams(this.getParameterTypes(), Modifier.isStatic(this.getModifiers()));
            v0.setMaxLocals(codeAttribute.getMaxLocals());
            v0.compileStmnt(v-7);
            final Bytecode a1 = v0.getBytecode();
            final int a2 = a1.getMaxLocals();
            final int a3 = a1.getMaxStack();
            codeAttribute.setMaxLocals(a2);
            if (a3 > codeAttribute.getMaxStack()) {
                codeAttribute.setMaxStack(a3);
            }
            a4 = iterator.insertAt(a4, a1.get());
            iterator.insert(a1.getExceptionTable(), a4);
            this.methodInfo.rebuildStackMapIf6(declaringClass.getClassPool(), declaringClass.getClassFile2());
            return v-9;
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
}
