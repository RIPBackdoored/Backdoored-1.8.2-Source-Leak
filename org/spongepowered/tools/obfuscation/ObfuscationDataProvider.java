package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import java.util.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;

public class ObfuscationDataProvider implements IObfuscationDataProvider
{
    private final IMixinAnnotationProcessor ap;
    private final List<ObfuscationEnvironment> environments;
    
    public ObfuscationDataProvider(final IMixinAnnotationProcessor a1, final List<ObfuscationEnvironment> a2) {
        super();
        this.ap = a1;
        this.environments = a2;
    }
    
    @Override
    public <T> ObfuscationData<T> getObfEntryRecursive(final MemberInfo v-3) {
        MemberInfo move = v-3;
        final ObfuscationData<String> obfClass = this.getObfClass(move.owner);
        ObfuscationData<T> v0 = (ObfuscationData<T>)this.getObfEntry(move);
        try {
            while (v0.isEmpty()) {
                final TypeHandle v2 = this.ap.getTypeProvider().getTypeHandle(move.owner);
                if (v2 == null) {
                    return v0;
                }
                final TypeHandle v3 = v2.getSuperclass();
                v0 = this.getObfEntryUsing(move, v3);
                if (!v0.isEmpty()) {
                    return applyParents(obfClass, v0);
                }
                for (final TypeHandle a1 : v2.getInterfaces()) {
                    v0 = this.getObfEntryUsing(move, a1);
                    if (!v0.isEmpty()) {
                        return applyParents(obfClass, v0);
                    }
                }
                if (v3 == null) {
                    break;
                }
                move = move.move(v3.getName());
            }
        }
        catch (Exception v4) {
            v4.printStackTrace();
            return (ObfuscationData<T>)this.getObfEntry(v-3);
        }
        return v0;
    }
    
    private <T> ObfuscationData<T> getObfEntryUsing(final MemberInfo a1, final TypeHandle a2) {
        return (a2 == null) ? new ObfuscationData<T>() : this.getObfEntry(a1.move(a2.getName()));
    }
    
    @Override
    public <T> ObfuscationData<T> getObfEntry(final MemberInfo a1) {
        if (a1.isField()) {
            return (ObfuscationData<T>)this.getObfField(a1);
        }
        return (ObfuscationData<T>)this.getObfMethod(a1.asMethodMapping());
    }
    
    @Override
    public <T> ObfuscationData<T> getObfEntry(final IMapping<T> a1) {
        if (a1 != null) {
            if (a1.getType() == IMapping.Type.FIELD) {
                return (ObfuscationData<T>)this.getObfField((MappingField)a1);
            }
            if (a1.getType() == IMapping.Type.METHOD) {
                return (ObfuscationData<T>)this.getObfMethod((MappingMethod)a1);
            }
        }
        return new ObfuscationData<T>();
    }
    
    @Override
    public ObfuscationData<MappingMethod> getObfMethodRecursive(final MemberInfo a1) {
        return this.getObfEntryRecursive(a1);
    }
    
    @Override
    public ObfuscationData<MappingMethod> getObfMethod(final MemberInfo a1) {
        return this.getRemappedMethod(a1, a1.isConstructor());
    }
    
    @Override
    public ObfuscationData<MappingMethod> getRemappedMethod(final MemberInfo a1) {
        return this.getRemappedMethod(a1, true);
    }
    
    private ObfuscationData<MappingMethod> getRemappedMethod(final MemberInfo v2, final boolean v3) {
        final ObfuscationData<MappingMethod> v4 = new ObfuscationData<MappingMethod>();
        for (final ObfuscationEnvironment a2 : this.environments) {
            final MappingMethod a3 = a2.getObfMethod(v2);
            if (a3 != null) {
                v4.put(a2.getType(), a3);
            }
        }
        if (!v4.isEmpty() || !v3) {
            return v4;
        }
        return this.remapDescriptor(v4, v2);
    }
    
    @Override
    public ObfuscationData<MappingMethod> getObfMethod(final MappingMethod a1) {
        return this.getRemappedMethod(a1, a1.isConstructor());
    }
    
    @Override
    public ObfuscationData<MappingMethod> getRemappedMethod(final MappingMethod a1) {
        return this.getRemappedMethod(a1, true);
    }
    
    private ObfuscationData<MappingMethod> getRemappedMethod(final MappingMethod v2, final boolean v3) {
        final ObfuscationData<MappingMethod> v4 = new ObfuscationData<MappingMethod>();
        for (final ObfuscationEnvironment a2 : this.environments) {
            final MappingMethod a3 = a2.getObfMethod(v2);
            if (a3 != null) {
                v4.put(a2.getType(), a3);
            }
        }
        if (!v4.isEmpty() || !v3) {
            return v4;
        }
        return this.remapDescriptor(v4, new MemberInfo(v2));
    }
    
    public ObfuscationData<MappingMethod> remapDescriptor(final ObfuscationData<MappingMethod> v2, final MemberInfo v3) {
        for (final ObfuscationEnvironment a2 : this.environments) {
            final MemberInfo a3 = a2.remapDescriptor(v3);
            if (a3 != null) {
                v2.put(a2.getType(), a3.asMethodMapping());
            }
        }
        return v2;
    }
    
    @Override
    public ObfuscationData<MappingField> getObfFieldRecursive(final MemberInfo a1) {
        return this.getObfEntryRecursive(a1);
    }
    
    @Override
    public ObfuscationData<MappingField> getObfField(final MemberInfo a1) {
        return this.getObfField(a1.asFieldMapping());
    }
    
    @Override
    public ObfuscationData<MappingField> getObfField(final MappingField v-2) {
        final ObfuscationData<MappingField> obfuscationData = new ObfuscationData<MappingField>();
        for (final ObfuscationEnvironment v1 : this.environments) {
            MappingField a1 = v1.getObfField(v-2);
            if (a1 != null) {
                if (a1.getDesc() == null && v-2.getDesc() != null) {
                    a1 = a1.transform(v1.remapDescriptor(v-2.getDesc()));
                }
                obfuscationData.put(v1.getType(), a1);
            }
        }
        return obfuscationData;
    }
    
    @Override
    public ObfuscationData<String> getObfClass(final TypeHandle a1) {
        return this.getObfClass(a1.getName());
    }
    
    @Override
    public ObfuscationData<String> getObfClass(final String v-2) {
        final ObfuscationData<String> obfuscationData = new ObfuscationData<String>(v-2);
        for (final ObfuscationEnvironment v1 : this.environments) {
            final String a1 = v1.getObfClass(v-2);
            if (a1 != null) {
                obfuscationData.put(v1.getType(), a1);
            }
        }
        return obfuscationData;
    }
    
    private static <T> ObfuscationData<T> applyParents(final ObfuscationData<String> v-2, final ObfuscationData<T> v-1) {
        for (final ObfuscationType v1 : v-1) {
            final String a1 = v-2.get(v1);
            final T a2 = v-1.get(v1);
            v-1.put(v1, (T)MemberInfo.fromMapping((IMapping<?>)a2).move(a1).asMapping());
        }
        return v-1;
    }
}
