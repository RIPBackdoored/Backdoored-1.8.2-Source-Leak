package javassist.compiler;

import java.util.*;
import javassist.*;
import javassist.bytecode.*;

public class AccessorMaker
{
    private CtClass clazz;
    private int uniqueNumber;
    private HashMap accessors;
    static final String lastParamType = "javassist.runtime.Inner";
    
    public AccessorMaker(final CtClass a1) {
        super();
        this.clazz = a1;
        this.uniqueNumber = 1;
        this.accessors = new HashMap();
    }
    
    public String getConstructor(final CtClass v-7, final String v-6, final MethodInfo v-5) throws CompileError {
        final String string = "<init>:" + v-6;
        String appendParameter = this.accessors.get(string);
        if (appendParameter != null) {
            return appendParameter;
        }
        appendParameter = Descriptor.appendParameter("javassist.runtime.Inner", v-6);
        final ClassFile classFile = this.clazz.getClassFile();
        try {
            final ConstPool a2 = classFile.getConstPool();
            final ClassPool a3 = this.clazz.getClassPool();
            final MethodInfo v1 = new MethodInfo(a2, "<init>", appendParameter);
            v1.setAccessFlags(0);
            v1.addAttribute(new SyntheticAttribute(a2));
            final ExceptionsAttribute v2 = v-5.getExceptionsAttribute();
            if (v2 != null) {
                v1.addAttribute(v2.copy(a2, null));
            }
            final CtClass[] v3 = Descriptor.getParameterTypes(v-6, a3);
            final Bytecode v4 = new Bytecode(a2);
            v4.addAload(0);
            int v5 = 1;
            for (int a4 = 0; a4 < v3.length; ++a4) {
                v5 += v4.addLoad(v5, v3[a4]);
            }
            v4.setMaxLocals(v5 + 1);
            v4.addInvokespecial(this.clazz, "<init>", v-6);
            v4.addReturn(null);
            v1.setCodeAttribute(v4.toCodeAttribute());
            classFile.addMethod(v1);
        }
        catch (CannotCompileException a5) {
            throw new CompileError(a5);
        }
        catch (NotFoundException a6) {
            throw new CompileError(a6);
        }
        this.accessors.put(string, appendParameter);
        return appendParameter;
    }
    
    public String getMethodAccessor(final String v-9, final String v-8, final String v-7, final MethodInfo v-6) throws CompileError {
        final String string = v-9 + ":" + v-8;
        String accessorName = this.accessors.get(string);
        if (accessorName != null) {
            return accessorName;
        }
        final ClassFile classFile = this.clazz.getClassFile();
        accessorName = this.findAccessorName(classFile);
        try {
            final ConstPool a2 = classFile.getConstPool();
            final ClassPool a3 = this.clazz.getClassPool();
            final MethodInfo a4 = new MethodInfo(a2, accessorName, v-7);
            a4.setAccessFlags(8);
            a4.addAttribute(new SyntheticAttribute(a2));
            final ExceptionsAttribute v1 = v-6.getExceptionsAttribute();
            if (v1 != null) {
                a4.addAttribute(v1.copy(a2, null));
            }
            final CtClass[] v2 = Descriptor.getParameterTypes(v-7, a3);
            int v3 = 0;
            final Bytecode v4 = new Bytecode(a2);
            for (int a5 = 0; a5 < v2.length; ++a5) {
                v3 += v4.addLoad(v3, v2[a5]);
            }
            v4.setMaxLocals(v3);
            if (v-8 == v-7) {
                v4.addInvokestatic(this.clazz, v-9, v-8);
            }
            else {
                v4.addInvokevirtual(this.clazz, v-9, v-8);
            }
            v4.addReturn(Descriptor.getReturnType(v-8, a3));
            a4.setCodeAttribute(v4.toCodeAttribute());
            classFile.addMethod(a4);
        }
        catch (CannotCompileException a6) {
            throw new CompileError(a6);
        }
        catch (NotFoundException a7) {
            throw new CompileError(a7);
        }
        this.accessors.put(string, accessorName);
        return accessorName;
    }
    
