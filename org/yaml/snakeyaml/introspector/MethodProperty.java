package org.yaml.snakeyaml.introspector;

import java.beans.*;
import java.lang.reflect.*;
import org.yaml.snakeyaml.error.*;
import java.util.*;
import java.lang.annotation.*;
import org.yaml.snakeyaml.util.*;

public class MethodProperty extends GenericProperty
{
    private final PropertyDescriptor property;
    private final boolean readable;
    private final boolean writable;
    
    private static Type discoverGenericType(final PropertyDescriptor v1) {
        final Method v2 = v1.getReadMethod();
        if (v2 != null) {
            return v2.getGenericReturnType();
        }
        final Method v3 = v1.getWriteMethod();
        if (v3 != null) {
            final Type[] a1 = v3.getGenericParameterTypes();
            if (a1.length > 0) {
                return a1[0];
            }
        }
        return null;
    }
    
    public MethodProperty(final PropertyDescriptor a1) {
        super(a1.getName(), a1.getPropertyType(), discoverGenericType(a1));
        this.property = a1;
        this.readable = (a1.getReadMethod() != null);
        this.writable = (a1.getWriteMethod() != null);
    }
    
    @Override
    public void set(final Object a1, final Object a2) throws Exception {
        if (!this.writable) {
            throw new YAMLException("No writable property '" + this.getName() + "' on class: " + a1.getClass().getName());
        }
        this.property.getWriteMethod().invoke(a1, a2);
    }
    
    @Override
    public Object get(final Object v2) {
        try {
            this.property.getReadMethod().setAccessible(true);
            return this.property.getReadMethod().invoke(v2, new Object[0]);
        }
        catch (Exception a1) {
            throw new YAMLException("Unable to find getter for property '" + this.property.getName() + "' on object " + v2 + ":" + a1);
        }
    }
    
    public List<Annotation> getAnnotations() {
        List<Annotation> v1;
        if (this.isReadable() && this.isWritable()) {
            v1 = ArrayUtils.toUnmodifiableCompositeList(this.property.getReadMethod().getAnnotations(), this.property.getWriteMethod().getAnnotations());
        }
        else if (this.isReadable()) {
            v1 = ArrayUtils.toUnmodifiableList(this.property.getReadMethod().getAnnotations());
        }
        else {
            v1 = ArrayUtils.toUnmodifiableList(this.property.getWriteMethod().getAnnotations());
        }
        return v1;
    }
    
    public <A extends java.lang.Object> A getAnnotation(final Class<A> a1) {
        A v1 = null;
        if (this.isReadable()) {
            v1 = this.property.getReadMethod().getAnnotation(a1);
        }
        if (v1 == null && this.isWritable()) {
            v1 = this.property.getWriteMethod().getAnnotation(a1);
        }
        return v1;
    }
    
    @Override
    public boolean isWritable() {
        return this.writable;
    }
    
    @Override
    public boolean isReadable() {
        return this.readable;
    }
}
