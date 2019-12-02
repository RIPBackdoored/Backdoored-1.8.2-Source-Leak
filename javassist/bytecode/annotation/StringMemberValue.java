package javassist.bytecode.annotation;

import javassist.bytecode.*;
import javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class StringMemberValue extends MemberValue
{
    int valueIndex;
    
    public StringMemberValue(final int a1, final ConstPool a2) {
        super('s', a2);
        this.valueIndex = a1;
    }
    
    public StringMemberValue(final String a1, final ConstPool a2) {
        super('s', a2);
        this.setValue(a1);
    }
    
    public StringMemberValue(final ConstPool a1) {
        super('s', a1);
        this.setValue("");
    }
    
    @Override
    Object getValue(final ClassLoader a1, final ClassPool a2, final Method a3) {
        return this.getValue();
    }
    
    @Override
    Class getType(final ClassLoader a1) {
        return String.class;
    }
    
    public String getValue() {
        return this.cp.getUtf8Info(this.valueIndex);
    }
    
    public void setValue(final String a1) {
        this.valueIndex = this.cp.addUtf8Info(a1);
    }
    
    @Override
    public String toString() {
        return "\"" + this.getValue() + "\"";
    }
    
    @Override
    public void write(final AnnotationsWriter a1) throws IOException {
        a1.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor a1) {
        a1.visitStringMemberValue(this);
    }
}
