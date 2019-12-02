package javassist.bytecode.annotation;

import javassist.*;
import java.lang.reflect.*;
import javassist.bytecode.*;
import java.io.*;

public class EnumMemberValue extends MemberValue
{
    int typeIndex;
    int valueIndex;
    
    public EnumMemberValue(final int a1, final int a2, final ConstPool a3) {
        super('e', a3);
        this.typeIndex = a1;
        this.valueIndex = a2;
    }
    
    public EnumMemberValue(final ConstPool a1) {
        super('e', a1);
        final int n = 0;
        this.valueIndex = n;
        this.typeIndex = n;
    }
    
    @Override
    Object getValue(final ClassLoader v1, final ClassPool v2, final Method v3) throws ClassNotFoundException {
        try {
            return this.getType(v1).getField(this.getValue()).get(null);
        }
        catch (NoSuchFieldException a1) {
            throw new ClassNotFoundException(this.getType() + "." + this.getValue());
        }
        catch (IllegalAccessException a2) {
            throw new ClassNotFoundException(this.getType() + "." + this.getValue());
        }
    }
    
    @Override
    Class getType(final ClassLoader a1) throws ClassNotFoundException {
        return MemberValue.loadClass(a1, this.getType());
    }
    
    public String getType() {
        return Descriptor.toClassName(this.cp.getUtf8Info(this.typeIndex));
    }
    
    public void setType(final String a1) {
        this.typeIndex = this.cp.addUtf8Info(Descriptor.of(a1));
    }
    
    public String getValue() {
        return this.cp.getUtf8Info(this.valueIndex);
    }
    
    public void setValue(final String a1) {
        this.valueIndex = this.cp.addUtf8Info(a1);
    }
    
    @Override
    public String toString() {
        return this.getType() + "." + this.getValue();
    }
    
    @Override
    public void write(final AnnotationsWriter a1) throws IOException {
        a1.enumConstValue(this.cp.getUtf8Info(this.typeIndex), this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor a1) {
        a1.visitEnumMemberValue(this);
    }
}
