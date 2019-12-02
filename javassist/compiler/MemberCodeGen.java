package javassist.compiler;

import java.util.*;
import javassist.*;
import javassist.bytecode.*;
import javassist.compiler.ast.*;

public class MemberCodeGen extends CodeGen
{
    protected MemberResolver resolver;
    protected CtClass thisClass;
    protected MethodInfo thisMethod;
    protected boolean resultStatic;
    
    public MemberCodeGen(final Bytecode a1, final CtClass a2, final ClassPool a3) {
        super(a1);
        this.resolver = new MemberResolver(a3);
        this.thisClass = a2;
        this.thisMethod = null;
    }
    
    public int getMajorVersion() {
        final ClassFile v1 = this.thisClass.getClassFile2();
        if (v1 == null) {
            return ClassFile.MAJOR_VERSION;
        }
        return v1.getMajorVersion();
    }
    
    public void setThisMethod(final CtMethod a1) {
        this.thisMethod = a1.getMethodInfo2();
        if (this.typeChecker != null) {
            this.typeChecker.setThisMethod(this.thisMethod);
        }
    }
    
    public CtClass getThisClass() {
        return this.thisClass;
    }
    
    @Override
    protected String getThisName() {
        return MemberResolver.javaToJvmName(this.thisClass.getName());
    }
    
    @Override
    protected String getSuperName() throws CompileError {
        return MemberResolver.javaToJvmName(MemberResolver.getSuperclass(this.thisClass).getName());
    }
    
    @Override
    protected void insertDefaultSuperCall() throws CompileError {
        this.bytecode.addAload(0);
        this.bytecode.addInvokespecial(MemberResolver.getSuperclass(this.thisClass), "<init>", "()V");
    }
    
    @Override
    protected void atTryStmnt(final Stmnt v-11) throws CompileError {
        final Bytecode bytecode = this.bytecode;
        final Stmnt stmnt = (Stmnt)v-11.getLeft();
        if (stmnt == null) {
            return;
        }
        ASTList tail = (ASTList)v-11.getRight().getLeft();
        final Stmnt v-12 = (Stmnt)v-11.getRight().getRight().getLeft();
        final ArrayList v5 = new ArrayList();
        JsrHook jsrHook = null;
        if (v-12 != null) {
            jsrHook = new JsrHook(this);
        }
        final int currentPc = bytecode.currentPc();
        stmnt.accept(this);
        final int currentPc2 = bytecode.currentPc();
        if (currentPc == currentPc2) {
            throw new CompileError("empty try block");
        }
        boolean b = !this.hasReturned;
        if (b) {
            bytecode.addOpcode(167);
            v5.add(new Integer(bytecode.currentPc()));
            bytecode.addIndex(0);
        }
        final int maxLocals = this.getMaxLocals();
        this.incMaxLocals(1);
        while (tail != null) {
            final Pair a1 = (Pair)tail.head();
            tail = tail.tail();
            final Declarator v1 = (Declarator)a1.getLeft();
            final Stmnt v2 = (Stmnt)a1.getRight();
            v1.setLocalVar(maxLocals);
            final CtClass v3 = this.resolver.lookupClassByJvmName(v1.getClassName());
            v1.setClassName(MemberResolver.javaToJvmName(v3.getName()));
            bytecode.addExceptionHandler(currentPc, currentPc2, bytecode.currentPc(), v3);
            bytecode.growStack(1);
            bytecode.addAstore(maxLocals);
            this.hasReturned = false;
            if (v2 != null) {
                v2.accept(this);
            }
            if (!this.hasReturned) {
                bytecode.addOpcode(167);
                v5.add(new Integer(bytecode.currentPc()));
                bytecode.addIndex(0);
                b = true;
            }
        }
        if (v-12 != null) {
            jsrHook.remove(this);
            final int v4 = bytecode.currentPc();
            bytecode.addExceptionHandler(currentPc, v4, v4, 0);
            bytecode.growStack(1);
            bytecode.addAstore(maxLocals);
            this.hasReturned = false;
            v-12.accept(this);
            if (!this.hasReturned) {
                bytecode.addAload(maxLocals);
                bytecode.addOpcode(191);
            }
            this.addFinally(jsrHook.jsrList, v-12);
        }
        final int v4 = bytecode.currentPc();
        this.patchGoto(v5, v4);
        this.hasReturned = !b;
        if (v-12 != null && b) {
            v-12.accept(this);
        }
    }
    
