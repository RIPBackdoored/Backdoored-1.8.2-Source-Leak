package javassist.bytecode.annotation;

import javassist.bytecode.*;
import javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class AnnotationMemberValue extends MemberValue
{
    Annotation value;
    
    public AnnotationMemberValue(final ConstPool a1) {
        this(null, a1);
    }
    
    public AnnotationMemberValue(final Annotation a1, final ConstPool a2) {
        super('@', a2);
        this.value = a1;
    }
    
    @Override
    Object getValue(final ClassLoader a1, final ClassPool a2, final Method a3) throws ClassNotFoundException {
        return AnnotationImpl.make(a1, this.getType(a1), a2, this.value);
    }
    
    @Override
    Class getType(final ClassLoader a1) throws ClassNotFoundException {
        if (this.value == null) {
            throw new ClassNotFoundException("no type specified");
        }
        return MemberValue.loadClass(a1, this.value.getTypeName());
    }
    
    public Annotation getValue() {
        return this.value;
    }
    
    public void setValue(final Annotation a1) {
        this.value = a1;
    }
    
    @Override
    public String toString() {
        return this.value.toString();
    }
    
    @Override
    public void write(final AnnotationsWriter a1) throws IOException {
        a1.annotationValue();
        this.value.write(a1);
    }
    
    @Override
    public void accept(final MemberValueVisitor a1) {
        a1.visitAnnotationMemberValue(this);
    }
}
