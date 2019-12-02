package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.service.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import java.lang.reflect.*;
import com.google.common.collect.*;
import java.util.*;

public final class ObfuscationType
{
    private static final Map<String, ObfuscationType> types;
    private final String key;
    private final ObfuscationTypeDescriptor descriptor;
    private final IMixinAnnotationProcessor ap;
    private final IOptionProvider options;
    
    private ObfuscationType(final ObfuscationTypeDescriptor a1, final IMixinAnnotationProcessor a2) {
        super();
        this.key = a1.getKey();
        this.descriptor = a1;
        this.ap = a2;
        this.options = a2;
    }
    
    public final ObfuscationEnvironment createEnvironment() {
        try {
            final Class<? extends ObfuscationEnvironment> v1 = this.descriptor.getEnvironmentType();
            final Constructor<? extends ObfuscationEnvironment> v2 = v1.getDeclaredConstructor(ObfuscationType.class);
            v2.setAccessible(true);
            return (ObfuscationEnvironment)v2.newInstance(this);
        }
        catch (Exception v3) {
            throw new RuntimeException(v3);
        }
    }
    
    @Override
    public String toString() {
        return this.key;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public ObfuscationTypeDescriptor getConfig() {
        return this.descriptor;
    }
    
    public IMixinAnnotationProcessor getAnnotationProcessor() {
        return this.ap;
    }
    
    public boolean isDefault() {
        final String v1 = this.options.getOption("defaultObfuscationEnv");
        return (v1 == null && this.key.equals("searge")) || (v1 != null && this.key.equals(v1.toLowerCase()));
    }
    
    public boolean isSupported() {
        return this.getInputFileNames().size() > 0;
    }
    
    public List<String> getInputFileNames() {
        final ImmutableList.Builder<String> builder = (ImmutableList.Builder<String>)ImmutableList.builder();
        final String option = this.options.getOption(this.descriptor.getInputFileOption());
        if (option != null) {
            builder.add(option);
        }
        final String option2 = this.options.getOption(this.descriptor.getExtraInputFilesOption());
        if (option2 != null) {
            for (final String v1 : option2.split(";")) {
                builder.add(v1.trim());
            }
        }
        return builder.build();
    }
    
    public String getOutputFileName() {
        return this.options.getOption(this.descriptor.getOutputFileOption());
    }
    
    public static Iterable<ObfuscationType> types() {
        return ObfuscationType.types.values();
    }
    
    public static ObfuscationType create(final ObfuscationTypeDescriptor a1, final IMixinAnnotationProcessor a2) {
        final String v1 = a1.getKey();
        if (ObfuscationType.types.containsKey(v1)) {
            throw new IllegalArgumentException("Obfuscation type with key " + v1 + " was already registered");
        }
        final ObfuscationType v2 = new ObfuscationType(a1, a2);
        ObfuscationType.types.put(v1, v2);
        return v2;
    }
    
    public static ObfuscationType get(final String a1) {
        final ObfuscationType v1 = ObfuscationType.types.get(a1);
        if (v1 == null) {
            throw new IllegalArgumentException("Obfuscation type with key " + a1 + " was not registered");
        }
        return v1;
    }
    
    static {
        types = new LinkedHashMap<String, ObfuscationType>();
    }
}
