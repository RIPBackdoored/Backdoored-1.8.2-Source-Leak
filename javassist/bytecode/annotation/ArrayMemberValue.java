package javassist.bytecode.annotation;

import javassist.bytecode.*;
import javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class ArrayMemberValue extends MemberValue
{
    MemberValue type;
    MemberValue[] values;
    
    public ArrayMemberValue(final ConstPool a1) {
        super('[', a1);
        this.type = null;
        this.values = null;
    }
    
    public ArrayMemberValue(final MemberValue a1, final ConstPool a2) {
        super('[', a2);
        this.type = a1;
        this.values = null;
    }
    
    @Override
    Object getValue(final ClassLoader v1, final ClassPool v2, final Method v3) throws ClassNotFoundException {
        if (this.values == null) {
            throw new ClassNotFoundException("no array elements found: " + v3.getName());
        }
        final int v4 = this.values.length;
        Class v5 = null;
        if (this.type == null) {
            final Class a1 = v3.getReturnType().getComponentType();
            if (a1 == null || v4 > 0) {
                throw new ClassNotFoundException("broken array type: " + v3.getName());
            }
        }
        else {
            v5 = this.type.getType(v1);
        }
        final Object v6 = Array.newInstance(v5, v4);
        for (int a2 = 0; a2 < v4; ++a2) {
            Array.set(v6, a2, this.values[a2].getValue(v1, v2, v3));
        }
        return v6;
    }
    
    @Override
    Class getType(final ClassLoader a1) throws ClassNotFoundException {
        if (this.type == null) {
            throw new ClassNotFoundException("no array type specified");
        }
        final Object v1 = Array.newInstance(this.type.getType(a1), 0);
        return v1.getClass();
    }
    
    public MemberValue getType() {
        return this.type;
    }
    
    public MemberValue[] getValue() {
        return this.values;
    }
    
    public void setValue(final MemberValue[] a1) {
        this.values = a1;
        if (a1 != null && a1.length > 0) {
            this.type = a1[0];
        }
    }
    
    @Override
    public String toString() {
        final StringBuffer v0 = new StringBuffer("{");
        if (this.values != null) {
            for (int v2 = 0; v2 < this.values.length; ++v2) {
                v0.append(this.values[v2].toString());
                if (v2 + 1 < this.values.length) {
                    v0.append(", ");
                }
            }
        }
        v0.append("}");
        return v0.toString();
    }
    
    @Override
    public void write(final AnnotationsWriter v2) throws IOException {
        final int v3 = (this.values == null) ? 0 : this.values.length;
        v2.arrayValue(v3);
        for (int a1 = 0; a1 < v3; ++a1) {
            this.values[a1].write(v2);
        }
    }
    
    @Override
    public void accept(final MemberValueVisitor a1) {
        a1.visitArrayMemberValue(this);
    }
}
