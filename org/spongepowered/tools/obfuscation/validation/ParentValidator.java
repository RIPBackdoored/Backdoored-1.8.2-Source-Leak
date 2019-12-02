package org.spongepowered.tools.obfuscation.validation;

import org.spongepowered.tools.obfuscation.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import java.util.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;

public class ParentValidator extends MixinValidator
{
    public ParentValidator(final IMixinAnnotationProcessor a1) {
        super(a1, IMixinValidator.ValidationPass.EARLY);
    }
    
    public boolean validate(final TypeElement a1, final AnnotationHandle a2, final Collection<TypeHandle> a3) {
        if (a1.getEnclosingElement().getKind() != ElementKind.PACKAGE && !a1.getModifiers().contains(Modifier.STATIC)) {
            this.error("Inner class mixin must be declared static", a1);
        }
        return true;
    }
}
