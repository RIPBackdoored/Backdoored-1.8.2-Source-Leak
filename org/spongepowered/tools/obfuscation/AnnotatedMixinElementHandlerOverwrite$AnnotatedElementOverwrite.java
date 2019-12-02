package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;

static class AnnotatedElementOverwrite extends AnnotatedElement<ExecutableElement>
{
    private final boolean shouldRemap;
    
    public AnnotatedElementOverwrite(final ExecutableElement a1, final AnnotationHandle a2, final boolean a3) {
        super((Element)a1, a2);
        this.shouldRemap = a3;
    }
    
    public boolean shouldRemap() {
        return this.shouldRemap;
    }
}
