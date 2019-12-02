package org.spongepowered.tools.obfuscation.interfaces;

import javax.lang.model.element.*;
import java.util.*;
import org.spongepowered.tools.obfuscation.mirror.*;

public interface IMixinValidator
{
    boolean validate(final ValidationPass p0, final TypeElement p1, final AnnotationHandle p2, final Collection<TypeHandle> p3);
    
    public enum ValidationPass
    {
        EARLY, 
        LATE, 
        FINAL;
        
        private static final /* synthetic */ ValidationPass[] $VALUES;
        
        public static ValidationPass[] values() {
            return ValidationPass.$VALUES.clone();
        }
        
        public static ValidationPass valueOf(final String a1) {
            return Enum.valueOf(ValidationPass.class, a1);
        }
        
        static {
            $VALUES = new ValidationPass[] { ValidationPass.EARLY, ValidationPass.LATE, ValidationPass.FINAL };
        }
    }
}
