package org.spongepowered.asm.lib.signature;

public abstract class SignatureVisitor
{
    public static final char EXTENDS = '+';
    public static final char SUPER = '-';
    public static final char INSTANCEOF = '=';
    protected final int api;
    
    public SignatureVisitor(final int a1) {
        super();
        if (a1 != 262144 && a1 != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = a1;
    }
    
    public void visitFormalTypeParameter(final String a1) {
    }
    
    public SignatureVisitor visitClassBound() {
        return this;
    }
    
    public SignatureVisitor visitInterfaceBound() {
        return this;
    }
    
    public SignatureVisitor visitSuperclass() {
        return this;
    }
    
    public SignatureVisitor visitInterface() {
        return this;
    }
    
    public SignatureVisitor visitParameterType() {
        return this;
    }
    
    public SignatureVisitor visitReturnType() {
        return this;
    }
    
    public SignatureVisitor visitExceptionType() {
        return this;
    }
    
    public void visitBaseType(final char a1) {
    }
    
    public void visitTypeVariable(final String a1) {
    }
    
    public SignatureVisitor visitArrayType() {
        return this;
    }
    
    public void visitClassType(final String a1) {
    }
    
    public void visitInnerClassType(final String a1) {
    }
    
    public void visitTypeArgument() {
    }
    
    public SignatureVisitor visitTypeArgument(final char a1) {
        return this;
    }
    
    public void visitEnd() {
    }
}