    public MethodInfo getFieldGetter(final FieldInfo v-7, final boolean v-6) throws CompileError {
        final String name = v-7.getName();
        final String string = name + ":getter";
        final Object value = this.accessors.get(string);
        if (value != null) {
            return (MethodInfo)value;
        }
        final ClassFile classFile = this.clazz.getClassFile();
        final String accessorName = this.findAccessorName(classFile);
        try {
            final ConstPool a2 = classFile.getConstPool();
            final ClassPool v1 = this.clazz.getClassPool();
            final String v2 = v-7.getDescriptor();
            String v3 = null;
            if (v-6) {
                final String a3 = "()" + v2;
            }
            else {
                v3 = "(" + Descriptor.of(this.clazz) + ")" + v2;
            }
            final MethodInfo v4 = new MethodInfo(a2, accessorName, v3);
            v4.setAccessFlags(8);
            v4.addAttribute(new SyntheticAttribute(a2));
            final Bytecode v5 = new Bytecode(a2);
            if (v-6) {
                v5.addGetstatic(Bytecode.THIS, name, v2);
            }
            else {
                v5.addAload(0);
                v5.addGetfield(Bytecode.THIS, name, v2);
                v5.setMaxLocals(1);
            }
            v5.addReturn(Descriptor.toCtClass(v2, v1));
            v4.setCodeAttribute(v5.toCodeAttribute());
            classFile.addMethod(v4);
            this.accessors.put(string, v4);
            return v4;
        }
        catch (CannotCompileException v6) {
            throw new CompileError(v6);
        }
        catch (NotFoundException v7) {
            throw new CompileError(v7);
        }
    }
    
    public MethodInfo getFieldSetter(final FieldInfo v-6, final boolean v-5) throws CompileError {
        final String name = v-6.getName();
        final String string = name + ":setter";
        final Object value = this.accessors.get(string);
        if (value != null) {
            return (MethodInfo)value;
        }
        final ClassFile classFile = this.clazz.getClassFile();
        final String v0 = this.findAccessorName(classFile);
        try {
            final ConstPool v2 = classFile.getConstPool();
            final ClassPool v3 = this.clazz.getClassPool();
            final String v4 = v-6.getDescriptor();
            String v5 = null;
            if (v-5) {
                final String a1 = "(" + v4 + ")V";
            }
            else {
                v5 = "(" + Descriptor.of(this.clazz) + v4 + ")V";
            }
            final MethodInfo v6 = new MethodInfo(v2, v0, v5);
            v6.setAccessFlags(8);
            v6.addAttribute(new SyntheticAttribute(v2));
            final Bytecode v7 = new Bytecode(v2);
            int v8 = 0;
            if (v-5) {
                final int a2 = v7.addLoad(0, Descriptor.toCtClass(v4, v3));
                v7.addPutstatic(Bytecode.THIS, name, v4);
            }
            else {
                v7.addAload(0);
                v8 = v7.addLoad(1, Descriptor.toCtClass(v4, v3)) + 1;
                v7.addPutfield(Bytecode.THIS, name, v4);
            }
            v7.addReturn(null);
            v7.setMaxLocals(v8);
            v6.setCodeAttribute(v7.toCodeAttribute());
            classFile.addMethod(v6);
            this.accessors.put(string, v6);
            return v6;
        }
        catch (CannotCompileException v9) {
            throw new CompileError(v9);
        }
        catch (NotFoundException v10) {
            throw new CompileError(v10);
        }
    }
    
    private String findAccessorName(final ClassFile a1) {
        String v1;
        do {
            v1 = "access$" + this.uniqueNumber++;
        } while (a1.getMethod(v1) != null);
        return v1;
    }
}
