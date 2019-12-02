package javassist.compiler;

import javassist.compiler.ast.*;
import javassist.*;

public class JvstTypeChecker extends TypeChecker
{
    private JvstCodeGen codeGen;
    
    public JvstTypeChecker(final CtClass a1, final ClassPool a2, final JvstCodeGen a3) {
        super(a1, a2);
        this.codeGen = a3;
    }
    
    public void addNullIfVoid() {
        if (this.exprType == 344) {
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/Object";
        }
    }
    
    @Override
    public void atMember(final Member a1) throws CompileError {
        final String v1 = a1.get();
        if (v1.equals(this.codeGen.paramArrayName)) {
            this.exprType = 307;
            this.arrayDim = 1;
            this.className = "java/lang/Object";
        }
        else if (v1.equals("$sig")) {
            this.exprType = 307;
            this.arrayDim = 1;
            this.className = "java/lang/Class";
        }
        else if (v1.equals("$type") || v1.equals("$class")) {
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/Class";
        }
        else {
            super.atMember(a1);
        }
    }
    
    @Override
    protected void atFieldAssign(final Expr v1, final int v2, final ASTree v3, final ASTree v4) throws CompileError {
        if (v3 instanceof Member && ((Member)v3).get().equals(this.codeGen.paramArrayName)) {
            v4.accept(this);
            final CtClass[] a2 = this.codeGen.paramTypeList;
            if (a2 == null) {
                return;
            }
            for (int a3 = a2.length, a4 = 0; a4 < a3; ++a4) {
                this.compileUnwrapValue(a2[a4]);
            }
        }
        else {
            super.atFieldAssign(v1, v2, v3, v4);
        }
    }
    
    @Override
    public void atCastExpr(final CastExpr v-1) throws CompileError {
        final ASTList v0 = v-1.getClassName();
        if (v0 != null && v-1.getArrayDim() == 0) {
            final ASTree v2 = v0.head();
            if (v2 instanceof Symbol && v0.tail() == null) {
                final String a1 = ((Symbol)v2).get();
                if (a1.equals(this.codeGen.returnCastName)) {
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
    
    protected void atCastToRtype(final CastExpr v-2) throws CompileError {
        final CtClass returnType = this.codeGen.returnType;
        v-2.getOprand().accept(this);
        if (this.exprType == 344 || CodeGen.isRefType(this.exprType) || this.arrayDim > 0) {
            this.compileUnwrapValue(returnType);
        }
        else if (returnType instanceof CtPrimitiveType) {
            final CtPrimitiveType a1 = (CtPrimitiveType)returnType;
            final int v1 = MemberResolver.descToType(a1.getDescriptor());
            this.exprType = v1;
            this.arrayDim = 0;
            this.className = null;
        }
    }
    
    protected void atCastToWrapper(final CastExpr a1) throws CompileError {
        a1.getOprand().accept(this);
        if (CodeGen.isRefType(this.exprType) || this.arrayDim > 0) {
            return;
        }
        final CtClass v1 = this.resolver.lookupClass(this.exprType, this.arrayDim, this.className);
        if (v1 instanceof CtPrimitiveType) {
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
            if (this.codeGen.procHandler != null && a1.equals(this.codeGen.proceedName)) {
                this.codeGen.procHandler.setReturnType(this, (ASTList)v2.oprand2());
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
        this.exprType = 324;
        this.arrayDim = 0;
        this.className = null;
    }
    
    public boolean isParamListName(final ASTList v2) {
        if (this.codeGen.paramTypeList != null && v2 != null && v2.tail() == null) {
            final ASTree a1 = v2.head();
            return a1 instanceof Member && ((Member)a1).get().equals(this.codeGen.paramListName);
        }
        return false;
    }
    
    @Override
    public int getMethodArgsLength(ASTList v2) {
        final String v3 = this.codeGen.paramListName;
        int v4 = 0;
        while (v2 != null) {
            final ASTree a1 = v2.head();
            if (a1 instanceof Member && ((Member)a1).get().equals(v3)) {
                if (this.codeGen.paramTypeList != null) {
                    v4 += this.codeGen.paramTypeList.length;
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
    public void atMethodArgs(ASTList v2, final int[] v3, final int[] v4, final String[] v5) throws CompileError {
        final CtClass[] v6 = this.codeGen.paramTypeList;
        final String v7 = this.codeGen.paramListName;
        int v8 = 0;
        while (v2 != null) {
            final ASTree a4 = v2.head();
            if (a4 instanceof Member && ((Member)a4).get().equals(v7)) {
                if (v6 != null) {
                    for (final CtClass a7 : v6) {
                        this.setType(a7);
                        v3[v8] = this.exprType;
                        v4[v8] = this.arrayDim;
                        v5[v8] = this.className;
                        ++v8;
                    }
                }
            }
            else {
                a4.accept(this);
                v3[v8] = this.exprType;
                v4[v8] = this.arrayDim;
                v5[v8] = this.className;
                ++v8;
            }
            v2 = v2.tail();
        }
    }
    
    void compileInvokeSpecial(final ASTree a1, final String a2, final String a3, final String a4, final ASTList a5) throws CompileError {
        a1.accept(this);
        final int v1 = this.getMethodArgsLength(a5);
        this.atMethodArgs(a5, new int[v1], new int[v1], new String[v1]);
        this.setReturnType(a4);
        this.addNullIfVoid();
    }
    
    protected void compileUnwrapValue(final CtClass a1) throws CompileError {
        if (a1 == CtClass.voidType) {
            this.addNullIfVoid();
        }
        else {
            this.setType(a1);
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
}