    private void addFinally(final ArrayList v-6, final Stmnt v-5) throws CompileError {
        final Bytecode bytecode = this.bytecode;
        for (int size = v-6.size(), i = 0; i < size; ++i) {
            final int[] a1 = v-6.get(i);
            final int a2 = a1[0];
            bytecode.write16bit(a2, bytecode.currentPc() - a2 + 1);
            final ReturnHook v1 = new JsrHook2(this, a1);
            v-5.accept(this);
            v1.remove(this);
            if (!this.hasReturned) {
                bytecode.addOpcode(167);
                bytecode.addIndex(a2 + 3 - bytecode.currentPc());
            }
        }
    }
    
    @Override
    public void atNewExpr(final NewExpr v-1) throws CompileError {
        if (v-1.isArray()) {
            this.atNewArrayExpr(v-1);
        }
        else {
            final CtClass a1 = this.resolver.lookupClassByName(v-1.getClassName());
            final String v1 = a1.getName();
            final ASTList v2 = v-1.getArguments();
            this.bytecode.addNew(v1);
            this.bytecode.addOpcode(89);
            this.atMethodCallCore(a1, "<init>", v2, false, true, -1, null);
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = MemberResolver.javaToJvmName(v1);
        }
    }
    
    public void atNewArrayExpr(final NewExpr a1) throws CompileError {
        final int v1 = a1.getArrayType();
        final ASTList v2 = a1.getArraySize();
        final ASTList v3 = a1.getClassName();
        final ArrayInit v4 = a1.getInitializer();
        if (v2.length() <= 1) {
            final ASTree v5 = v2.head();
            this.atNewArrayExpr2(v1, v5, Declarator.astToClassName(v3, '/'), v4);
            return;
        }
        if (v4 != null) {
            throw new CompileError("sorry, multi-dimensional array initializer for new is not supported");
        }
        this.atMultiNewArray(v1, v3, v2);
    }
    
    private void atNewArrayExpr2(final int v-4, final ASTree v-3, final String v-2, final ArrayInit v-1) throws CompileError {
        if (v-1 == null) {
            if (v-3 == null) {
                throw new CompileError("no array size");
            }
            v-3.accept(this);
        }
        else {
            if (v-3 != null) {
                throw new CompileError("unnecessary array size specified for new");
            }
            final int a1 = v-1.length();
            this.bytecode.addIconst(a1);
        }
        String v0 = null;
        if (v-4 == 307) {
            final String a2 = this.resolveClassName(v-2);
            this.bytecode.addAnewarray(MemberResolver.jvmToJavaName(a2));
        }
        else {
            v0 = null;
            int a3 = 0;
            switch (v-4) {
                case 301: {
                    a3 = 4;
                    break;
                }
                case 306: {
                    a3 = 5;
                    break;
                }
                case 317: {
                    a3 = 6;
                    break;
                }
                case 312: {
                    a3 = 7;
                    break;
                }
                case 303: {
                    a3 = 8;
                    break;
                }
                case 334: {
                    a3 = 9;
                    break;
                }
                case 324: {
                    a3 = 10;
                    break;
                }
                case 326: {
                    a3 = 11;
                    break;
                }
                default: {
                    badNewExpr();
                    break;
                }
            }
            this.bytecode.addOpcode(188);
            this.bytecode.add(a3);
        }
        if (v-1 != null) {
            final int v2 = v-1.length();
            ASTList v3 = v-1;
            for (int a4 = 0; a4 < v2; ++a4) {
                this.bytecode.addOpcode(89);
                this.bytecode.addIconst(a4);
                v3.head().accept(this);
                if (!CodeGen.isRefType(v-4)) {
                    this.atNumCastExpr(this.exprType, v-4);
                }
                this.bytecode.addOpcode(CodeGen.getArrayWriteOp(v-4, 0));
                v3 = v3.tail();
            }
        }
        this.exprType = v-4;
        this.arrayDim = 1;
        this.className = v0;
    }
    
