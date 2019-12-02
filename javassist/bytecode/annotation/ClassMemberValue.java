package javassist.bytecode.annotation;

import javassist.*;
import java.lang.reflect.*;
import javassist.bytecode.*;
import java.io.*;

public class ClassMemberValue extends MemberValue
{
    int valueIndex;
    
    public ClassMemberValue(final int a1, final ConstPool a2) {
        super('c', a2);
        this.valueIndex = a1;
    }
    
    public ClassMemberValue(final String a1, final ConstPool a2) {
        super('c', a2);
        this.setValue(a1);
    }
    
    public ClassMemberValue(final ConstPool a1) {
        super('c', a1);
        this.setValue("java.lang.Class");
    }
    
    @Override
    Object getValue(final ClassLoader a1, final ClassPool a2, final Method a3) throws ClassNotFoundException {
        final String v1 = this.getValue();
        if (v1.equals("void")) {
            return Void.TYPE;
        }
        if (v1.equals("int")) {
            return Integer.TYPE;
        }
        if (v1.equals("byte")) {
            return Byte.TYPE;
        }
        if (v1.equals("long")) {
            return Long.TYPE;
        }
        if (v1.equals("double")) {
            return Double.TYPE;
        }
        if (v1.equals("float")) {
            return Float.TYPE;
        }
        if (v1.equals("char")) {
            return Character.TYPE;
        }
        if (v1.equals("short")) {
            return Short.TYPE;
        }
        if (v1.equals("boolean")) {
            return Boolean.TYPE;
        }
        return MemberValue.loadClass(a1, v1);
    }
    
    @Override
    Class getType(final ClassLoader a1) throws ClassNotFoundException {
        return MemberValue.loadClass(a1, "java.lang.Class");
    }
    
    public String getValue() {
        final String v0 = this.cp.getUtf8Info(this.valueIndex);
        try {
            return SignatureAttribute.toTypeSignature(v0).jvmTypeName();
        }
        catch (BadBytecode v2) {
            throw new RuntimeException(v2);
        }
    }
    
    public void setValue(final String a1) {
        final String v1 = Descriptor.of(a1);
        this.valueIndex = this.cp.addUtf8Info(v1);
    }
    
    @Override
    public String toString() {
        return this.getValue().replace('$', '.') + ".class";
    }
    
    @Override
    public void write(final AnnotationsWriter a1) throws IOException {
        a1.classInfoIndex(this.cp.getUtf8Info(this.valueIndex));
    }
    
    @Override
    public void accept(final MemberValueVisitor a1) {
        a1.visitClassMemberValue(this);
    }
}
