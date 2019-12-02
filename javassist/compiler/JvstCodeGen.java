package javassist.compiler;

import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.*;

public class JvstCodeGen extends MemberCodeGen
{
    String paramArrayName;
    String paramListName;
    CtClass[] paramTypeList;
    private int paramVarBase;
    private boolean useParam0;
    private String param0Type;
    public static final String sigName = "$sig";
    public static final String dollarTypeName = "$type";
    public static final String clazzName = "$class";
    private CtClass dollarType;
    CtClass returnType;
    String returnCastName;
    private String returnVarName;
    public static final String wrapperCastName = "$w";
    String proceedName;
    public static final String cflowName = "$cflow";
    ProceedHandler procHandler;
    
    public JvstCodeGen(final Bytecode a1, final CtClass a2, final ClassPool a3) {
        super(a1, a2, a3);
        this.paramArrayName = null;
        this.paramListName = null;
        this.paramTypeList = null;
        this.paramVarBase = 0;
        this.useParam0 = false;
        this.param0Type = null;
        this.dollarType = null;
        this.returnType = null;
        this.returnCastName = null;
        this.returnVarName = null;
        this.proceedName = null;
        this.procHandler = null;
        this.setTypeChecker(new JvstTypeChecker(a2, a3, this));
    }
    
    private int indexOfParam1() {
        return this.paramVarBase + (this.useParam0 ? 1 : 0);
    }
    
    public void setProceedHandler(final ProceedHandler a1, final String a2) {
        this.proceedName = a2;
        this.procHandler = a1;
    }
    
    public void addNullIfVoid() {
        if (this.exprType == 344) {
            this.bytecode.addOpcode(1);
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/Object";
        }
    }
    
    @Override
    public void atMember(final Member a1) throws CompileError {
        final String v1 = a1.get();
        if (v1.equals(this.paramArrayName)) {
            compileParameterList(this.bytecode, this.paramTypeList, this.indexOfParam1());
            this.exprType = 307;
            this.arrayDim = 1;
            this.className = "java/lang/Object";
        }
        else if (v1.equals("$sig")) {
            this.bytecode.addLdc(Descriptor.ofMethod(this.returnType, this.paramTypeList));
            this.bytecode.addInvokestatic("javassist/runtime/Desc", "getParams", "(Ljava/lang/String;)[Ljava/lang/Class;");
            this.exprType = 307;
            this.arrayDim = 1;
            this.className = "java/lang/Class";
        }
        else if (v1.equals("$type")) {
            if (this.dollarType == null) {
                throw new CompileError("$type is not available");
            }
            this.bytecode.addLdc(Descriptor.of(this.dollarType));
            this.callGetType("getType");
        }
        else if (v1.equals("$class")) {
            if (this.param0Type == null) {
                throw new CompileError("$class is not available");
            }
            this.bytecode.addLdc(this.param0Type);
            this.callGetType("getClazz");
        }
        else {
            super.atMember(a1);
        }
    }
    
    private void callGetType(final String a1) {
        this.bytecode.addInvokestatic("javassist/runtime/Desc", a1, "(Ljava/lang/String;)Ljava/lang/Class;");
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/Class";
    }
    
    @Override
    protected void atFieldAssign(final Expr a1, final int a2, final ASTree a3, final ASTree a4, final boolean a5) throws CompileError {
        if (a3 instanceof Member && ((Member)a3).get().equals(this.paramArrayName)) {
            if (a2 != 61) {
                throw new CompileError("bad operator for " + this.paramArrayName);
            }
            a4.accept(this);
            if (this.arrayDim != 1 || this.exprType != 307) {
                throw new CompileError("invalid type for " + this.paramArrayName);
            }
            this.atAssignParamList(this.paramTypeList, this.bytecode);
            if (!a5) {
                this.bytecode.addOpcode(87);
            }
        }
        else {
            super.atFieldAssign(a1, a2, a3, a4, a5);
        }
    }
    
    protected void atAssignParamList(final CtClass[] v1, final Bytecode v2) throws CompileError {
        if (v1 == null) {
            return;
        }
        int v3 = this.indexOfParam1();
        for (int v4 = v1.length, a1 = 0; a1 < v4; ++a1) {
            v2.addOpcode(89);
            v2.addIconst(a1);
            v2.addOpcode(50);
            this.compileUnwrapValue(v1[a1], v2);
            v2.addStore(v3, v1[a1]);
            v3 += (CodeGen.is2word(this.exprType, this.arrayDim) ? 2 : 1);
        }
    }
    
