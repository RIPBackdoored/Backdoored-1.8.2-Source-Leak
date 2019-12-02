package javassist.bytecode.annotation;

import javassist.bytecode.*;
import javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class CharMemberValue extends MemberValue
{
    int valueIndex;
    
    public CharMemberValue(final int a1, final ConstPool a2) {
        super('C', a2);
        this.valueIndex = a1;
    }
    
    public CharMemberValue(final char a1, final ConstPool a2) {
        super('C', a2);
        this.setValue(a1);
    }
    
    public CharMemberValue(final ConstPool a1) {
        super('C', a1);
        this.setValue('\0');
    }
    
    @Override
    Object getValue(final ClassLoader a1, final ClassPool a2, final Method a3) {
        return new Character(this.getValue());
    }
    
    @Override
    Class getType(final ClassLoader a1) {
        return Character.TYPE;
    }
    
    public char getValue() {
        return (char)this.cp.getIntegerInfo(this.valueIndex);
    }
    
    public void setValue(final char a1) {
        this.valueIndex = this.cp.addIntegerInfo(a1);
    }
    
    @Override
    public String toString() {
        return Character.toString(this.getValue());
    }
    
    @Override
    public void write(final AnnotationsWriter a1) throws IOException {
        a1.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor a1) {
        a1.visitCharMemberValue(this);
    }
}