    private static void badNewExpr() throws CompileError {
        throw new CompileError("bad new expression");
    }
    
    @Override
    protected void atArrayVariableAssign(final ArrayInit a1, final int a2, final int a3, final String a4) throws CompileError {
        this.atNewArrayExpr2(a2, null, a4, a1);
    }
    
    @Override
    public void atArrayInit(final ArrayInit a1) throws CompileError {
        throw new CompileError("array initializer is not supported");
    }
    
    protected void atMultiNewArray(final int v1, final ASTList v2, ASTList v3) throws CompileError {
        final int v4 = v3.length();
        int v5 = 0;
        while (v3 != null) {
            final ASTree a1 = v3.head();
            if (a1 == null) {
                break;
            }
            ++v5;
            a1.accept(this);
            if (this.exprType != 324) {
                throw new CompileError("bad type for array size");
            }
            v3 = v3.tail();
        }
        this.exprType = v1;
        this.arrayDim = v4;
        String v6 = null;
        if (v1 == 307) {
            this.className = this.resolveClassName(v2);
            final String a2 = CodeGen.toJvmArrayName(this.className, v4);
        }
        else {
            v6 = CodeGen.toJvmTypeName(v1, v4);
        }
        this.bytecode.addMultiNewarray(v6, v5);
    }
    
    @Override
    public void atCallExpr(final CallExpr v-10) throws CompileError {
        String a2 = null;
        CtClass ctClass = null;
        final ASTree oprand1 = v-10.oprand1();
        final ASTList a3 = (ASTList)v-10.oprand2();
        boolean a4 = false;
        boolean v4 = false;
        int v5 = -1;
        final MemberResolver.Method method = v-10.getMethod();
        if (oprand1 instanceof Member) {
            a2 = ((Member)oprand1).get();
            ctClass = this.thisClass;
            if (this.inStaticMethod || (method != null && method.isStatic())) {
                a4 = true;
            }
            else {
                v5 = this.bytecode.currentPc();
                this.bytecode.addAload(0);
            }
        }
        else if (oprand1 instanceof Keyword) {
            v4 = true;
            a2 = "<init>";
            ctClass = this.thisClass;
            if (this.inStaticMethod) {
                throw new CompileError("a constructor cannot be static");
            }
            this.bytecode.addAload(0);
            if (((Keyword)oprand1).get() == 336) {
                ctClass = MemberResolver.getSuperclass(ctClass);
            }
        }
        else if (oprand1 instanceof Expr) {
            final Expr expr = (Expr)oprand1;
            a2 = ((Symbol)expr.oprand2()).get();
            final int v0 = expr.getOperator();
            if (v0 == 35) {
                ctClass = this.resolver.lookupClass(((Symbol)expr.oprand1()).get(), false);
                a4 = true;
            }
            else if (v0 == 46) {
                final ASTree v2 = expr.oprand1();
                final String v3 = TypeChecker.isDotSuper(v2);
                if (v3 != null) {
                    v4 = true;
                    ctClass = MemberResolver.getSuperInterface(this.thisClass, v3);
                    if (this.inStaticMethod || (method != null && method.isStatic())) {
                        a4 = true;
                    }
                    else {
                        v5 = this.bytecode.currentPc();
                        this.bytecode.addAload(0);
                    }
                }
                else {
                    if (v2 instanceof Keyword && ((Keyword)v2).get() == 336) {
                        v4 = true;
                    }
                    try {
                        v2.accept(this);
                    }
                    catch (NoFieldException a1) {
                        if (a1.getExpr() != v2) {
                            throw a1;
                        }
                        this.exprType = 307;
                        this.arrayDim = 0;
                        this.className = a1.getField();
                        a4 = true;
                    }
                    if (this.arrayDim > 0) {
                        ctClass = this.resolver.lookupClass("java.lang.Object", true);
                    }
                    else if (this.exprType == 307) {
                        ctClass = this.resolver.lookupClassByJvmName(this.className);
                    }
                    else {
                        badMethod();
                    }
                }
            }
            else {
                badMethod();
            }
        }
        else {
            fatal();
        }
        this.atMethodCallCore(ctClass, a2, a3, a4, v4, v5, method);
    }
    