    @Override
    public void atCastExpr(final CastExpr v-1) throws CompileError {
        final ASTList v0 = v-1.getClassName();
        if (v0 != null && v-1.getArrayDim() == 0) {
            final ASTree v2 = v0.head();
            if (v2 instanceof Symbol && v0.tail() == null) {
                final String a1 = ((Symbol)v2).get();
                if (a1.equals(this.returnCastName)) {
                    this.atCastToRtype(v-1);
                    return;
                }
                if (a1.equals("$w")) {
                    this.atCastToWrapper(v-1);
                    return;
                }
            }
        }
        super.atCastExpr(v-1);
    }
    
    protected void atCastToRtype(final CastExpr v-1) throws CompileError {
        v-1.getOprand().accept(this);
        if (this.exprType == 344 || CodeGen.isRefType(this.exprType) || this.arrayDim > 0) {
            this.compileUnwrapValue(this.returnType, this.bytecode);
        }
        else {
            if (!(this.returnType instanceof CtPrimitiveType)) {
                throw new CompileError("invalid cast");
            }
            final CtPrimitiveType a1 = (CtPrimitiveType)this.returnType;
            final int v1 = MemberResolver.descToType(a1.getDescriptor());
            this.atNumCastExpr(this.exprType, v1);
            this.exprType = v1;
            this.arrayDim = 0;
            this.className = null;
        }
    }
    
    protected void atCastToWrapper(final CastExpr v-2) throws CompileError {
        v-2.getOprand().accept(this);
        if (CodeGen.isRefType(this.exprType) || this.arrayDim > 0) {
            return;
        }
        final CtClass lookupClass = this.resolver.lookupClass(this.exprType, this.arrayDim, this.className);
        if (lookupClass instanceof CtPrimitiveType) {
            final CtPrimitiveType a1 = (CtPrimitiveType)lookupClass;
            final String v1 = a1.getWrapperName();
            this.bytecode.addNew(v1);
            this.bytecode.addOpcode(89);
            if (a1.getDataSize() > 1) {
                this.bytecode.addOpcode(94);
            }
            else {
                this.bytecode.addOpcode(93);
            }
            this.bytecode.addOpcode(88);
            this.bytecode.addInvokespecial(v1, "<init>", "(" + a1.getDescriptor() + ")V");
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/Object";
        }
    }
    
    @Override
    public void atCallExpr(final CallExpr v2) throws CompileError {
        final ASTree v3 = v2.oprand1();
        if (v3 instanceof Member) {
            final String a1 = ((Member)v3).get();
            if (this.procHandler != null && a1.equals(this.proceedName)) {
                this.procHandler.doit(this, this.bytecode, (ASTList)v2.oprand2());
                return;
            }
            if (a1.equals("$cflow")) {
                this.atCflow((ASTList)v2.oprand2());
                return;
            }
        }
        super.atCallExpr(v2);
    }
    
    protected void atCflow(final ASTList a1) throws CompileError {
        final StringBuffer v1 = new StringBuffer();
        if (a1 == null || a1.tail() != null) {
            throw new CompileError("bad $cflow");
        }
        makeCflowName(v1, a1.head());
        final String v2 = v1.toString();
        final Object[] v3 = this.resolver.getClassPool().lookupCflow(v2);
        if (v3 == null) {
            throw new CompileError("no such $cflow: " + v2);
        }
        this.bytecode.addGetstatic((String)v3[0], (String)v3[1], "Ljavassist/runtime/Cflow;");
        this.bytecode.addInvokevirtual("javassist.runtime.Cflow", "value", "()I");
        this.exprType = 324;
        this.arrayDim = 0;
        this.className = null;
    }
    
    private static void makeCflowName(final StringBuffer a2, final ASTree v1) throws CompileError {
        if (v1 instanceof Symbol) {
            a2.append(((Symbol)v1).get());
            return;
        }
        if (v1 instanceof Expr) {
            final Expr a3 = (Expr)v1;
            if (a3.getOperator() == 46) {
                makeCflowName(a2, a3.oprand1());
                a2.append('.');
                makeCflowName(a2, a3.oprand2());
                return;
            }
        }
        throw new CompileError("bad $cflow");
    }
    
    public boolean isParamListName(final ASTList v2) {
        if (this.paramTypeList != null && v2 != null && v2.tail() == null) {
            final ASTree a1 = v2.head();
            return a1 instanceof Member && ((Member)a1).get().equals(this.paramListName);
        }
        return false;
    }
    
