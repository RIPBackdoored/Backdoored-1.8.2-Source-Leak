package javassist.bytecode.annotation;

import javassist.bytecode.*;
import javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class ShortMemberValue extends MemberValue
{
    int valueIndex;
    
    public ShortMemberValue(final int a1, final ConstPool a2) {
        super('S', a2);
        this.valueIndex = a1;
    }
    
    public ShortMemberValue(final short a1, final ConstPool a2) {
        super('S', a2);
        this.setValue(a1);
    }
    
    public ShortMemberValue(final ConstPool a1) {
        super('S', a1);
        this.setValue((short)0);
    }
    
    @Override
    Object getValue(final ClassLoader a1, final ClassPool a2, final Method a3) {
        return new Short(this.getValue());
    }
    
    @Override
    Class getType(final ClassLoader a1) {
        return Short.TYPE;
    }
    
    public short getValue() {
        return (short)this.cp.getIntegerInfo(this.valueIndex);
    }
    
    public void setValue(final short a1) {
        this.valueIndex = this.cp.addIntegerInfo(a1);
    }
    
    @Override
    public String toString() {
        return Short.toString(this.getValue());
    }
    
    @Override
    public void write(final AnnotationsWriter a1) throws IOException {
        a1.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor a1) {
        a1.visitShortMemberValue(this);
    }
}
