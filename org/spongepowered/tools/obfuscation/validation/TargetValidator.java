package org.spongepowered.tools.obfuscation.validation;

import org.spongepowered.tools.obfuscation.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import javax.lang.model.element.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.gen.*;
import java.util.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.type.*;

public class TargetValidator extends MixinValidator
{
    public TargetValidator(final IMixinAnnotationProcessor a1) {
        super(a1, IMixinValidator.ValidationPass.LATE);
    }
    
    public boolean validate(final TypeElement a1, final AnnotationHandle a2, final Collection<TypeHandle> a3) {
        if ("true".equalsIgnoreCase(this.options.getOption("disableTargetValidator"))) {
            return true;
        }
        if (a1.getKind() == ElementKind.INTERFACE) {
            this.validateInterfaceMixin(a1, a3);
        }
        else {
            this.validateClassMixin(a1, a3);
        }
        return true;
    }
    
    private void validateInterfaceMixin(final TypeElement v-3, final Collection<TypeHandle> v-2) {
        boolean b = false;
        for (final Element v1 : v-3.getEnclosedElements()) {
            if (v1.getKind() == ElementKind.METHOD) {
                final boolean a1 = AnnotationHandle.of(v1, Accessor.class).exists();
                final boolean a2 = AnnotationHandle.of(v1, Invoker.class).exists();
                b |= (!a1 && !a2);
            }
        }
        if (!b) {
            return;
        }
        for (final TypeHandle v2 : v-2) {
            final TypeElement v3 = v2.getElement();
            if (v3 != null && v3.getKind() != ElementKind.INTERFACE) {
                this.error("Targetted type '" + v2 + " of " + v-3 + " is not an interface", v-3);
            }
        }
    }
    
    private void validateClassMixin(final TypeElement v2, final Collection<TypeHandle> v3) {
        final TypeMirror v4 = v2.getSuperclass();
        for (final TypeHandle a2 : v3) {
            final TypeMirror a3 = a2.getType();
            if (a3 != null && !this.validateSuperClass(a3, v4)) {
                this.error("Superclass " + v4 + " of " + v2 + " was not found in the hierarchy of target class " + a3, v2);
            }
        }
    }
    
    private boolean validateSuperClass(final TypeMirror a1, final TypeMirror a2) {
        return TypeUtils.isAssignable(this.processingEnv, a1, a2) || this.validateSuperClassRecursive(a1, a2);
    }
    
    private boolean validateSuperClassRecursive(final TypeMirror a1, final TypeMirror a2) {
        if (!(a1 instanceof DeclaredType)) {
            return false;
        }
        if (TypeUtils.isAssignable(this.processingEnv, a1, a2)) {
            return true;
        }
        final TypeElement v1 = (TypeElement)((DeclaredType)a1).asElement();
        final TypeMirror v2 = v1.getSuperclass();
        return v2.getKind() != TypeKind.NONE && (this.checkMixinsFor(v2, a2) || this.validateSuperClassRecursive(v2, a2));
    }
    
    private boolean checkMixinsFor(final TypeMirror v1, final TypeMirror v2) {
        for (final TypeMirror a1 : this.getMixinsTargeting(v1)) {
            if (TypeUtils.isAssignable(this.processingEnv, a1, v2)) {
                return true;
            }
        }
        return false;
    }
}
