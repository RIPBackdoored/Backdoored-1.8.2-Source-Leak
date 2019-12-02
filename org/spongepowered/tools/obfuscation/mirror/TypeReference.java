package org.spongepowered.tools.obfuscation.mirror;

import java.io.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;

public class TypeReference implements Serializable, Comparable<TypeReference>
{
    private static final long serialVersionUID = 1L;
    private final String name;
    private transient TypeHandle handle;
    
    public TypeReference(final TypeHandle a1) {
        super();
        this.name = a1.getName();
        this.handle = a1;
    }
    
    public TypeReference(final String a1) {
        super();
        this.name = a1;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getClassName() {
        return this.name.replace('/', '.');
    }
    
    public TypeHandle getHandle(final ProcessingEnvironment v0) {
        if (this.handle == null) {
            final TypeElement v = v0.getElementUtils().getTypeElement(this.getClassName());
            try {
                this.handle = new TypeHandle(v);
            }
            catch (Exception a1) {
                a1.printStackTrace();
            }
        }
        return this.handle;
    }
    
    @Override
    public String toString() {
        return String.format("TypeReference[%s]", this.name);
    }
    
    @Override
    public int compareTo(final TypeReference a1) {
        return (a1 == null) ? -1 : this.name.compareTo(a1.name);
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof TypeReference && this.compareTo((TypeReference)a1) == 0;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    @Override
    public /* bridge */ int compareTo(final Object o) {
        return this.compareTo((TypeReference)o);
    }
}