    private static void badMethod() throws CompileError {
        throw new CompileError("bad method");
    }
    
    public void atMethodCallCore(final CtClass a4, final String a5, final ASTList a6, boolean a7, final boolean v1, final int v2, MemberResolver.Method v3) throws CompileError {
        final int v4 = this.getMethodArgsLength(a6);
        final int[] v5 = new int[v4];
        final int[] v6 = new int[v4];
        final String[] v7 = new String[v4];
        if (!a7 && v3 != null && v3.isStatic()) {
            this.bytecode.addOpcode(87);
            a7 = true;
        }
        final int v8 = this.bytecode.getStackDepth();
        this.atMethodArgs(a6, v5, v6, v7);
        if (v3 == null) {
            v3 = this.resolver.lookupMethod(a4, this.thisClass, this.thisMethod, a5, v5, v6, v7);
        }
        if (v3 == null) {
            String a9 = null;
            if (a5.equals("<init>")) {
                final String a8 = "constructor not found";
            }
            else {
                a9 = "Method " + a5 + " not found in " + a4.getName();
            }
            throw new CompileError(a9);
        }
        this.atMethodCallCore2(a4, a5, a7, v1, v2, v3);
    }
    
    private void atMethodCallCore2(final CtClass a4, String a5, boolean a6, boolean v1, final int v2, final MemberResolver.Method v3) throws CompileError {
        CtClass v4 = v3.declaring;
        final MethodInfo v5 = v3.info;
        String v6 = v5.getDescriptor();
        int v7 = v5.getAccessFlags();
        if (a5.equals("<init>")) {
            v1 = true;
            if (v4 != a4) {
                throw new CompileError("no such constructor: " + a4.getName());
            }
            if (v4 != this.thisClass && AccessFlag.isPrivate(v7)) {
                v6 = this.getAccessibleConstructor(v6, v4, v5);
                this.bytecode.addOpcode(1);
            }
        }
        else if (AccessFlag.isPrivate(v7)) {
            if (v4 == this.thisClass) {
                v1 = true;
            }
            else {
                v1 = false;
                a6 = true;
                final String a7 = v6;
                if ((v7 & 0x8) == 0x0) {
                    v6 = Descriptor.insertParameter(v4.getName(), a7);
                }
                v7 = (AccessFlag.setPackage(v7) | 0x8);
                a5 = this.getAccessiblePrivate(a5, a7, v6, v5, v4);
            }
        }
        boolean v8 = false;
        if ((v7 & 0x8) != 0x0) {
            if (!a6) {
                a6 = true;
                if (v2 >= 0) {
                    this.bytecode.write(v2, 0);
                }
                else {
                    v8 = true;
                }
            }
            this.bytecode.addInvokestatic(v4, a5, v6);
        }
        else if (v1) {
            this.bytecode.addInvokespecial(a4, a5, v6);
        }
        else {
            if (!Modifier.isPublic(v4.getModifiers()) || v4.isInterface() != a4.isInterface()) {
                v4 = a4;
            }
            if (v4.isInterface()) {
                final int a8 = Descriptor.paramSize(v6) + 1;
                this.bytecode.addInvokeinterface(v4, a5, v6, a8);
            }
            else {
                if (a6) {
                    throw new CompileError(a5 + " is not static");
                }
                this.bytecode.addInvokevirtual(v4, a5, v6);
            }
        }
        this.setReturnType(v6, a6, v8);
    }
    
