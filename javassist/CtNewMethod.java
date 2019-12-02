package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;
import java.util.*;

public class CtNewMethod
{
    public CtNewMethod() {
        super();
    }
    
    public static CtMethod make(final String a1, final CtClass a2) throws CannotCompileException {
        return make(a1, a2, null, null);
    }
    
    public static CtMethod make(final String a3, final CtClass a4, final String v1, final String v2) throws CannotCompileException {
        final Javac v3 = new Javac(a4);
        try {
            if (v2 != null) {
                v3.recordProceed(v1, v2);
            }
            final CtMember a5 = v3.compile(a3);
            if (a5 instanceof CtMethod) {
                return (CtMethod)a5;
            }
        }
        catch (CompileError a6) {
            throw new CannotCompileException(a6);
        }
        throw new CannotCompileException("not a method");
    }
    
    public static CtMethod make(final CtClass a1, final String a2, final CtClass[] a3, final CtClass[] a4, final String a5, final CtClass a6) throws CannotCompileException {
        return make(1, a1, a2, a3, a4, a5, a6);
    }
    
    public static CtMethod make(final int a3, final CtClass a4, final String a5, final CtClass[] a6, final CtClass[] a7, final String v1, final CtClass v2) throws CannotCompileException {
        try {
            final CtMethod a8 = new CtMethod(a4, a5, a6, v2);
            a8.setModifiers(a3);
            a8.setExceptionTypes(a7);
            a8.setBody(v1);
            return a8;
        }
        catch (NotFoundException a9) {
            throw new CannotCompileException(a9);
        }
    }
    
    public static CtMethod copy(final CtMethod a1, final CtClass a2, final ClassMap a3) throws CannotCompileException {
        return new CtMethod(a1, a2, a3);
    }
    
    public static CtMethod copy(final CtMethod a1, final String a2, final CtClass a3, final ClassMap a4) throws CannotCompileException {
        final CtMethod v1 = new CtMethod(a1, a3, a4);
        v1.setName(a2);
        return v1;
    }
    
    public static CtMethod abstractMethod(final CtClass a1, final String a2, final CtClass[] a3, final CtClass[] a4, final CtClass a5) throws NotFoundException {
        final CtMethod v1 = new CtMethod(a1, a2, a3, a5);
        v1.setExceptionTypes(a4);
        return v1;
    }
    
    public static CtMethod getter(final String v1, final CtField v2) throws CannotCompileException {
        final FieldInfo v3 = v2.getFieldInfo2();
        final String v4 = v3.getDescriptor();
        final String v5 = "()" + v4;
        final ConstPool v6 = v3.getConstPool();
        final MethodInfo v7 = new MethodInfo(v6, v1, v5);
        v7.setAccessFlags(1);
        final Bytecode v8 = new Bytecode(v6, 2, 1);
        try {
            final String a1 = v3.getName();
            if ((v3.getAccessFlags() & 0x8) == 0x0) {
                v8.addAload(0);
                v8.addGetfield(Bytecode.THIS, a1, v4);
            }
            else {
                v8.addGetstatic(Bytecode.THIS, a1, v4);
            }
            v8.addReturn(v2.getType());
        }
        catch (NotFoundException a2) {
            throw new CannotCompileException(a2);
        }
        v7.setCodeAttribute(v8.toCodeAttribute());
        final CtClass v9 = v2.getDeclaringClass();
        return new CtMethod(v7, v9);
    }
    
    public static CtMethod setter(final String v1, final CtField v2) throws CannotCompileException {
        final FieldInfo v3 = v2.getFieldInfo2();
        final String v4 = v3.getDescriptor();
        final String v5 = "(" + v4 + ")V";
        final ConstPool v6 = v3.getConstPool();
        final MethodInfo v7 = new MethodInfo(v6, v1, v5);
        v7.setAccessFlags(1);
        final Bytecode v8 = new Bytecode(v6, 3, 3);
        try {
            final String a1 = v3.getName();
            if ((v3.getAccessFlags() & 0x8) == 0x0) {
                v8.addAload(0);
                v8.addLoad(1, v2.getType());
                v8.addPutfield(Bytecode.THIS, a1, v4);
            }
            else {
                v8.addLoad(1, v2.getType());
                v8.addPutstatic(Bytecode.THIS, a1, v4);
            }
            v8.addReturn(null);
        }
        catch (NotFoundException a2) {
            throw new CannotCompileException(a2);
        }
        v7.setCodeAttribute(v8.toCodeAttribute());
        final CtClass v9 = v2.getDeclaringClass();
        return new CtMethod(v7, v9);
    }
    
    public static CtMethod delegator(final CtMethod a2, final CtClass v1) throws CannotCompileException {
        try {
            return delegator0(a2, v1);
        }
        catch (NotFoundException a3) {
            throw new CannotCompileException(a3);
        }
    }
    
    private static CtMethod delegator0(final CtMethod a2, final CtClass v1) throws CannotCompileException, NotFoundException {
        final MethodInfo v2 = a2.getMethodInfo2();
        final String v3 = v2.getName();
        final String v4 = v2.getDescriptor();
        final ConstPool v5 = v1.getClassFile2().getConstPool();
        final MethodInfo v6 = new MethodInfo(v5, v3, v4);
        v6.setAccessFlags(v2.getAccessFlags());
        final ExceptionsAttribute v7 = v2.getExceptionsAttribute();
        if (v7 != null) {
            v6.setExceptionsAttribute((ExceptionsAttribute)v7.copy(v5, null));
        }
        final Bytecode v8 = new Bytecode(v5, 0, 0);
        final boolean v9 = Modifier.isStatic(a2.getModifiers());
        final CtClass v10 = a2.getDeclaringClass();
        final CtClass[] v11 = a2.getParameterTypes();
        int v12 = 0;
        if (v9) {
            final int a3 = v8.addLoadParameters(v11, 0);
            v8.addInvokestatic(v10, v3, v4);
        }
        else {
            v8.addLoad(0, v10);
            v12 = v8.addLoadParameters(v11, 1);
            v8.addInvokespecial(v10, v3, v4);
        }
        v8.addReturn(a2.getReturnType());
        v8.setMaxLocals(++v12);
        v8.setMaxStack((v12 < 2) ? 2 : v12);
        v6.setCodeAttribute(v8.toCodeAttribute());
        return new CtMethod(v6, v1);
    }
    
    public static CtMethod wrapped(final CtClass a1, final String a2, final CtClass[] a3, final CtClass[] a4, final CtMethod a5, final CtMethod.ConstParameter a6, final CtClass a7) throws CannotCompileException {
        return CtNewWrappedMethod.wrapped(a1, a2, a3, a4, a5, a6, a7);
    }
}
