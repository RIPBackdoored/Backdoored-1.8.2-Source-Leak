package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class SimpleVerifier extends BasicVerifier
{
    private final Type currentClass;
    private final Type currentSuperClass;
    private final List<Type> currentClassInterfaces;
    private final boolean isInterface;
    private ClassLoader loader;
    
    public SimpleVerifier() {
        this(null, null, false);
    }
    
    public SimpleVerifier(final Type a1, final Type a2, final boolean a3) {
        this(a1, a2, null, a3);
    }
    
    public SimpleVerifier(final Type a1, final Type a2, final List<Type> a3, final boolean a4) {
        this(327680, a1, a2, a3, a4);
    }
    
    protected SimpleVerifier(final int a1, final Type a2, final Type a3, final List<Type> a4, final boolean a5) {
        super(a1);
        this.loader = this.getClass().getClassLoader();
        this.currentClass = a2;
        this.currentSuperClass = a3;
        this.currentClassInterfaces = a4;
        this.isInterface = a5;
    }
    
    public void setClassLoader(final ClassLoader a1) {
        this.loader = a1;
    }
    
    @Override
    public BasicValue newValue(final Type v-2) {
        if (v-2 == null) {
            return BasicValue.UNINITIALIZED_VALUE;
        }
        final boolean b = v-2.getSort() == 9;
        if (b) {
            switch (v-2.getElementType().getSort()) {
                case 1:
                case 2:
                case 3:
                case 4: {
                    return new BasicValue(v-2);
                }
            }
        }
        BasicValue v0 = super.newValue(v-2);
        if (BasicValue.REFERENCE_VALUE.equals(v0)) {
            if (b) {
                v0 = this.newValue(v-2.getElementType());
                String v2 = v0.getType().getDescriptor();
                for (int a1 = 0; a1 < v-2.getDimensions(); ++a1) {
                    v2 = '[' + v2;
                }
                v0 = new BasicValue(Type.getType(v2));
            }
            else {
                v0 = new BasicValue(v-2);
            }
        }
        return v0;
    }
    
    @Override
    protected boolean isArrayValue(final BasicValue a1) {
        final Type v1 = a1.getType();
        return v1 != null && ("Lnull;".equals(v1.getDescriptor()) || v1.getSort() == 9);
    }
    
    @Override
    protected BasicValue getElementValue(final BasicValue a1) throws AnalyzerException {
        final Type v1 = a1.getType();
        if (v1 != null) {
            if (v1.getSort() == 9) {
                return this.newValue(Type.getType(v1.getDescriptor().substring(1)));
            }
            if ("Lnull;".equals(v1.getDescriptor())) {
                return a1;
            }
        }
        throw new Error("Internal error");
    }
    
    @Override
    protected boolean isSubTypeOf(final BasicValue a1, final BasicValue a2) {
        final Type v1 = a2.getType();
        final Type v2 = a1.getType();
        switch (v1.getSort()) {
            case 5:
            case 6:
            case 7:
            case 8: {
                return v2.equals(v1);
            }
            case 9:
            case 10: {
                return "Lnull;".equals(v2.getDescriptor()) || ((v2.getSort() == 10 || v2.getSort() == 9) && this.isAssignableFrom(v1, v2));
            }
            default: {
                throw new Error("Internal error");
            }
        }
    }
    
    @Override
    public BasicValue merge(final BasicValue v2, final BasicValue v3) {
        if (v2.equals(v3)) {
            return v2;
        }
        Type a1 = v2.getType();
        final Type a2 = v3.getType();
        if (a1 == null || (a1.getSort() != 10 && a1.getSort() != 9) || a2 == null || (a2.getSort() != 10 && a2.getSort() != 9)) {
            return BasicValue.UNINITIALIZED_VALUE;
        }
        if ("Lnull;".equals(a1.getDescriptor())) {
            return v3;
        }
        if ("Lnull;".equals(a2.getDescriptor())) {
            return v2;
        }
        if (this.isAssignableFrom(a1, a2)) {
            return v2;
        }
        if (this.isAssignableFrom(a2, a1)) {
            return v3;
        }
        while (a1 != null && !this.isInterface(a1)) {
            a1 = this.getSuperClass(a1);
            if (this.isAssignableFrom(a1, a2)) {
                return this.newValue(a1);
            }
        }
        return BasicValue.REFERENCE_VALUE;
    }
    
    protected boolean isInterface(final Type a1) {
        if (this.currentClass != null && a1.equals(this.currentClass)) {
            return this.isInterface;
        }
        return this.getClass(a1).isInterface();
    }
    
    protected Type getSuperClass(final Type a1) {
        if (this.currentClass != null && a1.equals(this.currentClass)) {
            return this.currentSuperClass;
        }
        final Class<?> v1 = this.getClass(a1).getSuperclass();
        return (v1 == null) ? null : Type.getType(v1);
    }
    
    protected boolean isAssignableFrom(final Type v2, final Type v3) {
        if (v2.equals(v3)) {
            return true;
        }
        if (this.currentClass != null && v2.equals(this.currentClass)) {
            if (this.getSuperClass(v3) == null) {
                return false;
            }
            if (this.isInterface) {
                return v3.getSort() == 10 || v3.getSort() == 9;
            }
            return this.isAssignableFrom(v2, this.getSuperClass(v3));
        }
        else {
            if (this.currentClass == null || !v3.equals(this.currentClass)) {
                Class<?> v4 = this.getClass(v2);
                if (v4.isInterface()) {
                    v4 = Object.class;
                }
                return v4.isAssignableFrom(this.getClass(v3));
            }
            if (this.isAssignableFrom(v2, this.currentSuperClass)) {
                return true;
            }
            if (this.currentClassInterfaces != null) {
                for (int a2 = 0; a2 < this.currentClassInterfaces.size(); ++a2) {
                    final Type a3 = this.currentClassInterfaces.get(a2);
                    if (this.isAssignableFrom(v2, a3)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
    
    protected Class<?> getClass(final Type v2) {
        try {
            if (v2.getSort() == 9) {
                return Class.forName(v2.getDescriptor().replace('/', '.'), false, this.loader);
            }
            return Class.forName(v2.getClassName(), false, this.loader);
        }
        catch (ClassNotFoundException a1) {
            throw new RuntimeException(a1.toString());
        }
    }
    
    public /* bridge */ Value merge(final Value value, final Value value2) {
        return this.merge((BasicValue)value, (BasicValue)value2);
    }
    
    public /* bridge */ Value newValue(final Type v-2) {
        return this.newValue(v-2);
    }
}
