package javassist;

import java.util.*;
import javassist.bytecode.*;

public class ClassMap extends HashMap
{
    private ClassMap parent;
    
    public ClassMap() {
        super();
        this.parent = null;
    }
    
    ClassMap(final ClassMap a1) {
        super();
        this.parent = a1;
    }
    
    public void put(final CtClass a1, final CtClass a2) {
        this.put(a1.getName(), a2.getName());
    }
    
    public void put(final String a1, final String a2) {
        if (a1 == a2) {
            return;
        }
        final String v1 = toJvmName(a1);
        final String v2 = (String)this.get(v1);
        if (v2 == null || !v2.equals(v1)) {
            super.put(v1, toJvmName(a2));
        }
    }
    
    public void putIfNone(final String a1, final String a2) {
        if (a1 == a2) {
            return;
        }
        final String v1 = toJvmName(a1);
        final String v2 = (String)this.get(v1);
        if (v2 == null) {
            super.put(v1, toJvmName(a2));
        }
    }
    
    protected final void put0(final Object a1, final Object a2) {
        super.put(a1, a2);
    }
    
    @Override
    public Object get(final Object a1) {
        final Object v1 = super.get(a1);
        if (v1 == null && this.parent != null) {
            return this.parent.get(a1);
        }
        return v1;
    }
    
    public void fix(final CtClass a1) {
        this.fix(a1.getName());
    }
    
    public void fix(final String a1) {
        final String v1 = toJvmName(a1);
        super.put(v1, v1);
    }
    
    public static String toJvmName(final String a1) {
        return Descriptor.toJvmName(a1);
    }
    
    public static String toJavaName(final String a1) {
        return Descriptor.toJavaName(a1);
    }
}
