package org.spongepowered.asm.mixin.transformer.ext;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.mixin.*;
import com.google.common.collect.*;
import java.util.*;

public final class Extensions
{
    private final MixinTransformer transformer;
    private final List<IExtension> extensions;
    private final Map<Class<? extends IExtension>, IExtension> extensionMap;
    private final List<IClassGenerator> generators;
    private final List<IClassGenerator> generatorsView;
    private final Map<Class<? extends IClassGenerator>, IClassGenerator> generatorMap;
    private List<IExtension> activeExtensions;
    
    public Extensions(final MixinTransformer a1) {
        super();
        this.extensions = new ArrayList<IExtension>();
        this.extensionMap = new HashMap<Class<? extends IExtension>, IExtension>();
        this.generators = new ArrayList<IClassGenerator>();
        this.generatorsView = Collections.unmodifiableList((List<? extends IClassGenerator>)this.generators);
        this.generatorMap = new HashMap<Class<? extends IClassGenerator>, IClassGenerator>();
        this.activeExtensions = Collections.emptyList();
        this.transformer = a1;
    }
    
    public MixinTransformer getTransformer() {
        return this.transformer;
    }
    
    public void add(final IExtension a1) {
        this.extensions.add(a1);
        this.extensionMap.put(a1.getClass(), a1);
    }
    
    public List<IExtension> getExtensions() {
        return Collections.unmodifiableList((List<? extends IExtension>)this.extensions);
    }
    
    public List<IExtension> getActiveExtensions() {
        return this.activeExtensions;
    }
    
    public <T extends java.lang.Object> T getExtension(final Class<? extends IExtension> a1) {
        return lookup((Class<? extends T>)a1, (Map<Class<? extends T>, T>)this.extensionMap, (List<T>)this.extensions);
    }
    
    public void select(final MixinEnvironment v2) {
        final ImmutableList.Builder<IExtension> v3 = (ImmutableList.Builder<IExtension>)ImmutableList.builder();
        for (final IExtension a1 : this.extensions) {
            if (a1.checkActive(v2)) {
                v3.add(a1);
            }
        }
        this.activeExtensions = v3.build();
    }
    
    public void preApply(final ITargetClassContext v2) {
        for (final IExtension a1 : this.activeExtensions) {
            a1.preApply(v2);
        }
    }
    
    public void postApply(final ITargetClassContext v2) {
        for (final IExtension a1 : this.activeExtensions) {
            a1.postApply(v2);
        }
    }
    
    public void export(final MixinEnvironment a3, final String a4, final boolean v1, final byte[] v2) {
        for (final IExtension a5 : this.activeExtensions) {
            a5.export(a3, a4, v1, v2);
        }
    }
    
    public void add(final IClassGenerator a1) {
        this.generators.add(a1);
        this.generatorMap.put(a1.getClass(), a1);
    }
    
    public List<IClassGenerator> getGenerators() {
        return this.generatorsView;
    }
    
    public <T extends java.lang.Object> T getGenerator(final Class<? extends IClassGenerator> a1) {
        return lookup((Class<? extends T>)a1, (Map<Class<? extends T>, T>)this.generatorMap, (List<T>)this.generators);
    }
    
    private static <T> T lookup(final Class<? extends T> a2, final Map<Class<? extends T>, T> a3, final List<T> v1) {
        T v2 = a3.get(a2);
        if (v2 == null) {
            for (final T a4 : v1) {
                if (a2.isAssignableFrom(a4.getClass())) {
                    v2 = a4;
                    break;
                }
            }
            if (v2 == null) {
                throw new IllegalArgumentException("Extension for <" + a2.getName() + "> could not be found");
            }
            a3.put(a2, v2);
        }
        return v2;
    }
}
