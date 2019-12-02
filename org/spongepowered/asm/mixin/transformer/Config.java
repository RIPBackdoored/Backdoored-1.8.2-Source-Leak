package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.mixin.*;

public class Config
{
    private final String name;
    private final MixinConfig config;
    
    public Config(final MixinConfig a1) {
        super();
        this.name = a1.getName();
        this.config = a1;
    }
    
    public String getName() {
        return this.name;
    }
    
    MixinConfig get() {
        return this.config;
    }
    
    public boolean isVisited() {
        return this.config.isVisited();
    }
    
    public IMixinConfig getConfig() {
        return this.config;
    }
    
    public MixinEnvironment getEnvironment() {
        return this.config.getEnvironment();
    }
    
    @Override
    public String toString() {
        return this.config.toString();
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof Config && this.name.equals(((Config)a1).name);
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    @Deprecated
    public static Config create(final String a1, final MixinEnvironment a2) {
        return MixinConfig.create(a1, a2);
    }
    
    public static Config create(final String a1) {
        return MixinConfig.create(a1, MixinEnvironment.getDefaultEnvironment());
    }
}
