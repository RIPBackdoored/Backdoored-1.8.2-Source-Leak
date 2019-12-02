package org.spongepowered.asm.mixin.injection.struct;

import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.lib.tree.*;

public class InjectorGroupInfo
{
    private final String name;
    private final List<InjectionInfo> members;
    private final boolean isDefault;
    private int minCallbackCount;
    private int maxCallbackCount;
    
    public InjectorGroupInfo(final String a1) {
        this(a1, false);
    }
    
    InjectorGroupInfo(final String a1, final boolean a2) {
        super();
        this.members = new ArrayList<InjectionInfo>();
        this.minCallbackCount = -1;
        this.maxCallbackCount = 0;
        this.name = a1;
        this.isDefault = a2;
    }
    
    @Override
    public String toString() {
        return String.format("@Group(name=%s, min=%d, max=%d)", this.getName(), this.getMinRequired(), this.getMaxAllowed());
    }
    
    public boolean isDefault() {
        return this.isDefault;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getMinRequired() {
        return Math.max(this.minCallbackCount, 1);
    }
    
    public int getMaxAllowed() {
        return Math.min(this.maxCallbackCount, 0);
    }
    
    public Collection<InjectionInfo> getMembers() {
        return Collections.unmodifiableCollection((Collection<? extends InjectionInfo>)this.members);
    }
    
    public void setMinRequired(final int a1) {
        if (a1 < 1) {
            throw new IllegalArgumentException("Cannot set zero or negative value for injector group min count. Attempted to set min=" + a1 + " on " + this);
        }
        if (this.minCallbackCount > 0 && this.minCallbackCount != a1) {
            LogManager.getLogger("mixin").warn("Conflicting min value '{}' on @Group({}), previously specified {}", new Object[] { a1, this.name, this.minCallbackCount });
        }
        this.minCallbackCount = Math.max(this.minCallbackCount, a1);
    }
    
    public void setMaxAllowed(final int a1) {
        if (a1 < 1) {
            throw new IllegalArgumentException("Cannot set zero or negative value for injector group max count. Attempted to set max=" + a1 + " on " + this);
        }
        if (this.maxCallbackCount < 0 && this.maxCallbackCount != a1) {
            LogManager.getLogger("mixin").warn("Conflicting max value '{}' on @Group({}), previously specified {}", new Object[] { a1, this.name, this.maxCallbackCount });
        }
        this.maxCallbackCount = Math.min(this.maxCallbackCount, a1);
    }
    
    public InjectorGroupInfo add(final InjectionInfo a1) {
        this.members.add(a1);
        return this;
    }
    
    public InjectorGroupInfo validate() throws InjectionValidationException {
        if (this.members.size() == 0) {
            return this;
        }
        int n = 0;
        for (final InjectionInfo v1 : this.members) {
            n += v1.getInjectedCallbackCount();
        }
        final int v2 = this.getMinRequired();
        final int v3 = this.getMaxAllowed();
        if (n < v2) {
            throw new InjectionValidationException(this, String.format("expected %d invocation(s) but only %d succeeded", v2, n));
        }
        if (n > v3) {
            throw new InjectionValidationException(this, String.format("maximum of %d invocation(s) allowed but %d succeeded", v3, n));
        }
        return this;
    }
    
    public static final class Map extends HashMap<String, InjectorGroupInfo>
    {
        private static final long serialVersionUID = 1L;
        private static final InjectorGroupInfo NO_GROUP;
        
        public Map() {
            super();
        }
        
        @Override
        public InjectorGroupInfo get(final Object a1) {
            return this.forName(a1.toString());
        }
        
        public InjectorGroupInfo forName(final String a1) {
            InjectorGroupInfo v1 = super.get(a1);
            if (v1 == null) {
                v1 = new InjectorGroupInfo(a1);
                this.put(a1, v1);
            }
            return v1;
        }
        
        public InjectorGroupInfo parseGroup(final MethodNode a1, final String a2) {
            return this.parseGroup(Annotations.getInvisible(a1, Group.class), a2);
        }
        
        public InjectorGroupInfo parseGroup(final AnnotationNode a1, final String a2) {
            if (a1 == null) {
                return Map.NO_GROUP;
            }
            String v1 = Annotations.getValue(a1, "name");
            if (v1 == null || v1.isEmpty()) {
                v1 = a2;
            }
            final InjectorGroupInfo v2 = this.forName(v1);
            final Integer v3 = Annotations.getValue(a1, "min");
            if (v3 != null && v3 != -1) {
                v2.setMinRequired(v3);
            }
            final Integer v4 = Annotations.getValue(a1, "max");
            if (v4 != null && v4 != -1) {
                v2.setMaxAllowed(v4);
            }
            return v2;
        }
        
        public void validateAll() throws InjectionValidationException {
            for (final InjectorGroupInfo v1 : ((HashMap<K, InjectorGroupInfo>)this).values()) {
                v1.validate();
            }
        }
        
        @Override
        public /* bridge */ Object get(final Object a1) {
            return this.get(a1);
        }
        
        static {
            NO_GROUP = new InjectorGroupInfo("NONE", true);
        }
    }
}