    protected String getAccessiblePrivate(final String a3, final String a4, final String a5, final MethodInfo v1, final CtClass v2) throws CompileError {
        if (this.isEnclosing(v2, this.thisClass)) {
            final AccessorMaker a6 = v2.getAccessorMaker();
            if (a6 != null) {
                return a6.getMethodAccessor(a3, a4, a5, v1);
            }
        }
        throw new CompileError("Method " + a3 + " is private");
    }
    
    protected String getAccessibleConstructor(final String a3, final CtClass v1, final MethodInfo v2) throws CompileError {
        if (this.isEnclosing(v1, this.thisClass)) {
            final AccessorMaker a4 = v1.getAccessorMaker();
            if (a4 != null) {
                return a4.getConstructor(v1, a3, v2);
            }
        }
        throw new CompileError("the called constructor is private in " + v1.getName());
    }
    
    private boolean isEnclosing(final CtClass a1, CtClass a2) {
        try {
            while (a2 != null) {
                a2 = a2.getDeclaringClass();
                if (a2 == a1) {
                    return true;
                }
            }
        }
        catch (NotFoundException ex) {}
        return false;
    }
    
    public int getMethodArgsLength(final ASTList a1) {
        return ASTList.length(a1);
    }
    
    public void atMethodArgs(ASTList a3, final int[] a4, final int[] v1, final String[] v2) throws CompileError {
        int v3 = 0;
        while (a3 != null) {
            final ASTree a5 = a3.head();
            a5.accept(this);
            a4[v3] = this.exprType;
            v1[v3] = this.arrayDim;
            v2[v3] = this.className;
            ++v3;
            a3 = a3.tail();
        }
    }
    
    void setReturnType(final String a3, final boolean v1, final boolean v2) throws CompileError {
        int v3 = a3.indexOf(41);
        if (v3 < 0) {
            badMethod();
        }
        char v4 = a3.charAt(++v3);
        int v5 = 0;
        while (v4 == '[') {
            ++v5;
            v4 = a3.charAt(++v3);
        }
        this.arrayDim = v5;
        if (v4 == 'L') {
            final int a4 = a3.indexOf(59, v3 + 1);
            if (a4 < 0) {
                badMethod();
            }
            this.exprType = 307;
            this.className = a3.substring(v3 + 1, a4);
        }
        else {
            this.exprType = MemberResolver.descToType(v4);
            this.className = null;
        }
        final int v6 = this.exprType;
        if (v1 && v2) {
            if (CodeGen.is2word(v6, v5)) {
                this.bytecode.addOpcode(93);
                this.bytecode.addOpcode(88);
                this.bytecode.addOpcode(87);
            }
            else if (v6 == 344) {
                this.bytecode.addOpcode(87);
            }
            else {
                this.bytecode.addOpcode(95);
                this.bytecode.addOpcode(87);
            }
        }
    }
    
    @Override
    protected void atFieldAssign(final Expr v-11, final int v-10, final ASTree v-9, final ASTree v-8, final boolean v-7) throws CompileError {
        final CtField fieldAccess = this.fieldAccess(v-9, false);
        final boolean resultStatic = this.resultStatic;
        if (v-10 != 61 && !resultStatic) {
            this.bytecode.addOpcode(89);
        }
        int atFieldRead;
        if (v-10 == 61) {
            final FieldInfo a2 = fieldAccess.getFieldInfo2();
            this.setFieldType(a2);
            final AccessorMaker a3 = this.isAccessibleField(fieldAccess, a2);
            if (a3 == null) {
                final int a4 = this.addFieldrefInfo(fieldAccess, a2);
            }
            else {
                final int a5 = 0;
            }
        }
        else {
            atFieldRead = this.atFieldRead(fieldAccess, resultStatic);
        }
        final int exprType = this.exprType;
        final int arrayDim = this.arrayDim;
        final String className = this.className;
        this.atAssignCore(v-11, v-10, v-8, exprType, arrayDim, className);
        final boolean v0 = CodeGen.is2word(exprType, arrayDim);
        if (v-7) {
            int v2 = 0;
            if (resultStatic) {
                final int a6 = v0 ? 92 : 89;
            }
            else {
                v2 = (v0 ? 93 : 90);
            }
            this.bytecode.addOpcode(v2);
        }
        this.atFieldAssignCore(fieldAccess, resultStatic, atFieldRead, v0);
        this.exprType = exprType;
        this.arrayDim = arrayDim;
        this.className = className;
    }
    
