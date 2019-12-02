package javassist;

import java.util.*;
import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.compiler.*;

public class CtField extends CtMember
{
    static final String javaLangString = "java.lang.String";
    protected FieldInfo fieldInfo;
    
    public CtField(final CtClass a1, final String a2, final CtClass a3) throws CannotCompileException {
        this(Descriptor.of(a1), a2, a3);
    }
    
    public CtField(final CtField v1, final CtClass v2) throws CannotCompileException {
        this(v1.fieldInfo.getDescriptor(), v1.fieldInfo.getName(), v2);
        final ListIterator v3 = v1.fieldInfo.getAttributes().listIterator();
        final FieldInfo v4 = this.fieldInfo;
        v4.setAccessFlags(v1.fieldInfo.getAccessFlags());
        final ConstPool v5 = v4.getConstPool();
        while (v3.hasNext()) {
            final AttributeInfo a1 = v3.next();
            v4.addAttribute(a1.copy(v5, null));
        }
    }
    
    private CtField(final String a1, final String a2, final CtClass a3) throws CannotCompileException {
        super(a3);
        final ClassFile v1 = a3.getClassFile2();
        if (v1 == null) {
            throw new CannotCompileException("bad declaring class: " + a3.getName());
        }
        this.fieldInfo = new FieldInfo(v1.getConstPool(), a2, a1);
    }
    
    CtField(final FieldInfo a1, final CtClass a2) {
        super(a2);
        this.fieldInfo = a1;
    }
    
    @Override
    public String toString() {
        return this.getDeclaringClass().getName() + "." + this.getName() + ":" + this.fieldInfo.getDescriptor();
    }
    
    @Override
    protected void extendToString(final StringBuffer a1) {
        a1.append(' ');
        a1.append(this.getName());
        a1.append(' ');
        a1.append(this.fieldInfo.getDescriptor());
    }
    
    protected ASTree getInitAST() {
        return null;
    }
    
    Initializer getInit() {
        final ASTree v1 = this.getInitAST();
        if (v1 == null) {
            return null;
        }
        return Initializer.byExpr(v1);
    }
    
    public static CtField make(final String v1, final CtClass v2) throws CannotCompileException {
        final Javac v3 = new Javac(v2);
        try {
            final CtMember a1 = v3.compile(v1);
            if (a1 instanceof CtField) {
                return (CtField)a1;
            }
        }
        catch (CompileError a2) {
            throw new CannotCompileException(a2);
        }
        throw new CannotCompileException("not a field");
    }
    
    public FieldInfo getFieldInfo() {
        this.declaringClass.checkModify();
        return this.fieldInfo;
    }
    
    public FieldInfo getFieldInfo2() {
        return this.fieldInfo;
    }
    
    @Override
    public CtClass getDeclaringClass() {
        return super.getDeclaringClass();
    }
    
    @Override
    public String getName() {
        return this.fieldInfo.getName();
    }
    
    public void setName(final String a1) {
        this.declaringClass.checkModify();
        this.fieldInfo.setName(a1);
    }
    
    @Override
    public int getModifiers() {
        return AccessFlag.toModifier(this.fieldInfo.getAccessFlags());
    }
    
    @Override
    public void setModifiers(final int a1) {
        this.declaringClass.checkModify();
        this.fieldInfo.setAccessFlags(AccessFlag.of(a1));
    }
    
    @Override
    public boolean hasAnnotation(final String a1) {
        final FieldInfo v1 = this.getFieldInfo2();
        final AnnotationsAttribute v2 = (AnnotationsAttribute)v1.getAttribute("RuntimeInvisibleAnnotations");
        final AnnotationsAttribute v3 = (AnnotationsAttribute)v1.getAttribute("RuntimeVisibleAnnotations");
        return CtClassType.hasAnnotationType(a1, this.getDeclaringClass().getClassPool(), v2, v3);
    }
    
