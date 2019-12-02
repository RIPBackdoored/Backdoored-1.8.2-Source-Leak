package javassist.bytecode.annotation;

import javassist.bytecode.*;
import javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class BooleanMemberValue extends MemberValue
{
    int valueIndex;
    
    public BooleanMemberValue(final int a1, final ConstPool a2) {
        super('Z', a2);
        this.valueIndex = a1;
    }
    
    public BooleanMemberValue(final boolean a1, final ConstPool a2) {
        super('Z', a2);
        this.setValue(a1);
    }
    
    public BooleanMemberValue(final ConstPool a1) {
        super('Z', a1);
        this.setValue(false);
    }
    
    @Override
    Object getValue(final ClassLoader a1, final ClassPool a2, final Method a3) {
        return new Boolean(this.getValue());
    }
    
    @Override
    Class getType(final ClassLoader a1) {
        return Boolean.TYPE;
    }
    
    public boolean getValue() {
        return this.cp.getIntegerInfo(this.valueIndex) != 0;
    }
    
    public void setValue(final boolean a1) {
        this.valueIndex = this.cp.addIntegerInfo(a1 ? 1 : 0);
    }
    
    @Override
    public String toString() {
        return this.getValue() ? "true" : "false";
    }
    
    @Override
    public void write(final AnnotationsWriter a1) throws IOException {
        a1.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor a1) {
        a1.visitBooleanMemberValue(this);
    }
}