    private void atFieldAssignCore(final CtField v2, final boolean v3, final int v4, final boolean v5) throws CompileError {
        if (v4 != 0) {
            if (v3) {
                this.bytecode.add(179);
                this.bytecode.growStack(v5 ? -2 : -1);
            }
            else {
                this.bytecode.add(181);
                this.bytecode.growStack(v5 ? -3 : -2);
            }
            this.bytecode.addIndex(v4);
        }
        else {
            final CtClass a1 = v2.getDeclaringClass();
            final AccessorMaker a2 = a1.getAccessorMaker();
            final FieldInfo a3 = v2.getFieldInfo2();
            final MethodInfo a4 = a2.getFieldSetter(a3, v3);
            this.bytecode.addInvokestatic(a1, a4.getName(), a4.getDescriptor());
        }
    }
    
    @Override
    public void atMember(final Member a1) throws CompileError {
        this.atFieldRead(a1);
    }
    
    @Override
    protected void atFieldRead(final ASTree a1) throws CompileError {
        final CtField v1 = this.fieldAccess(a1, true);
        if (v1 == null) {
            this.atArrayLength(a1);
            return;
        }
        final boolean v2 = this.resultStatic;
        final ASTree v3 = TypeChecker.getConstantFieldValue(v1);
        if (v3 == null) {
            this.atFieldRead(v1, v2);
        }
        else {
            v3.accept(this);
            this.setFieldType(v1.getFieldInfo2());
        }
    }
    
    private void atArrayLength(final ASTree a1) throws CompileError {
        if (this.arrayDim == 0) {
            throw new CompileError(".length applied to a non array");
        }
        this.bytecode.addOpcode(190);
        this.exprType = 324;
        this.arrayDim = 0;
    }
    
    private int atFieldRead(final CtField v2, final boolean v3) throws CompileError {
        final FieldInfo v4 = v2.getFieldInfo2();
        final boolean v5 = this.setFieldType(v4);
        final AccessorMaker v6 = this.isAccessibleField(v2, v4);
        if (v6 != null) {
            final MethodInfo a1 = v6.getFieldGetter(v4, v3);
            this.bytecode.addInvokestatic(v2.getDeclaringClass(), a1.getName(), a1.getDescriptor());
            return 0;
        }
        final int a2 = this.addFieldrefInfo(v2, v4);
        if (v3) {
            this.bytecode.add(178);
            this.bytecode.growStack(v5 ? 2 : 1);
        }
        else {
            this.bytecode.add(180);
            this.bytecode.growStack(v5 ? 1 : 0);
        }
        this.bytecode.addIndex(a2);
        return a2;
    }
    
    private AccessorMaker isAccessibleField(final CtField v2, final FieldInfo v3) throws CompileError {
        if (!AccessFlag.isPrivate(v3.getAccessFlags()) || v2.getDeclaringClass() == this.thisClass) {
            return null;
        }
        final CtClass a2 = v2.getDeclaringClass();
        if (!this.isEnclosing(a2, this.thisClass)) {
            throw new CompileError("Field " + v2.getName() + " in " + a2.getName() + " is private.");
        }
        final AccessorMaker a3 = a2.getAccessorMaker();
        if (a3 != null) {
            return a3;
        }
        throw new CompileError("fatal error.  bug?");
    }
    
    private boolean setFieldType(final FieldInfo a1) throws CompileError {
        final String v1 = a1.getDescriptor();
        int v2 = 0;
        int v3 = 0;
        char v4;
        for (v4 = v1.charAt(v2); v4 == '['; v4 = v1.charAt(++v2)) {
            ++v3;
        }
        this.arrayDim = v3;
        this.exprType = MemberResolver.descToType(v4);
        if (v4 == 'L') {
            this.className = v1.substring(v2 + 1, v1.indexOf(59, v2 + 1));
        }
        else {
            this.className = null;
        }
        final boolean v5 = v3 == 0 && (v4 == 'J' || v4 == 'D');
        return v5;
    }
    
