package org.spongepowered.asm.service.mojang;

import net.minecraft.launchwrapper.*;
import java.util.*;
import java.lang.reflect.*;

final class LaunchClassLoaderUtil
{
    private static final String CACHED_CLASSES_FIELD = "cachedClasses";
    private static final String INVALID_CLASSES_FIELD = "invalidClasses";
    private static final String CLASS_LOADER_EXCEPTIONS_FIELD = "classLoaderExceptions";
    private static final String TRANSFORMER_EXCEPTIONS_FIELD = "transformerExceptions";
    private final LaunchClassLoader classLoader;
    private final Map<String, Class<?>> cachedClasses;
    private final Set<String> invalidClasses;
    private final Set<String> classLoaderExceptions;
    private final Set<String> transformerExceptions;
    
    LaunchClassLoaderUtil(final LaunchClassLoader a1) {
        super();
        this.classLoader = a1;
        this.cachedClasses = getField(a1, "cachedClasses");
        this.invalidClasses = getField(a1, "invalidClasses");
        this.classLoaderExceptions = getField(a1, "classLoaderExceptions");
        this.transformerExceptions = getField(a1, "transformerExceptions");
    }
    
    LaunchClassLoader getClassLoader() {
        return this.classLoader;
    }
    
    boolean isClassLoaded(final String a1) {
        return this.cachedClasses.containsKey(a1);
    }
    
    boolean isClassExcluded(final String a1, final String a2) {
        return this.isClassClassLoaderExcluded(a1, a2) || this.isClassTransformerExcluded(a1, a2);
    }
    
    boolean isClassClassLoaderExcluded(final String v1, final String v2) {
        for (final String a1 : this.getClassLoaderExceptions()) {
            if ((v2 != null && v2.startsWith(a1)) || v1.startsWith(a1)) {
                return true;
            }
        }
        return false;
    }
    
    boolean isClassTransformerExcluded(final String v1, final String v2) {
        for (final String a1 : this.getTransformerExceptions()) {
            if ((v2 != null && v2.startsWith(a1)) || v1.startsWith(a1)) {
                return true;
            }
        }
        return false;
    }
    
    void registerInvalidClass(final String a1) {
        if (this.invalidClasses != null) {
            this.invalidClasses.add(a1);
        }
    }
    
    Set<String> getClassLoaderExceptions() {
        if (this.classLoaderExceptions != null) {
            return this.classLoaderExceptions;
        }
        return Collections.emptySet();
    }
    
    Set<String> getTransformerExceptions() {
        if (this.transformerExceptions != null) {
            return this.transformerExceptions;
        }
        return Collections.emptySet();
    }
    
    private static <T> T getField(final LaunchClassLoader v1, final String v2) {
        try {
            final Field a1 = LaunchClassLoader.class.getDeclaredField(v2);
            a1.setAccessible(true);
            return (T)a1.get(v1);
        }
        catch (Exception a2) {
            a2.printStackTrace();
            return null;
        }
    }
}
