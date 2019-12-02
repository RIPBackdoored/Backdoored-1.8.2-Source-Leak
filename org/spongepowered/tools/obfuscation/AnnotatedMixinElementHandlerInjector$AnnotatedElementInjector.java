package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.struct.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;
import org.spongepowered.asm.mixin.injection.*;
import java.lang.annotation.*;
import java.util.*;
import javax.tools.*;

static class AnnotatedElementInjector extends AnnotatedElement<ExecutableElement>
{
    private final InjectorRemap state;
    
    public AnnotatedElementInjector(final ExecutableElement a1, final AnnotationHandle a2, final InjectorRemap a3) {
        super((Element)a1, a2);
        this.state = a3;
    }
    
    public boolean shouldRemap() {
        return this.state.shouldRemap();
    }
    
    public boolean hasCoerceArgument() {
        if (!this.annotation.toString().equals("@Inject")) {
            return false;
        }
        final Iterator<? extends VariableElement> iterator = ((ExecutableElement)this.element).getParameters().iterator();
        if (iterator.hasNext()) {
            final VariableElement v1 = (VariableElement)iterator.next();
            return AnnotationHandle.of(v1, Coerce.class).exists();
        }
        return false;
    }
    
    public void addMessage(final Diagnostic.Kind a1, final CharSequence a2, final Element a3, final AnnotationHandle a4) {
        this.state.addMessage(a1, a2, a3, a4);
    }
    
    @Override
    public String toString() {
        return this.getAnnotation().toString();
    }
}