    @Override
    public int getMethodArgsLength(ASTList v2) {
        final String v3 = this.paramListName;
        int v4 = 0;
        while (v2 != null) {
            final ASTree a1 = v2.head();
            if (a1 instanceof Member && ((Member)a1).get().equals(v3)) {
                if (this.paramTypeList != null) {
                    v4 += this.paramTypeList.length;
                }
            }
            else {
                ++v4;
            }
            v2 = v2.tail();
        }
        return v4;
    }
    
    @Override
    public void atMethodArgs(ASTList v-6, final int[] v-5, final int[] v-4, final String[] v-3) throws CompileError {
        final CtClass[] paramTypeList = this.paramTypeList;
        final String paramListName = this.paramListName;
        int v0 = 0;
        while (v-6 != null) {
            final ASTree v2 = v-6.head();
            if (v2 instanceof Member && ((Member)v2).get().equals(paramListName)) {
                if (paramTypeList != null) {
                    final int a3 = paramTypeList.length;
                    int a4 = this.indexOfParam1();
                    for (final CtClass a6 : paramTypeList) {
                        a4 += this.bytecode.addLoad(a4, a6);
                        this.setType(a6);
                        v-5[v0] = this.exprType;
                        v-4[v0] = this.arrayDim;
                        v-3[v0] = this.className;
                        ++v0;
                    }
                }
            }
            else {
                v2.accept(this);
                v-5[v0] = this.exprType;
                v-4[v0] = this.arrayDim;
                v-3[v0] = this.className;
                ++v0;
            }
            v-6 = v-6.tail();
        }
    }
    
    void compileInvokeSpecial(final ASTree a1, final int a2, final String a3, final ASTList a4) throws CompileError {
        a1.accept(this);
        final int v1 = this.getMethodArgsLength(a4);
        this.atMethodArgs(a4, new int[v1], new int[v1], new String[v1]);
        this.bytecode.addInvokespecial(a2, a3);
        this.setReturnType(a3, false, false);
        this.addNullIfVoid();
    }
    
    @Override
    protected void atReturnStmnt(final Stmnt a1) throws CompileError {
        ASTree v1 = a1.getLeft();
        if (v1 != null && this.returnType == CtClass.voidType) {
            this.compileExpr(v1);
            if (CodeGen.is2word(this.exprType, this.arrayDim)) {
                this.bytecode.addOpcode(88);
            }
            else if (this.exprType != 344) {
                this.bytecode.addOpcode(87);
            }
            v1 = null;
        }
        this.atReturnStmnt2(v1);
    }
    
    public int recordReturnType(final CtClass a4, final String v1, final String v2, final SymbolTable v3) throws CompileError {
        this.returnType = a4;
        this.returnCastName = v1;
        this.returnVarName = v2;
        if (v2 == null) {
            return -1;
        }
        final int a5 = this.getMaxLocals();
        final int a6 = a5 + this.recordVar(a4, v2, a5, v3);
        this.setMaxLocals(a6);
        return a5;
    }
    
    public void recordType(final CtClass a1) {
        this.dollarType = a1;
    }
    
    public int recordParams(final CtClass[] a1, final boolean a2, final String a3, final String a4, final String a5, final SymbolTable a6) throws CompileError {
        return this.recordParams(a1, a2, a3, a4, a5, !a2, 0, this.getThisName(), a6);
    }
    
    public int recordParams(final CtClass[] a5, final boolean a6, final String a7, final String a8, final String a9, final boolean v1, final int v2, final String v3, final SymbolTable v4) throws CompileError {
        this.paramTypeList = a5;
        this.paramArrayName = a8;
        this.paramListName = a9;
        this.paramVarBase = v2;
        this.useParam0 = v1;
        if (v3 != null) {
            this.param0Type = MemberResolver.jvmToJavaName(v3);
        }
        this.inStaticMethod = a6;
        int v5 = v2;
        if (v1) {
            final String a10 = a7 + "0";
            final Declarator a11 = new Declarator(307, MemberResolver.javaToJvmName(v3), 0, v5++, new Symbol(a10));
            v4.append(a10, a11);
        }
        for (int a12 = 0; a12 < a5.length; ++a12) {
            v5 += this.recordVar(a5[a12], a7 + (a12 + 1), v5, v4);
        }
        if (this.getMaxLocals() < v5) {
            this.setMaxLocals(v5);
        }
        return v5;
    }
    