    @Override
    public Object getAnnotation(final Class a1) throws ClassNotFoundException {
        final FieldInfo v1 = this.getFieldInfo2();
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
        final FieldInfo v1 = this.getFieldInfo2();
        final AnnotationsAttribute v2 = (AnnotationsAttribute)v1.getAttribute("RuntimeInvisibleAnnotations");
        final AnnotationsAttribute v3 = (AnnotationsAttribute)v1.getAttribute("RuntimeVisibleAnnotations");
        return CtClassType.toAnnotationType(a1, this.getDeclaringClass().getClassPool(), v2, v3);
    }
    
    @Override
    public String getSignature() {
        return this.fieldInfo.getDescriptor();
    }
    
    @Override
    public String getGenericSignature() {
        final SignatureAttribute v1 = (SignatureAttribute)this.fieldInfo.getAttribute("Signature");
        return (v1 == null) ? null : v1.getSignature();
    }
    
    @Override
    public void setGenericSignature(final String a1) {
        this.declaringClass.checkModify();
        this.fieldInfo.addAttribute(new SignatureAttribute(this.fieldInfo.getConstPool(), a1));
    }
    
    public CtClass getType() throws NotFoundException {
        return Descriptor.toCtClass(this.fieldInfo.getDescriptor(), this.declaringClass.getClassPool());
    }
    
    public void setType(final CtClass a1) {
        this.declaringClass.checkModify();
        this.fieldInfo.setDescriptor(Descriptor.of(a1));
    }
    
    public Object getConstantValue() {
        final int constantValue = this.fieldInfo.getConstantValue();
        if (constantValue == 0) {
            return null;
        }
        final ConstPool v0 = this.fieldInfo.getConstPool();
        switch (v0.getTag(constantValue)) {
            case 5: {
                return new Long(v0.getLongInfo(constantValue));
            }
            case 4: {
                return new Float(v0.getFloatInfo(constantValue));
            }
            case 6: {
                return new Double(v0.getDoubleInfo(constantValue));
            }
            case 3: {
                final int v2 = v0.getIntegerInfo(constantValue);
                if ("Z".equals(this.fieldInfo.getDescriptor())) {
                    return new Boolean(v2 != 0);
                }
                return new Integer(v2);
            }
            case 8: {
                return v0.getStringInfo(constantValue);
            }
            default: {
                throw new RuntimeException("bad tag: " + v0.getTag(constantValue) + " at " + constantValue);
            }
        }
    }
    
    @Override
    public byte[] getAttribute(final String a1) {
        final AttributeInfo v1 = this.fieldInfo.getAttribute(a1);
        if (v1 == null) {
            return null;
        }
        return v1.get();
    }
    
    @Override
    public void setAttribute(final String a1, final byte[] a2) {
        this.declaringClass.checkModify();
        this.fieldInfo.addAttribute(new AttributeInfo(this.fieldInfo.getConstPool(), a1, a2));
    }
    
    public abstract static class Initializer
    {
        public Initializer() {
            super();
        }
        
        public static Initializer constant(final int a1) {
            return new IntInitializer(a1);
        }
        
        public static Initializer constant(final boolean a1) {
            return new IntInitializer(a1 ? 1 : 0);
        }
        
        public static Initializer constant(final long a1) {
            return new LongInitializer(a1);
        }
        
        public static Initializer constant(final float a1) {
            return new FloatInitializer(a1);
        }
        
        public static Initializer constant(final double a1) {
            return new DoubleInitializer(a1);
        }
        
        public static Initializer constant(final String a1) {
            return new StringInitializer(a1);
        }
        
        public static Initializer byParameter(final int a1) {
            final ParamInitializer v1 = new ParamInitializer();
            v1.nthParam = a1;
            return v1;
        }
        
        public static Initializer byNew(final CtClass a1) {
            final NewInitializer v1 = new NewInitializer();
            v1.objectType = a1;
            v1.stringParams = null;
            v1.withConstructorParams = false;
            return v1;
        }
        
