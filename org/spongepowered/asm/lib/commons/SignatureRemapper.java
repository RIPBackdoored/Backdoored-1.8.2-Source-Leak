package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.signature.*;
import java.util.*;

public class SignatureRemapper extends SignatureVisitor
{
    private final SignatureVisitor v;
    private final Remapper remapper;
    private Stack<String> classNames;
    
    public SignatureRemapper(final SignatureVisitor a1, final Remapper a2) {
        this(327680, a1, a2);
    }
    
    protected SignatureRemapper(final int a1, final SignatureVisitor a2, final Remapper a3) {
        super(a1);
        this.classNames = new Stack<String>();
        this.v = a2;
        this.remapper = a3;
    }
    
    @Override
    public void visitClassType(final String a1) {
        this.classNames.push(a1);
        this.v.visitClassType(this.remapper.mapType(a1));
    }
    
    @Override
    public void visitInnerClassType(final String a1) {
        final String v1 = this.classNames.pop();
        final String v2 = v1 + '$' + a1;
        this.classNames.push(v2);
        final String v3 = this.remapper.mapType(v1) + '$';
        final String v4 = this.remapper.mapType(v2);
        final int v5 = v4.startsWith(v3) ? v3.length() : (v4.lastIndexOf(36) + 1);
        this.v.visitInnerClassType(v4.substring(v5));
    }
    
    @Override
    public void visitFormalTypeParameter(final String a1) {
        this.v.visitFormalTypeParameter(a1);
    }
    
    @Override
    public void visitTypeVariable(final String a1) {
        this.v.visitTypeVariable(a1);
    }
    
    @Override
    public SignatureVisitor visitArrayType() {
        this.v.visitArrayType();
        return this;
    }
    
    @Override
    public void visitBaseType(final char a1) {
        this.v.visitBaseType(a1);
    }
    
    @Override
    public SignatureVisitor visitClassBound() {
        this.v.visitClassBound();
        return this;
    }
    
    @Override
    public SignatureVisitor visitExceptionType() {
        this.v.visitExceptionType();
        return this;
    }
    
    @Override
    public SignatureVisitor visitInterface() {
        this.v.visitInterface();
        return this;
    }
    
    @Override
    public SignatureVisitor visitInterfaceBound() {
        this.v.visitInterfaceBound();
        return this;
    }
    
    @Override
    public SignatureVisitor visitParameterType() {
        this.v.visitParameterType();
        return this;
    }
    
    @Override
    public SignatureVisitor visitReturnType() {
        this.v.visitReturnType();
        return this;
    }
    
    @Override
    public SignatureVisitor visitSuperclass() {
        this.v.visitSuperclass();
        return this;
    }
    
    @Override
    public void visitTypeArgument() {
        this.v.visitTypeArgument();
    }
    
    @Override
    public SignatureVisitor visitTypeArgument(final char a1) {
        this.v.visitTypeArgument(a1);
        return this;
    }
    
    @Override
    public void visitEnd() {
        this.v.visitEnd();
        this.classNames.pop();
    }
}
