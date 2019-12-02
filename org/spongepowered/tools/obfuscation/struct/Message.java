package org.spongepowered.tools.obfuscation.struct;

import javax.tools.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.annotation.processing.*;

public class Message
{
    private Diagnostic.Kind kind;
    private CharSequence msg;
    private final Element element;
    private final AnnotationMirror annotation;
    private final AnnotationValue value;
    
    public Message(final Diagnostic.Kind a1, final CharSequence a2) {
        this(a1, a2, null, (AnnotationMirror)null, null);
    }
    
    public Message(final Diagnostic.Kind a1, final CharSequence a2, final Element a3) {
        this(a1, a2, a3, (AnnotationMirror)null, null);
    }
    
    public Message(final Diagnostic.Kind a1, final CharSequence a2, final Element a3, final AnnotationHandle a4) {
        this(a1, a2, a3, a4.asMirror(), null);
    }
    
    public Message(final Diagnostic.Kind a1, final CharSequence a2, final Element a3, final AnnotationMirror a4) {
        this(a1, a2, a3, a4, null);
    }
    
    public Message(final Diagnostic.Kind a1, final CharSequence a2, final Element a3, final AnnotationHandle a4, final AnnotationValue a5) {
        this(a1, a2, a3, a4.asMirror(), a5);
    }
    
    public Message(final Diagnostic.Kind a1, final CharSequence a2, final Element a3, final AnnotationMirror a4, final AnnotationValue a5) {
        super();
        this.kind = a1;
        this.msg = a2;
        this.element = a3;
        this.annotation = a4;
        this.value = a5;
    }
    
    public Message sendTo(final Messager a1) {
        if (this.value != null) {
            a1.printMessage(this.kind, this.msg, this.element, this.annotation, this.value);
        }
        else if (this.annotation != null) {
            a1.printMessage(this.kind, this.msg, this.element, this.annotation);
        }
        else if (this.element != null) {
            a1.printMessage(this.kind, this.msg, this.element);
        }
        else {
            a1.printMessage(this.kind, this.msg);
        }
        return this;
    }
    
    public Diagnostic.Kind getKind() {
        return this.kind;
    }
    
    public Message setKind(final Diagnostic.Kind a1) {
        this.kind = a1;
        return this;
    }
    
    public CharSequence getMsg() {
        return this.msg;
    }
    
    public Message setMsg(final CharSequence a1) {
        this.msg = a1;
        return this;
    }
    
    public Element getElement() {
        return this.element;
    }
    
    public AnnotationMirror getAnnotation() {
        return this.annotation;
    }
    
    public AnnotationValue getValue() {
        return this.value;
    }
}