        public static Initializer byNew(final CtClass a1, final String[] a2) {
            final NewInitializer v1 = new NewInitializer();
            v1.objectType = a1;
            v1.stringParams = a2;
            v1.withConstructorParams = false;
            return v1;
        }
        
        public static Initializer byNewWithParams(final CtClass a1) {
            final NewInitializer v1 = new NewInitializer();
            v1.objectType = a1;
            v1.stringParams = null;
            v1.withConstructorParams = true;
            return v1;
        }
        
        public static Initializer byNewWithParams(final CtClass a1, final String[] a2) {
            final NewInitializer v1 = new NewInitializer();
            v1.objectType = a1;
            v1.stringParams = a2;
            v1.withConstructorParams = true;
            return v1;
        }
        
        public static Initializer byCall(final CtClass a1, final String a2) {
            final MethodInitializer v1 = new MethodInitializer();
            v1.objectType = a1;
            v1.methodName = a2;
            v1.stringParams = null;
            v1.withConstructorParams = false;
            return v1;
        }
        
        public static Initializer byCall(final CtClass a1, final String a2, final String[] a3) {
            final MethodInitializer v1 = new MethodInitializer();
            v1.objectType = a1;
            v1.methodName = a2;
            v1.stringParams = a3;
            v1.withConstructorParams = false;
            return v1;
        }
        
        public static Initializer byCallWithParams(final CtClass a1, final String a2) {
            final MethodInitializer v1 = new MethodInitializer();
            v1.objectType = a1;
            v1.methodName = a2;
            v1.stringParams = null;
            v1.withConstructorParams = true;
            return v1;
        }
        
        public static Initializer byCallWithParams(final CtClass a1, final String a2, final String[] a3) {
            final MethodInitializer v1 = new MethodInitializer();
            v1.objectType = a1;
            v1.methodName = a2;
            v1.stringParams = a3;
            v1.withConstructorParams = true;
            return v1;
        }
        
        public static Initializer byNewArray(final CtClass a1, final int a2) throws NotFoundException {
            return new ArrayInitializer(a1.getComponentType(), a2);
        }
        
        public static Initializer byNewArray(final CtClass a1, final int[] a2) {
            return new MultiArrayInitializer(a1, a2);
        }
        
        public static Initializer byExpr(final String a1) {
            return new CodeInitializer(a1);
        }
        
        static Initializer byExpr(final ASTree a1) {
            return new PtreeInitializer(a1);
        }
        
        void check(final String a1) throws CannotCompileException {
        }
        
        abstract int compile(final CtClass p0, final String p1, final Bytecode p2, final CtClass[] p3, final Javac p4) throws CannotCompileException;
        
        abstract int compileIfStatic(final CtClass p0, final String p1, final Bytecode p2, final Javac p3) throws CannotCompileException;
        
        int getConstantValue(final ConstPool a1, final CtClass a2) {
            return 0;
        }
    }
    
    abstract static class CodeInitializer0 extends Initializer
    {
        CodeInitializer0() {
            super();
        }
        
        abstract void compileExpr(final Javac p0) throws CompileError;
        
        @Override
        int compile(final CtClass a3, final String a4, final Bytecode a5, final CtClass[] v1, final Javac v2) throws CannotCompileException {
            try {
                a5.addAload(0);
                this.compileExpr(v2);
                a5.addPutfield(Bytecode.THIS, a4, Descriptor.of(a3));
                return a5.getMaxStack();
            }
            catch (CompileError a6) {
                throw new CannotCompileException(a6);
            }
        }
        
        @Override
        int compileIfStatic(final CtClass a3, final String a4, final Bytecode v1, final Javac v2) throws CannotCompileException {
            try {
                this.compileExpr(v2);
                v1.addPutstatic(Bytecode.THIS, a4, Descriptor.of(a3));
                return v1.getMaxStack();
            }
            catch (CompileError a5) {
                throw new CannotCompileException(a5);
            }
        }
        