    private int addFieldrefInfo(final CtField a1, final FieldInfo a2) {
        final ConstPool v1 = this.bytecode.getConstPool();
        final String v2 = a1.getDeclaringClass().getName();
        final int v3 = v1.addClassInfo(v2);
        final String v4 = a2.getName();
        final String v5 = a2.getDescriptor();
        return v1.addFieldrefInfo(v3, v4, v5);
    }
    
    @Override
    protected void atClassObject2(final String a1) throws CompileError {
        if (this.getMajorVersion() < 49) {
            super.atClassObject2(a1);
        }
        else {
            this.bytecode.addLdc(this.bytecode.getConstPool().addClassInfo(a1));
        }
    }
    
    @Override
    protected void atFieldPlusPlus(final int a3, final boolean a4, final ASTree a5, final Expr v1, final boolean v2) throws CompileError {
        final CtField v3 = this.fieldAccess(a5, false);
        final boolean v4 = this.resultStatic;
        if (!v4) {
            this.bytecode.addOpcode(89);
        }
        final int v5 = this.atFieldRead(v3, v4);
        final int v6 = this.exprType;
        final boolean v7 = CodeGen.is2word(v6, this.arrayDim);
        int v8 = 0;
        if (v4) {
            final int a6 = v7 ? 92 : 89;
        }
        else {
            v8 = (v7 ? 93 : 90);
        }
        this.atPlusPlusCore(v8, v2, a3, a4, v1);
        this.atFieldAssignCore(v3, v4, v5, v7);
    }
    
    protected CtField fieldAccess(final ASTree v-2, final boolean v-1) throws CompileError {
        if (v-2 instanceof Member) {
            final String a2 = ((Member)v-2).get();
            CtField v1 = null;
            try {
                v1 = this.thisClass.getField(a2);
            }
            catch (NotFoundException a3) {
                throw new NoFieldException(a2, v-2);
            }
            final boolean v2 = Modifier.isStatic(v1.getModifiers());
            if (!v2) {
                if (this.inStaticMethod) {
                    throw new CompileError("not available in a static method: " + a2);
                }
                this.bytecode.addAload(0);
            }
            this.resultStatic = v2;
            return v1;
        }
        if (v-2 instanceof Expr) {
            final Expr v3 = (Expr)v-2;
            final int v4 = v3.getOperator();
            if (v4 == 35) {
                final CtField v5 = this.resolver.lookupField(((Symbol)v3.oprand1()).get(), (Symbol)v3.oprand2());
                this.resultStatic = true;
                return v5;
            }
            if (v4 == 46) {
                CtField v5 = null;
                try {
                    v3.oprand1().accept(this);
                    if (this.exprType == 307 && this.arrayDim == 0) {
                        v5 = this.resolver.lookupFieldByJvmName(this.className, (Symbol)v3.oprand2());
                    }
                    else {
                        if (v-1 && this.arrayDim > 0 && ((Symbol)v3.oprand2()).get().equals("length")) {
                            return null;
                        }
                        badLvalue();
                    }
                    final boolean v6 = Modifier.isStatic(v5.getModifiers());
                    if (v6) {
                        this.bytecode.addOpcode(87);
                    }
                    this.resultStatic = v6;
                    return v5;
                }
                catch (NoFieldException v7) {
                    if (v7.getExpr() != v3.oprand1()) {
                        throw v7;
                    }
                    final Symbol v8 = (Symbol)v3.oprand2();
                    final String v9 = v7.getField();
                    v5 = this.resolver.lookupFieldByJvmName2(v9, v8, v-2);
                    this.resultStatic = true;
                    return v5;
                }
            }
            badLvalue();
        }
        else {
            badLvalue();
        }
        this.resultStatic = false;
        return null;
    }
    
