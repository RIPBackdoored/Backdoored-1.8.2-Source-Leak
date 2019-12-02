package org.spongepowered.tools.obfuscation;

import org.spongepowered.asm.obfuscation.mapping.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.tools.obfuscation.mirror.*;

abstract static class AnnotatedElementShadow<E extends java.lang.Object, M extends java.lang.Object> extends AnnotatedElement<E>
{
    private final boolean shouldRemap;
    private final ShadowElementName name;
    private final IMapping.Type type;
    
    protected AnnotatedElementShadow(final E a1, final AnnotationHandle a2, final boolean a3, final IMapping.Type a4) {
        super((Element)a1, a2);
        this.shouldRemap = a3;
        this.name = new ShadowElementName((Element)a1, a2);
        this.type = a4;
    }
    
    public boolean shouldRemap() {
        return this.shouldRemap;
    }
    
    public ShadowElementName getName() {
        return this.name;
    }
    
    public IMapping.Type getElementType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return this.getElementType().name().toLowerCase();
    }
    
    public ShadowElementName setObfuscatedName(final IMapping<?> a1) {
        return this.setObfuscatedName(a1.getSimpleName());
    }
    
    public ShadowElementName setObfuscatedName(final String a1) {
        return this.getName().setObfuscatedName(a1);
    }
    
    public ObfuscationData<M> getObfuscationData(final IObfuscationDataProvider a1, final TypeHandle a2) {
        return a1.getObfEntry((IMapping<M>)this.getMapping(a2, this.getName().toString(), this.getDesc()));
    }
    
    public abstract M getMapping(final TypeHandle p0, final String p1, final String p2);
    
    public abstract void addMapping(final ObfuscationType p0, final IMapping<?> p1);
}