        int getConstantValue2(final ConstPool v1, final CtClass v2, final ASTree v3) {
            if (v2.isPrimitive()) {
                if (v3 instanceof IntConst) {
                    final long a1 = ((IntConst)v3).get();
                    if (v2 == CtClass.doubleType) {
                        return v1.addDoubleInfo((double)a1);
                    }
                    if (v2 == CtClass.floatType) {
                        return v1.addFloatInfo((float)a1);
                    }
                    if (v2 == CtClass.longType) {
                        return v1.addLongInfo(a1);
                    }
                    if (v2 != CtClass.voidType) {
                        return v1.addIntegerInfo((int)a1);
                    }
                }
                else if (v3 instanceof DoubleConst) {
                    final double a2 = ((DoubleConst)v3).get();
                    if (v2 == CtClass.floatType) {
                        return v1.addFloatInfo((float)a2);
                    }
                    if (v2 == CtClass.doubleType) {
                        return v1.addDoubleInfo(a2);
                    }
                }
            }
            else if (v3 instanceof StringL && v2.getName().equals("java.lang.String")) {
                return v1.addStringInfo(((StringL)v3).get());
            }
            return 0;
        }
    }
    
    static class CodeInitializer extends CodeInitializer0
    {
        private String expression;
        
        CodeInitializer(final String a1) {
            super();
            this.expression = a1;
        }
        
        @Override
        void compileExpr(final Javac a1) throws CompileError {
            a1.compileExpr(this.expression);
        }
        
        @Override
        int getConstantValue(final ConstPool v2, final CtClass v3) {
            try {
                final ASTree a1 = Javac.parseExpr(this.expression, new SymbolTable());
                return this.getConstantValue2(v2, v3, a1);
            }
            catch (CompileError a2) {
                return 0;
            }
        }
    }
    
    static class PtreeInitializer extends CodeInitializer0
    {
        private ASTree expression;
        
        PtreeInitializer(final ASTree a1) {
            super();
            this.expression = a1;
        }
        
        @Override
        void compileExpr(final Javac a1) throws CompileError {
            a1.compileExpr(this.expression);
        }
        
        @Override
        int getConstantValue(final ConstPool a1, final CtClass a2) {
            return this.getConstantValue2(a1, a2, this.expression);
        }
    }
    
    static class ParamInitializer extends Initializer
    {
        int nthParam;
        
        ParamInitializer() {
            super();
        }
        
        @Override
        int compile(final CtClass a4, final String a5, final Bytecode v1, final CtClass[] v2, final Javac v3) throws CannotCompileException {
            if (v2 != null && this.nthParam < v2.length) {
                v1.addAload(0);
                final int a6 = nthParamToLocal(this.nthParam, v2, false);
                final int a7 = v1.addLoad(a6, a4) + 1;
                v1.addPutfield(Bytecode.THIS, a5, Descriptor.of(a4));
                return a7;
            }
            return 0;
        }
        
        static int nthParamToLocal(final int v1, final CtClass[] v2, final boolean v3) {
            final CtClass v4 = CtClass.longType;
            final CtClass v5 = CtClass.doubleType;
            int v6 = 0;
            if (v3) {
                final int a1 = 0;
            }
            else {
                v6 = 1;
            }
            for (final CtClass a3 : v2) {
                if (a3 == v4 || a3 == v5) {
                    v6 += 2;
                }
                else {
                    ++v6;
                }
            }
            return v6;
        }
        
        @Override
        int compileIfStatic(final CtClass a1, final String a2, final Bytecode a3, final Javac a4) throws CannotCompileException {
            return 0;
        }
    }
    
    static class NewInitializer extends Initializer
    {
        CtClass objectType;
        String[] stringParams;
        boolean withConstructorParams;
        
        NewInitializer() {
            super();
        }
        