    private static void badLvalue() throws CompileError {
        throw new CompileError("bad l-value");
    }
    
    public CtClass[] makeParamList(final MethodDecl v-2) throws CompileError {
        ASTList v0 = v-2.getParams();
        CtClass[] array = null;
        if (v0 == null) {
            final CtClass[] a1 = new CtClass[0];
        }
        else {
            int v2 = 0;
            array = new CtClass[v0.length()];
            while (v0 != null) {
                array[v2++] = this.resolver.lookupClass((Declarator)v0.head());
                v0 = v0.tail();
            }
        }
        return array;
    }
    
    public CtClass[] makeThrowsList(final MethodDecl v2) throws CompileError {
        ASTList v3 = v2.getThrows();
        if (v3 == null) {
            return null;
        }
        int a1 = 0;
        final CtClass[] v4 = new CtClass[v3.length()];
        while (v3 != null) {
            v4[a1++] = this.resolver.lookupClassByName((ASTList)v3.head());
            v3 = v3.tail();
        }
        return v4;
    }
    
    @Override
    protected String resolveClassName(final ASTList a1) throws CompileError {
        return this.resolver.resolveClassName(a1);
    }
    
    @Override
    protected String resolveClassName(final String a1) throws CompileError {
        return this.resolver.resolveJvmClassName(a1);
    }
    
    static class JsrHook extends ReturnHook
    {
        ArrayList jsrList;
        CodeGen cgen;
        int var;
        
        JsrHook(final CodeGen a1) {
            super(a1);
            this.jsrList = new ArrayList();
            this.cgen = a1;
            this.var = -1;
        }
        
        private int getVar(final int a1) {
            if (this.var < 0) {
                this.var = this.cgen.getMaxLocals();
                this.cgen.incMaxLocals(a1);
            }
            return this.var;
        }
        
        private void jsrJmp(final Bytecode a1) {
            a1.addOpcode(167);
            this.jsrList.add(new int[] { a1.currentPc(), this.var });
            a1.addIndex(0);
        }
        
        @Override
        protected boolean doit(final Bytecode a1, final int a2) {
            switch (a2) {
                case 177: {
                    this.jsrJmp(a1);
                    break;
                }
                case 176: {
                    a1.addAstore(this.getVar(1));
                    this.jsrJmp(a1);
                    a1.addAload(this.var);
                    break;
                }
                case 172: {
                    a1.addIstore(this.getVar(1));
                    this.jsrJmp(a1);
                    a1.addIload(this.var);
                    break;
                }
                case 173: {
                    a1.addLstore(this.getVar(2));
                    this.jsrJmp(a1);
                    a1.addLload(this.var);
                    break;
                }
                case 175: {
                    a1.addDstore(this.getVar(2));
                    this.jsrJmp(a1);
                    a1.addDload(this.var);
                    break;
                }
                case 174: {
                    a1.addFstore(this.getVar(1));
                    this.jsrJmp(a1);
                    a1.addFload(this.var);
                    break;
                }
                default: {
                    throw new RuntimeException("fatal");
                }
            }
            return false;
        }
    }
    
    static class JsrHook2 extends ReturnHook
    {
        int var;
        int target;
        
        JsrHook2(final CodeGen a1, final int[] a2) {
            super(a1);
            this.target = a2[0];
            this.var = a2[1];
        }
        
        @Override
        protected boolean doit(final Bytecode a1, final int a2) {
            switch (a2) {
                case 177: {
                    break;
                }
                case 176: {
                    a1.addAstore(this.var);
                    break;
                }
                case 172: {
                    a1.addIstore(this.var);
                    break;
                }
                case 173: {
                    a1.addLstore(this.var);
                    break;
                }
                case 175: {
                    a1.addDstore(this.var);
                    break;
                }
                case 174: {
                    a1.addFstore(this.var);
                    break;
                }
                default: {
                    throw new RuntimeException("fatal");
                }
            }
            a1.addOpcode(167);
            a1.addIndex(this.target - a1.currentPc() + 3);
            return true;
        }
    }
}
