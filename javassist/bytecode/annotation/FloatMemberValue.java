package javassist.bytecode.annotation;

import javassist.bytecode.*;
import javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class FloatMemberValue extends MemberValue
{
    int valueIndex;
    
    public FloatMemberValue(final int a1, final ConstPool a2) {
        super('F', a2);
        this.valueIndex = a1;
    }
    
    public FloatMemberValue(final float a1, final ConstPool a2) {
        super('F', a2);
        this.setValue(a1);
    }
    
    public FloatMemberValue(final ConstPool a1) {
        super('F', a1);
        this.setValue(0.0f);
    }
    
    @Override
    Object getValue(final ClassLoader a1, final ClassPool a2, final Method a3) {
        return new Float(this.getValue());
    }
    
    @Override
    Class getType(final ClassLoader a1) {
        return Float.TYPE;
    }
    
    public float getValue() {
        return this.cp.getFloatInfo(this.valueIndex);
    }
    
    public void setValue(final float a1) {
        this.valueIndex = this.cp.addFloatInfo(a1);
    }
    
    @Override
    public String toString() {
        return Float.toString(this.getValue());
    }
    
    @Override
    public void write(final AnnotationsWriter a1) throws IOException {
        a1.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor a1) {
        a1.visitFloatMemberValue(this);
    }
}