        @Override
        int compile(final CtClass a3, final String a4, final Bytecode a5, final CtClass[] v1, final Javac v2) throws CannotCompileException {
            a5.addAload(0);
            a5.addNew(this.objectType);
            a5.add(89);
            a5.addAload(0);
            int v3 = 0;
            if (this.stringParams == null) {
                final int a6 = 4;
            }
            else {
                v3 = this.compileStringParameter(a5) + 4;
            }
            if (this.withConstructorParams) {
                v3 += CtNewWrappedMethod.compileParameterList(a5, v1, 1);
            }
            a5.addInvokespecial(this.objectType, "<init>", this.getDescriptor());
            a5.addPutfield(Bytecode.THIS, a4, Descriptor.of(a3));
            return v3;
        }
        
        private String getDescriptor() {
            final String v1 = "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)V";
            if (this.stringParams == null) {
                if (this.withConstructorParams) {
                    return "(Ljava/lang/Object;[Ljava/lang/Object;)V";
                }
                return "(Ljava/lang/Object;)V";
            }
            else {
                if (this.withConstructorParams) {
                    return "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)V";
                }
                return "(Ljava/lang/Object;[Ljava/lang/String;)V";
            }
        }
        
        @Override
        int compileIfStatic(final CtClass a3, final String a4, final Bytecode v1, final Javac v2) throws CannotCompileException {
            v1.addNew(this.objectType);
            v1.add(89);
            int v3 = 2;
            String v4 = null;
            if (this.stringParams == null) {
                final String a5 = "()V";
            }
            else {
                v4 = "([Ljava/lang/String;)V";
                v3 += this.compileStringParameter(v1);
            }
            v1.addInvokespecial(this.objectType, "<init>", v4);
            v1.addPutstatic(Bytecode.THIS, a4, Descriptor.of(a3));
            return v3;
        }
        
        protected final int compileStringParameter(final Bytecode v2) throws CannotCompileException {
            final int v3 = this.stringParams.length;
            v2.addIconst(v3);
            v2.addAnewarray("java.lang.String");
            for (int a1 = 0; a1 < v3; ++a1) {
                v2.add(89);
                v2.addIconst(a1);
                v2.addLdc(this.stringParams[a1]);
                v2.add(83);
            }
            return 4;
        }
    }
    
    static class MethodInitializer extends NewInitializer
    {
        String methodName;
        
        MethodInitializer() {
            super();
        }
        
        @Override
        int compile(final CtClass a3, final String a4, final Bytecode a5, final CtClass[] v1, final Javac v2) throws CannotCompileException {
            a5.addAload(0);
            a5.addAload(0);
            int v3 = 0;
            if (this.stringParams == null) {
                final int a6 = 2;
            }
            else {
                v3 = this.compileStringParameter(a5) + 2;
            }
            if (this.withConstructorParams) {
                v3 += CtNewWrappedMethod.compileParameterList(a5, v1, 1);
            }
            final String v4 = Descriptor.of(a3);
            final String v5 = this.getDescriptor() + v4;
            a5.addInvokestatic(this.objectType, this.methodName, v5);
            a5.addPutfield(Bytecode.THIS, a4, v4);
            return v3;
        }
        
        private String getDescriptor() {
            final String v1 = "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)";
            if (this.stringParams == null) {
                if (this.withConstructorParams) {
                    return "(Ljava/lang/Object;[Ljava/lang/Object;)";
                }
                return "(Ljava/lang/Object;)";
            }
            else {
                if (this.withConstructorParams) {
                    return "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)";
                }
                return "(Ljava/lang/Object;[Ljava/lang/String;)";
            }
        }
        
        @Override
        int compileIfStatic(final CtClass a3, final String a4, final Bytecode v1, final Javac v2) throws CannotCompileException {
            int v3 = 1;
            String v4 = null;
            if (this.stringParams == null) {
                final String a5 = "()";
            }
            else {
                v4 = "([Ljava/lang/String;)";
                v3 += this.compileStringParameter(v1);
            }
            final String v5 = Descriptor.of(a3);
            v1.addInvokestatic(this.objectType, this.methodName, v4 + v5);
            v1.addPutstatic(Bytecode.THIS, a4, v5);
            return v3;
        }
    }
    
