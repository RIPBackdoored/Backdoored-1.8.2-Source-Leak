package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.asm.obfuscation.mapping.*;

static class ShadowElementName extends AliasedElementName
{
    private final boolean hasPrefix;
    private final String prefix;
    private final String baseName;
    private String obfuscated;
    
    ShadowElementName(final Element a1, final AnnotationHandle a2) {
        super(a1, a2);
        this.prefix = a2.getValue("prefix", "shadow$");
        boolean v1 = false;
        String v2 = this.originalName;
        if (v2.startsWith(this.prefix)) {
            v1 = true;
            v2 = v2.substring(this.prefix.length());
        }
        this.hasPrefix = v1;
        final String s = v2;
        this.baseName = s;
        this.obfuscated = s;
    }
    
    @Override
    public String toString() {
        return this.baseName;
    }
    
    @Override
    public String baseName() {
        return this.baseName;
    }
    
    public ShadowElementName setObfuscatedName(final IMapping<?> a1) {
        this.obfuscated = a1.getName();
        return this;
    }
    
    public ShadowElementName setObfuscatedName(final String a1) {
        this.obfuscated = a1;
        return this;
    }
    
    @Override
    public boolean hasPrefix() {
        return this.hasPrefix;
    }
    
    public String prefix() {
        return this.hasPrefix ? this.prefix : "";
    }
    
    public String name() {
        return this.prefix(this.baseName);
    }
    
    public String obfuscated() {
        return this.prefix(this.obfuscated);
    }
    
    public String prefix(final String a1) {
        return this.hasPrefix ? (this.prefix + a1) : a1;
    }
}
