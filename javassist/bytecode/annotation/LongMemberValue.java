package javassist.bytecode.annotation;

import javassist.bytecode.*;
import javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class LongMemberValue extends MemberValue
{
    int valueIndex;
    
    public LongMemberValue(final int a1, final ConstPool a2) {
        super('J', a2);
        this.valueIndex = a1;
    }
    
    public LongMemberValue(final long a1, final ConstPool a2) {
        super('J', a2);
        this.setValue(a1);
    }
    
    public LongMemberValue(final ConstPool a1) {
        super('J', a1);
        this.setValue(0L);
    }
    
    @Override
    Object getValue(final ClassLoader a1, final ClassPool a2, final Method a3) {
        return new Long(this.getValue());
    }
    
    @Override
    Class getType(final ClassLoader a1) {
        return Long.TYPE;
    }
    
    public long getValue() {
        return this.cp.getLongInfo(this.valueIndex);
    }
    
    public void setValue(final long a1) {
        this.valueIndex = this.cp.addLongInfo(a1);
    }
    
    @Override
    public String toString() {
        return Long.toString(this.getValue());
    }
    
    @Override
    public void write(final AnnotationsWriter a1) throws IOException {
        a1.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor a1) {
        a1.visitLongMemberValue(this);
    }
}