    static class IntInitializer extends Initializer
    {
        int value;
        
        IntInitializer(final int a1) {
            super();
            this.value = a1;
        }
        
        @Override
        void check(final String a1) throws CannotCompileException {
            final char v1 = a1.charAt(0);
            if (v1 != 'I' && v1 != 'S' && v1 != 'B' && v1 != 'C' && v1 != 'Z') {
                throw new CannotCompileException("type mismatch");
            }
        }
        
        @Override
        int compile(final CtClass a1, final String a2, final Bytecode a3, final CtClass[] a4, final Javac a5) throws CannotCompileException {
            a3.addAload(0);
            a3.addIconst(this.value);
            a3.addPutfield(Bytecode.THIS, a2, Descriptor.of(a1));
            return 2;
        }
        
        @Override
        int compileIfStatic(final CtClass a1, final String a2, final Bytecode a3, final Javac a4) throws CannotCompileException {
            a3.addIconst(this.value);
            a3.addPutstatic(Bytecode.THIS, a2, Descriptor.of(a1));
            return 1;
        }
        
        @Override
        int getConstantValue(final ConstPool a1, final CtClass a2) {
            return a1.addIntegerInfo(this.value);
        }
    }
    
    static class LongInitializer extends Initializer
    {
        long value;
        
        LongInitializer(final long a1) {
            super();
            this.value = a1;
        }
        
        @Override
        void check(final String a1) throws CannotCompileException {
            if (!a1.equals("J")) {
                throw new CannotCompileException("type mismatch");
            }
        }
        
        @Override
        int compile(final CtClass a1, final String a2, final Bytecode a3, final CtClass[] a4, final Javac a5) throws CannotCompileException {
            a3.addAload(0);
            a3.addLdc2w(this.value);
            a3.addPutfield(Bytecode.THIS, a2, Descriptor.of(a1));
            return 3;
        }
        
        @Override
        int compileIfStatic(final CtClass a1, final String a2, final Bytecode a3, final Javac a4) throws CannotCompileException {
            a3.addLdc2w(this.value);
            a3.addPutstatic(Bytecode.THIS, a2, Descriptor.of(a1));
            return 2;
        }
        
        @Override
        int getConstantValue(final ConstPool a1, final CtClass a2) {
            if (a2 == CtClass.longType) {
                return a1.addLongInfo(this.value);
            }
            return 0;
        }
    }
    
    static class FloatInitializer extends Initializer
    {
        float value;
        
        FloatInitializer(final float a1) {
            super();
            this.value = a1;
        }
        
        @Override
        void check(final String a1) throws CannotCompileException {
            if (!a1.equals("F")) {
                throw new CannotCompileException("type mismatch");
            }
        }
        
        @Override
        int compile(final CtClass a1, final String a2, final Bytecode a3, final CtClass[] a4, final Javac a5) throws CannotCompileException {
            a3.addAload(0);
            a3.addFconst(this.value);
            a3.addPutfield(Bytecode.THIS, a2, Descriptor.of(a1));
            return 3;
        }
        
        @Override
        int compileIfStatic(final CtClass a1, final String a2, final Bytecode a3, final Javac a4) throws CannotCompileException {
            a3.addFconst(this.value);
            a3.addPutstatic(Bytecode.THIS, a2, Descriptor.of(a1));
            return 2;
        }
        
        @Override
        int getConstantValue(final ConstPool a1, final CtClass a2) {
            if (a2 == CtClass.floatType) {
                return a1.addFloatInfo(this.value);
            }
            return 0;
        }
    }
    
    static class DoubleInitializer extends Initializer
    {
        double value;
        
        DoubleInitializer(final double a1) {
            super();
            this.value = a1;
        }
        
        @Override
        void check(final String a1) throws CannotCompileException {
            if (!a1.equals("D")) {
                throw new CannotCompileException("type mismatch");
            }
        }
        