    public int recordVariable(final CtClass v1, final String v2, final SymbolTable v3) throws CompileError {
        if (v2 == null) {
            return -1;
        }
        final int a1 = this.getMaxLocals();
        final int a2 = a1 + this.recordVar(v1, v2, a1, v3);
        this.setMaxLocals(a2);
        return a1;
    }
    
    private int recordVar(final CtClass a1, final String a2, final int a3, final SymbolTable a4) throws CompileError {
        if (a1 == CtClass.voidType) {
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/Object";
        }
        else {
            this.setType(a1);
        }
        final Declarator v1 = new Declarator(this.exprType, this.className, this.arrayDim, a3, new Symbol(a2));
        a4.append(a2, v1);
        return CodeGen.is2word(this.exprType, this.arrayDim) ? 2 : 1;
    }
    
    public void recordVariable(final String a1, final String a2, final int a3, final SymbolTable a4) throws CompileError {
        int v2;
        char v3;
        for (v2 = 0; (v3 = a1.charAt(v2)) == '['; ++v2) {}
        final int v4 = MemberResolver.descToType(v3);
        String v5 = null;
        if (v4 == 307) {
            if (v2 == 0) {
                v5 = a1.substring(1, a1.length() - 1);
            }
            else {
                v5 = a1.substring(v2 + 1, a1.length() - 1);
            }
        }
        final Declarator v6 = new Declarator(v4, v5, v2, a3, new Symbol(a2));
        a4.append(a2, v6);
    }
    
    public static int compileParameterList(final Bytecode v-4, final CtClass[] v-3, int v-2) {
        if (v-3 == null) {
            v-4.addIconst(0);
            v-4.addAnewarray("java.lang.Object");
            return 1;
        }
        final CtClass[] v3 = { null };
        final int v0 = v-3.length;
        v-4.addIconst(v0);
        v-4.addAnewarray("java.lang.Object");
        for (int v2 = 0; v2 < v0; ++v2) {
            v-4.addOpcode(89);
            v-4.addIconst(v2);
            if (v-3[v2].isPrimitive()) {
                final CtPrimitiveType a1 = (CtPrimitiveType)v-3[v2];
                final String a2 = a1.getWrapperName();
                v-4.addNew(a2);
                v-4.addOpcode(89);
                final int a3 = v-4.addLoad(v-2, a1);
                v-2 += a3;
                v3[0] = a1;
                v-4.addInvokespecial(a2, "<init>", Descriptor.ofMethod(CtClass.voidType, v3));
            }
            else {
                v-4.addAload(v-2);
                ++v-2;
            }
            v-4.addOpcode(83);
        }
        return 8;
    }
    
    protected void compileUnwrapValue(final CtClass v2, final Bytecode v3) throws CompileError {
        if (v2 == CtClass.voidType) {
            this.addNullIfVoid();
            return;
        }
        if (this.exprType == 344) {
            throw new CompileError("invalid type for " + this.returnCastName);
        }
        if (v2 instanceof CtPrimitiveType) {
            final CtPrimitiveType a1 = (CtPrimitiveType)v2;
            final String a2 = a1.getWrapperName();
            v3.addCheckcast(a2);
            v3.addInvokevirtual(a2, a1.getGetMethodName(), a1.getGetMethodDescriptor());
            this.setType(v2);
        }
        else {
            v3.addCheckcast(v2);
            this.setType(v2);
        }
    }
    
    public void setType(final CtClass a1) throws CompileError {
        this.setType(a1, 0);
    }
    
    private void setType(final CtClass v2, final int v3) throws CompileError {
        if (v2.isPrimitive()) {
            final CtPrimitiveType a1 = (CtPrimitiveType)v2;
            this.exprType = MemberResolver.descToType(a1.getDescriptor());
            this.arrayDim = v3;
            this.className = null;
        }
        else {
            if (v2.isArray()) {
                try {
                    this.setType(v2.getComponentType(), v3 + 1);
                    return;
                }
                catch (NotFoundException a2) {
                    throw new CompileError("undefined type: " + v2.getName());
                }
            }
            this.exprType = 307;
            this.arrayDim = v3;
            this.className = MemberResolver.javaToJvmName(v2.getName());
        }
    }
    
    public void doNumCast(final CtClass v2) throws CompileError {
        if (this.arrayDim == 0 && !CodeGen.isRefType(this.exprType)) {
            if (!(v2 instanceof CtPrimitiveType)) {
                throw new CompileError("type mismatch");
            }
            final CtPrimitiveType a1 = (CtPrimitiveType)v2;
            this.atNumCastExpr(this.exprType, MemberResolver.descToType(a1.getDescriptor()));
        }
    }
}
