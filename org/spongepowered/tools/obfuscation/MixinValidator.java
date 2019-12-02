package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import java.util.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;
import javax.tools.*;
import javax.lang.model.type.*;

public abstract class MixinValidator implements IMixinValidator
{
    protected final ProcessingEnvironment processingEnv;
    protected final Messager messager;
    protected final IOptionProvider options;
    protected final ValidationPass pass;
    
    public MixinValidator(final IMixinAnnotationProcessor a1, final ValidationPass a2) {
        super();
        this.processingEnv = a1.getProcessingEnvironment();
        this.messager = a1;
        this.options = a1;
        this.pass = a2;
    }
    
    @Override
    public final boolean validate(final ValidationPass a1, final TypeElement a2, final AnnotationHandle a3, final Collection<TypeHandle> a4) {
        return a1 != this.pass || this.validate(a2, a3, a4);
    }
    
    protected abstract boolean validate(final TypeElement p0, final AnnotationHandle p1, final Collection<TypeHandle> p2);
    
    protected final void note(final String a1, final Element a2) {
        this.messager.printMessage(Diagnostic.Kind.NOTE, a1, a2);
    }
    
    protected final void warning(final String a1, final Element a2) {
        this.messager.printMessage(Diagnostic.Kind.WARNING, a1, a2);
    }
    
    protected final void error(final String a1, final Element a2) {
        this.messager.printMessage(Diagnostic.Kind.ERROR, a1, a2);
    }
    
    protected final Collection<TypeMirror> getMixinsTargeting(final TypeMirror a1) {
        return AnnotatedMixins.getMixinsForEnvironment(this.processingEnv).getMixinsTargeting(a1);
    }
}
