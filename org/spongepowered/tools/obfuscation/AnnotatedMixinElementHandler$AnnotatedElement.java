package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;
import javax.annotation.processing.*;
import javax.tools.*;

abstract static class AnnotatedElement<E extends java.lang.Object>
{
    protected final E element;
    protected final AnnotationHandle annotation;
    private final String desc;
    
    public AnnotatedElement(final E a1, final AnnotationHandle a2) {
        super();
        this.element = (Element)a1;
        this.annotation = a2;
        this.desc = TypeUtils.getDescriptor((Element)a1);
    }
    
    public E getElement() {
        return (E)this.element;
    }
    
    public AnnotationHandle getAnnotation() {
        return this.annotation;
    }
    
    public String getSimpleName() {
        return this.getElement().getSimpleName().toString();
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public final void printMessage(final Messager a1, final Diagnostic.Kind a2, final CharSequence a3) {
        a1.printMessage(a2, a3, this.element, this.annotation.asMirror());
    }
}