        @Override
        int compile(final CtClass a1, final String a2, final Bytecode a3, final CtClass[] a4, final Javac a5) throws CannotCompileException {
            a3.addAload(0);
            a3.addLdc2w(this.value);
            a3.addPutfield(Bytecode.THIS, a2, Descriptor.of(a1));
            return 3;
        }
        
        @Override
        int compileIfStatic(final CtClass a1, final String a2, final Bytecode a3, final Javac a4) throws CannotCompileException {
            a3.addLdc2w(this.value);
            a3.addPutstatic(Bytecode.THIS, a2, Descriptor.of(a1));
            return 2;
        }
        
        @Override
        int getConstantValue(final ConstPool a1, final CtClass a2) {
            if (a2 == CtClass.doubleType) {
                return a1.addDoubleInfo(this.value);
            }
            return 0;
        }
    }
    
    static class StringInitializer extends Initializer
    {
        String value;
        
        StringInitializer(final String a1) {
            super();
            this.value = a1;
        }
        
        @Override
        int compile(final CtClass a1, final String a2, final Bytecode a3, final CtClass[] a4, final Javac a5) throws CannotCompileException {
            a3.addAload(0);
            a3.addLdc(this.value);
            a3.addPutfield(Bytecode.THIS, a2, Descriptor.of(a1));
            return 2;
        }
        
        @Override
        int compileIfStatic(final CtClass a1, final String a2, final Bytecode a3, final Javac a4) throws CannotCompileException {
            a3.addLdc(this.value);
            a3.addPutstatic(Bytecode.THIS, a2, Descriptor.of(a1));
            return 1;
        }
        
        @Override
        int getConstantValue(final ConstPool a1, final CtClass a2) {
            if (a2.getName().equals("java.lang.String")) {
                return a1.addStringInfo(this.value);
            }
            return 0;
        }
    }
    
    static class ArrayInitializer extends Initializer
    {
        CtClass type;
        int size;
        
        ArrayInitializer(final CtClass a1, final int a2) {
            super();
            this.type = a1;
            this.size = a2;
        }
        
        private void addNewarray(final Bytecode a1) {
            if (this.type.isPrimitive()) {
                a1.addNewarray(((CtPrimitiveType)this.type).getArrayType(), this.size);
            }
            else {
                a1.addAnewarray(this.type, this.size);
            }
        }
        
        @Override
        int compile(final CtClass a1, final String a2, final Bytecode a3, final CtClass[] a4, final Javac a5) throws CannotCompileException {
            a3.addAload(0);
            this.addNewarray(a3);
            a3.addPutfield(Bytecode.THIS, a2, Descriptor.of(a1));
            return 2;
        }
        
        @Override
        int compileIfStatic(final CtClass a1, final String a2, final Bytecode a3, final Javac a4) throws CannotCompileException {
            this.addNewarray(a3);
            a3.addPutstatic(Bytecode.THIS, a2, Descriptor.of(a1));
            return 1;
        }
    }
    
    static class MultiArrayInitializer extends Initializer
    {
        CtClass type;
        int[] dim;
        
        MultiArrayInitializer(final CtClass a1, final int[] a2) {
            super();
            this.type = a1;
            this.dim = a2;
        }
        
        @Override
        void check(final String a1) throws CannotCompileException {
            if (a1.charAt(0) != '[') {
                throw new CannotCompileException("type mismatch");
            }
        }
        
        @Override
        int compile(final CtClass a1, final String a2, final Bytecode a3, final CtClass[] a4, final Javac a5) throws CannotCompileException {
            a3.addAload(0);
            final int v1 = a3.addMultiNewarray(a1, this.dim);
            a3.addPutfield(Bytecode.THIS, a2, Descriptor.of(a1));
            return v1 + 1;
        }
        
        @Override
        int compileIfStatic(final CtClass a1, final String a2, final Bytecode a3, final Javac a4) throws CannotCompileException {
            final int v1 = a3.addMultiNewarray(a1, this.dim);
            a3.addPutstatic(Bytecode.THIS, a2, Descriptor.of(a1));
            return v1;
        }
    }
}
