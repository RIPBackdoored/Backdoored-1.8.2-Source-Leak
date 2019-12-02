package javassist.bytecode.analysis;

import javassist.*;
import java.util.*;

public class MultiType extends Type
{
    private Map interfaces;
    private Type resolved;
    private Type potentialClass;
    private MultiType mergeSource;
    private boolean changed;
    
    public MultiType(final Map a1) {
        this(a1, null);
    }
    
    public MultiType(final Map a1, final Type a2) {
        super(null);
        this.changed = false;
        this.interfaces = a1;
        this.potentialClass = a2;
    }
    
    @Override
    public CtClass getCtClass() {
        if (this.resolved != null) {
            return this.resolved.getCtClass();
        }
        return Type.OBJECT.getCtClass();
    }
    
    @Override
    public Type getComponent() {
        return null;
    }
    
    @Override
    public int getSize() {
        return 1;
    }
    
    @Override
    public boolean isArray() {
        return false;
    }
    
    @Override
    boolean popChanged() {
        final boolean v1 = this.changed;
        this.changed = false;
        return v1;
    }
    
    @Override
    public boolean isAssignableFrom(final Type a1) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public boolean isAssignableTo(final Type a1) {
        if (this.resolved != null) {
            return a1.isAssignableFrom(this.resolved);
        }
        if (Type.OBJECT.equals(a1)) {
            return true;
        }
        if (this.potentialClass != null && !a1.isAssignableFrom(this.potentialClass)) {
            this.potentialClass = null;
        }
        final Map v1 = this.mergeMultiAndSingle(this, a1);
        if (v1.size() == 1 && this.potentialClass == null) {
            this.resolved = Type.get(v1.values().iterator().next());
            this.propogateResolved();
            return true;
        }
        if (v1.size() >= 1) {
            this.interfaces = v1;
            this.propogateState();
            return true;
        }
        if (this.potentialClass != null) {
            this.resolved = this.potentialClass;
            this.propogateResolved();
            return true;
        }
        return false;
    }
    
    private void propogateState() {
        for (MultiType v1 = this.mergeSource; v1 != null; v1 = v1.mergeSource) {
            v1.interfaces = this.interfaces;
            v1.potentialClass = this.potentialClass;
        }
    }
    
    private void propogateResolved() {
        for (MultiType v1 = this.mergeSource; v1 != null; v1 = v1.mergeSource) {
            v1.resolved = this.resolved;
        }
    }
    
    @Override
    public boolean isReference() {
        return true;
    }
    
    private Map getAllMultiInterfaces(final MultiType v2) {
        final Map v3 = new HashMap();
        for (final CtClass a1 : v2.interfaces.values()) {
            v3.put(a1.getName(), a1);
            this.getAllInterfaces(a1, v3);
        }
        return v3;
    }
    
    private Map mergeMultiInterfaces(final MultiType a1, final MultiType a2) {
        final Map v1 = this.getAllMultiInterfaces(a1);
        final Map v2 = this.getAllMultiInterfaces(a2);
        return this.findCommonInterfaces(v1, v2);
    }
    
    private Map mergeMultiAndSingle(final MultiType a1, final Type a2) {
        final Map v1 = this.getAllMultiInterfaces(a1);
        final Map v2 = this.getAllInterfaces(a2.getCtClass(), null);
        return this.findCommonInterfaces(v1, v2);
    }
    
    private boolean inMergeSource(MultiType a1) {
        while (a1 != null) {
            if (a1 == this) {
                return true;
            }
            a1 = a1.mergeSource;
        }
        return false;
    }
    
    @Override
    public Type merge(final Type v0) {
        if (this == v0) {
            return this;
        }
        if (v0 == MultiType.UNINIT) {
            return this;
        }
        if (v0 == MultiType.BOGUS) {
            return MultiType.BOGUS;
        }
        if (v0 == null) {
            return this;
        }
        if (this.resolved != null) {
            return this.resolved.merge(v0);
        }
        if (this.potentialClass != null) {
            final Type a1 = this.potentialClass.merge(v0);
            if (!a1.equals(this.potentialClass) || a1.popChanged()) {
                this.potentialClass = (Type.OBJECT.equals(a1) ? null : a1);
                this.changed = true;
            }
        }
        Map v2;
        if (v0 instanceof MultiType) {
            final MultiType v = (MultiType)v0;
            if (v.resolved != null) {
                v2 = this.mergeMultiAndSingle(this, v.resolved);
            }
            else {
                v2 = this.mergeMultiInterfaces(v, this);
                if (!this.inMergeSource(v)) {
                    this.mergeSource = v;
                }
            }
        }
        else {
            v2 = this.mergeMultiAndSingle(this, v0);
        }
        if (v2.size() > 1 || (v2.size() == 1 && this.potentialClass != null)) {
            if (v2.size() != this.interfaces.size()) {
                this.changed = true;
            }
            else if (!this.changed) {
                final Iterator v3 = v2.keySet().iterator();
                while (v3.hasNext()) {
                    if (!this.interfaces.containsKey(v3.next())) {
                        this.changed = true;
                    }
                }
            }
            this.interfaces = v2;
            this.propogateState();
            return this;
        }
        if (v2.size() == 1) {
            this.resolved = Type.get(v2.values().iterator().next());
        }
        else if (this.potentialClass != null) {
            this.resolved = this.potentialClass;
        }
        else {
            this.resolved = MultiType.OBJECT;
        }
        this.propogateResolved();
        return this.resolved;
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (!(a1 instanceof MultiType)) {
            return false;
        }
        final MultiType v1 = (MultiType)a1;
        if (this.resolved != null) {
            return this.resolved.equals(v1.resolved);
        }
        return v1.resolved == null && this.interfaces.keySet().equals(v1.interfaces.keySet());
    }
    
    @Override
    public String toString() {
        if (this.resolved != null) {
            return this.resolved.toString();
        }
        final StringBuffer v1 = new StringBuffer("{");
        final Iterator v2 = this.interfaces.keySet().iterator();
        while (v2.hasNext()) {
            v1.append(v2.next());
            v1.append(", ");
        }
        v1.setLength(v1.length() - 2);
        if (this.potentialClass != null) {
            v1.append(", *").append(this.potentialClass.toString());
        }
        v1.append("}");
        return v1.toString();
    }
}
