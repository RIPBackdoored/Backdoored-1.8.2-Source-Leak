package javassist.expr;

import javassist.*;
import javassist.bytecode.*;

public class ExprEditor
{
    public ExprEditor() {
        super();
    }
    
    public boolean doit(final CtClass v-7, final MethodInfo v-6) throws CannotCompileException {
        final CodeAttribute codeAttribute = v-6.getCodeAttribute();
        if (codeAttribute == null) {
            return false;
        }
        final CodeIterator iterator = codeAttribute.iterator();
        boolean b = false;
        final LoopContext v3 = new LoopContext(codeAttribute.getMaxLocals());
        while (iterator.hasNext()) {
            if (this.loopBody(iterator, v-7, v-6, v3)) {
                b = true;
            }
        }
        final ExceptionTable exceptionTable = codeAttribute.getExceptionTable();
        for (int v0 = exceptionTable.size(), a2 = 0; a2 < v0; ++a2) {
            final Handler a3 = new Handler(exceptionTable, a2, iterator, v-7, v-6);
            this.edit(a3);
            if (a3.edited()) {
                b = true;
                v3.updateMax(a3.locals(), a3.stack());
            }
        }
        if (codeAttribute.getMaxLocals() < v3.maxLocals) {
            codeAttribute.setMaxLocals(v3.maxLocals);
        }
        codeAttribute.setMaxStack(codeAttribute.getMaxStack() + v3.maxStack);
        try {
            if (b) {
                v-6.rebuildStackMapIf6(v-7.getClassPool(), v-7.getClassFile2());
            }
        }
        catch (BadBytecode v2) {
            throw new CannotCompileException(v2.getMessage(), v2);
        }
        return b;
    }
    
    boolean doit(final CtClass a4, final MethodInfo a5, final LoopContext v1, final CodeIterator v2, int v3) throws CannotCompileException {
        boolean v4 = false;
        while (v2.hasNext() && v2.lookAhead() < v3) {
            final int a6 = v2.getCodeLength();
            if (this.loopBody(v2, a4, a5, v1)) {
                v4 = true;
                final int a7 = v2.getCodeLength();
                if (a6 == a7) {
                    continue;
                }
                v3 += a7 - a6;
            }
        }
        return v4;
    }
    
    final boolean loopBody(final CodeIterator v-3, final CtClass v-2, final MethodInfo v-1, final LoopContext v0) throws CannotCompileException {
        try {
            Expr v = null;
            final int v2 = v-3.next();
            final int v3 = v-3.byteAt(v2);
            if (v3 >= 178) {
                if (v3 < 188) {
                    if (v3 == 184 || v3 == 185 || v3 == 182) {
                        v = new MethodCall(v2, v-3, v-2, v-1);
                        this.edit((MethodCall)v);
                    }
                    else if (v3 == 180 || v3 == 178 || v3 == 181 || v3 == 179) {
                        v = new FieldAccess(v2, v-3, v-2, v-1, v3);
                        this.edit((FieldAccess)v);
                    }
                    else if (v3 == 187) {
                        final int a1 = v-3.u16bitAt(v2 + 1);
                        v0.newList = new NewOp(v0.newList, v2, v-1.getConstPool().getClassInfo(a1));
                    }
                    else if (v3 == 183) {
                        final NewOp a2 = v0.newList;
                        if (a2 != null && v-1.getConstPool().isConstructor(a2.type, v-3.u16bitAt(v2 + 1)) > 0) {
                            v = new NewExpr(v2, v-3, v-2, v-1, a2.type, a2.pos);
                            this.edit((NewExpr)v);
                            v0.newList = a2.next;
                        }
                        else {
                            final MethodCall a3 = new MethodCall(v2, v-3, v-2, v-1);
                            if (a3.getMethodName().equals("<init>")) {
                                final ConstructorCall a4 = (ConstructorCall)(v = new ConstructorCall(v2, v-3, v-2, v-1));
                                this.edit(a4);
                            }
                            else {
                                v = a3;
                                this.edit(a3);
                            }
                        }
                    }
                }
                else if (v3 == 188 || v3 == 189 || v3 == 197) {
                    v = new NewArray(v2, v-3, v-2, v-1, v3);
                    this.edit((NewArray)v);
                }
                else if (v3 == 193) {
                    v = new Instanceof(v2, v-3, v-2, v-1);
                    this.edit((Instanceof)v);
                }
                else if (v3 == 192) {
                    v = new Cast(v2, v-3, v-2, v-1);
                    this.edit((Cast)v);
                }
            }
            if (v != null && v.edited()) {
                v0.updateMax(v.locals(), v.stack());
                return true;
            }
            return false;
        }
        catch (BadBytecode v4) {
            throw new CannotCompileException(v4);
        }
    }
    
    public void edit(final NewExpr a1) throws CannotCompileException {
    }
    
    public void edit(final NewArray a1) throws CannotCompileException {
    }
    
    public void edit(final MethodCall a1) throws CannotCompileException {
    }
    
    public void edit(final ConstructorCall a1) throws CannotCompileException {
    }
    
    public void edit(final FieldAccess a1) throws CannotCompileException {
    }
    
    public void edit(final Instanceof a1) throws CannotCompileException {
    }
    
    public void edit(final Cast a1) throws CannotCompileException {
    }
    
    public void edit(final Handler a1) throws CannotCompileException {
    }
    
    static final class NewOp
    {
        NewOp next;
        int pos;
        String type;
        
        NewOp(final NewOp a1, final int a2, final String a3) {
            super();
            this.next = a1;
            this.pos = a2;
            this.type = a3;
        }
    }
    
    static final class LoopContext
    {
        NewOp newList;
        int maxLocals;
        int maxStack;
        
        LoopContext(final int a1) {
            super();
            this.maxLocals = a1;
            this.maxStack = 0;
            this.newList = null;
        }
        
        void updateMax(final int a1, final int a2) {
            if (this.maxLocals < a1) {
                this.maxLocals = a1;
            }
            if (this.maxStack < a2) {
                this.maxStack = a2;
            }
        }
    }
}
