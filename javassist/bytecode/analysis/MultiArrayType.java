package javassist.bytecode.analysis;

import javassist.*;

public class MultiArrayType extends Type
{
    private MultiType component;
    private int dims;
    
    public MultiArrayType(final MultiType a1, final int a2) {
        super(null);
        this.component = a1;
        this.dims = a2;
    }
    
    @Override
    public CtClass getCtClass() {
        final CtClass ctClass = this.component.getCtClass();
        if (ctClass == null) {
            return null;
        }
        ClassPool classPool = ctClass.getClassPool();
        if (classPool == null) {
            classPool = ClassPool.getDefault();
        }
        final String v0 = this.arrayName(ctClass.getName(), this.dims);
        try {
            return classPool.get(v0);
        }
        catch (NotFoundException v2) {
            throw new RuntimeException(v2);
        }
    }
    
    @Override
    boolean popChanged() {
        return this.component.popChanged();
    }
    
    @Override
    public int getDimensions() {
        return this.dims;
    }
    
    @Override
    public Type getComponent() {
        return (this.dims == 1) ? this.component : new MultiArrayType(this.component, this.dims - 1);
    }
    
    @Override
    public int getSize() {
        return 1;
    }
    
    @Override
    public boolean isArray() {
        return true;
    }
    
    @Override
    public boolean isAssignableFrom(final Type a1) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public boolean isReference() {
        return true;
    }
    
    public boolean isAssignableTo(final Type a1) {
        if (Type.eq(a1.getCtClass(), Type.OBJECT.getCtClass())) {
            return true;
        }
        if (Type.eq(a1.getCtClass(), Type.CLONEABLE.getCtClass())) {
            return true;
        }
        if (Type.eq(a1.getCtClass(), Type.SERIALIZABLE.getCtClass())) {
            return true;
        }
        if (!a1.isArray()) {
            return false;
        }
        final Type v1 = this.getRootComponent(a1);
        final int v2 = a1.getDimensions();
        if (v2 > this.dims) {
            return false;
        }
        if (v2 < this.dims) {
            return Type.eq(v1.getCtClass(), Type.OBJECT.getCtClass()) || Type.eq(v1.getCtClass(), Type.CLONEABLE.getCtClass()) || Type.eq(v1.getCtClass(), Type.SERIALIZABLE.getCtClass());
        }
        return this.component.isAssignableTo(v1);
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (!(a1 instanceof MultiArrayType)) {
            return false;
        }
        final MultiArrayType v1 = (MultiArrayType)a1;
        return this.component.equals(v1.component) && this.dims == v1.dims;
    }
    
    @Override
    public String toString() {
        return this.arrayName(this.component.toString(), this.dims);
    }
}
