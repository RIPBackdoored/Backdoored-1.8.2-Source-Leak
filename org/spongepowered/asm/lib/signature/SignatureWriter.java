package org.spongepowered.asm.lib.signature;

public class SignatureWriter extends SignatureVisitor
{
    private final StringBuilder buf;
    private boolean hasFormals;
    private boolean hasParameters;
    private int argumentStack;
    
    public SignatureWriter() {
        super(327680);
        this.buf = new StringBuilder();
    }
    
    @Override
    public void visitFormalTypeParameter(final String a1) {
        if (!this.hasFormals) {
            this.hasFormals = true;
            this.buf.append('<');
        }
        this.buf.append(a1);
        this.buf.append(':');
    }
    
    @Override
    public SignatureVisitor visitClassBound() {
        return this;
    }
    
    @Override
    public SignatureVisitor visitInterfaceBound() {
        this.buf.append(':');
        return this;
    }
    
    @Override
    public SignatureVisitor visitSuperclass() {
        this.endFormals();
        return this;
    }
    
    @Override
    public SignatureVisitor visitInterface() {
        return this;
    }
    
    @Override
    public SignatureVisitor visitParameterType() {
        this.endFormals();
        if (!this.hasParameters) {
            this.hasParameters = true;
            this.buf.append('(');
        }
        return this;
    }
    
    @Override
    public SignatureVisitor visitReturnType() {
        this.endFormals();
        if (!this.hasParameters) {
            this.buf.append('(');
        }
        this.buf.append(')');
        return this;
    }
    
    @Override
    public SignatureVisitor visitExceptionType() {
        this.buf.append('^');
        return this;
    }
    
    @Override
    public void visitBaseType(final char a1) {
        this.buf.append(a1);
    }
    
    @Override
    public void visitTypeVariable(final String a1) {
        this.buf.append('T');
        this.buf.append(a1);
        this.buf.append(';');
    }
    
    @Override
    public SignatureVisitor visitArrayType() {
        this.buf.append('[');
        return this;
    }
    
    @Override
    public void visitClassType(final String a1) {
        this.buf.append('L');
        this.buf.append(a1);
        this.argumentStack *= 2;
    }
    
    @Override
    public void visitInnerClassType(final String a1) {
        this.endArguments();
        this.buf.append('.');
        this.buf.append(a1);
        this.argumentStack *= 2;
    }
    
    @Override
    public void visitTypeArgument() {
        if (this.argumentStack % 2 == 0) {
            ++this.argumentStack;
            this.buf.append('<');
        }
        this.buf.append('*');
    }
    
    @Override
    public SignatureVisitor visitTypeArgument(final char a1) {
        if (this.argumentStack % 2 == 0) {
            ++this.argumentStack;
            this.buf.append('<');
        }
        if (a1 != '=') {
            this.buf.append(a1);
        }
        return this;
    }
    
    @Override
    public void visitEnd() {
        this.endArguments();
        this.buf.append(';');
    }
    
    @Override
    public String toString() {
        return this.buf.toString();
    }
    
    private void endFormals() {
        if (this.hasFormals) {
            this.hasFormals = false;
            this.buf.append('>');
        }
    }
    
    private void endArguments() {
        if (this.argumentStack % 2 != 0) {
            this.buf.append('>');
        }
        this.argumentStack /= 2;
    }
}
