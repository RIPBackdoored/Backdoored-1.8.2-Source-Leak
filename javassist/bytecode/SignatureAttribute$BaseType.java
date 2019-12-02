package javassist.bytecode;

import javassist.*;

public static class BaseType extends Type
{
    char descriptor;
    
    BaseType(final char a1) {
        super();
        this.descriptor = a1;
    }
    
    public BaseType(final String a1) {
        this(Descriptor.of(a1).charAt(0));
    }
    
    public char getDescriptor() {
        return this.descriptor;
    }
    
    public CtClass getCtlass() {
        return Descriptor.toPrimitiveClass(this.descriptor);
    }
    
    @Override
    public String toString() {
        return Descriptor.toClassName(Character.toString(this.descriptor));
    }
    
    @Override
    void encode(final StringBuffer a1) {
        a1.append(this.descriptor);
    }
}
