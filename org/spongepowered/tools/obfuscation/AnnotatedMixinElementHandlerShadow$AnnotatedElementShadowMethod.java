package org.spongepowered.tools.obfuscation;

import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;

class AnnotatedElementShadowMethod extends AnnotatedElementShadow<ExecutableElement, MappingMethod>
{
    final /* synthetic */ AnnotatedMixinElementHandlerShadow this$0;
    
    public AnnotatedElementShadowMethod(final AnnotatedMixinElementHandlerShadow a1, final ExecutableElement a2, final AnnotationHandle a3, final boolean a4) {
        this.this$0 = a1;
        super((Element)a2, a3, a4, IMapping.Type.METHOD);
    }
    
    @Override
    public MappingMethod getMapping(final TypeHandle a1, final String a2, final String a3) {
        return a1.getMappingMethod(a2, a3);
    }
    
    @Override
    public void addMapping(final ObfuscationType a1, final IMapping<?> a2) {
        this.this$0.addMethodMapping(a1, this.setObfuscatedName(a2), this.getDesc(), a2.getDesc());
    }
    
    public /* bridge */ IMapping getMapping(final TypeHandle a1, final String a2, final String a3) {
        return this.getMapping(a1, a2, a3);
    }
}
