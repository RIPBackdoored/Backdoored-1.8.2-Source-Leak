package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.asm.mixin.gen.*;

static class AnnotatedElementInvoker extends AnnotatedElementAccessor
{
    public AnnotatedElementInvoker(final ExecutableElement a1, final AnnotationHandle a2, final boolean a3) {
        super(a1, a2, a3);
    }
    
    @Override
    public String getAccessorDesc() {
        return TypeUtils.getDescriptor((ExecutableElement)this.getElement());
    }
    
    @Override
    public AccessorInfo.AccessorType getAccessorType() {
        return AccessorInfo.AccessorType.METHOD_PROXY;
    }
    
    @Override
    public String getTargetTypeName() {
        return TypeUtils.getJavaSignature(this.getElement());
    }
}
