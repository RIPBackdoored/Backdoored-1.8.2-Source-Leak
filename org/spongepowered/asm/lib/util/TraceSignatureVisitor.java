package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.signature.*;

public final class TraceSignatureVisitor extends SignatureVisitor
{
    private final StringBuilder declaration;
    private boolean isInterface;
    private boolean seenFormalParameter;
    private boolean seenInterfaceBound;
    private boolean seenParameter;
    private boolean seenInterface;
    private StringBuilder returnType;
    private StringBuilder exceptions;
    private int argumentStack;
    private int arrayStack;
    private String separator;
    
    public TraceSignatureVisitor(final int a1) {
        super(327680);
        this.separator = "";
        this.isInterface = ((a1 & 0x200) != 0x0);
        this.declaration = new StringBuilder();
    }
    
    private TraceSignatureVisitor(final StringBuilder a1) {
        super(327680);
        this.separator = "";
        this.declaration = a1;
    }
    
    @Override
    public void visitFormalTypeParameter(final String a1) {
        this.declaration.append(this.seenFormalParameter ? ", " : "<").append(a1);
        this.seenFormalParameter = true;
        this.seenInterfaceBound = false;
    }
    
    @Override
    public SignatureVisitor visitClassBound() {
        this.separator = " extends ";
        this.startType();
        return this;
    }
    
    @Override
    public SignatureVisitor visitInterfaceBound() {
        this.separator = (this.seenInterfaceBound ? ", " : " extends ");
        this.seenInterfaceBound = true;
        this.startType();
        return this;
    }
    
    @Override
    public SignatureVisitor visitSuperclass() {
        this.endFormals();
        this.separator = " extends ";
        this.startType();
        return this;
    }
    
    @Override
    public SignatureVisitor visitInterface() {
        this.separator = (this.seenInterface ? ", " : (this.isInterface ? " extends " : " implements "));
        this.seenInterface = true;
        this.startType();
        return this;
    }
    
    @Override
    public SignatureVisitor visitParameterType() {
        this.endFormals();
        if (this.seenParameter) {
            this.declaration.append(", ");
        }
        else {
            this.seenParameter = true;
            this.declaration.append('(');
        }
        this.startType();
        return this;
    }
    
    @Override
    public SignatureVisitor visitReturnType() {
        this.endFormals();
        if (this.seenParameter) {
            this.seenParameter = false;
        }
        else {
            this.declaration.append('(');
        }
        this.declaration.append(')');
        this.returnType = new StringBuilder();
        return new TraceSignatureVisitor(this.returnType);
    }
    
    @Override
    public SignatureVisitor visitExceptionType() {
        if (this.exceptions == null) {
            this.exceptions = new StringBuilder();
        }
        else {
            this.exceptions.append(", ");
        }
        return new TraceSignatureVisitor(this.exceptions);
    }
    
    @Override
    public void visitBaseType(final char a1) {
        switch (a1) {
            case 'V': {
                this.declaration.append("void");
                break;
            }
            case 'B': {
                this.declaration.append("byte");
                break;
            }
            case 'J': {
                this.declaration.append("long");
                break;
            }
            case 'Z': {
                this.declaration.append("boolean");
                break;
            }
            case 'I': {
                this.declaration.append("int");
                break;
            }
            case 'S': {
                this.declaration.append("short");
                break;
            }
            case 'C': {
                this.declaration.append("char");
                break;
            }
            case 'F': {
                this.declaration.append("float");
                break;
            }
            default: {
                this.declaration.append("double");
                break;
            }
        }
        this.endType();
    }
    
    @Override
    public void visitTypeVariable(final String a1) {
        this.declaration.append(a1);
        this.endType();
    }
    
    @Override
    public SignatureVisitor visitArrayType() {
        this.startType();
        this.arrayStack |= 0x1;
        return this;
    }
    
    @Override
    public void visitClassType(final String v2) {
        if ("java/lang/Object".equals(v2)) {
            final boolean a1 = this.argumentStack % 2 != 0 || this.seenParameter;
            if (a1) {
                this.declaration.append(this.separator).append(v2.replace('/', '.'));
            }
        }
        else {
            this.declaration.append(this.separator).append(v2.replace('/', '.'));
        }
        this.separator = "";
        this.argumentStack *= 2;
    }
    
    @Override
    public void visitInnerClassType(final String a1) {
        if (this.argumentStack % 2 != 0) {
            this.declaration.append('>');
        }
        this.argumentStack /= 2;
        this.declaration.append('.');
        this.declaration.append(this.separator).append(a1.replace('/', '.'));
        this.separator = "";
        this.argumentStack *= 2;
    }
    
    @Override
    public void visitTypeArgument() {
        if (this.argumentStack % 2 == 0) {
            ++this.argumentStack;
            this.declaration.append('<');
        }
        else {
            this.declaration.append(", ");
        }
        this.declaration.append('?');
    }
    
    @Override
    public SignatureVisitor visitTypeArgument(final char a1) {
        if (this.argumentStack % 2 == 0) {
            ++this.argumentStack;
            this.declaration.append('<');
        }
        else {
            this.declaration.append(", ");
        }
        if (a1 == '+') {
            this.declaration.append("? extends ");
        }
        else if (a1 == '-') {
            this.declaration.append("? super ");
        }
        this.startType();
        return this;
    }
    
    @Override
    public void visitEnd() {
        if (this.argumentStack % 2 != 0) {
            this.declaration.append('>');
        }
        this.argumentStack /= 2;
        this.endType();
    }
    
    public String getDeclaration() {
        return this.declaration.toString();
    }
    
    public String getReturnType() {
        return (this.returnType == null) ? null : this.returnType.toString();
    }
    
    public String getExceptions() {
        return (this.exceptions == null) ? null : this.exceptions.toString();
    }
    
    private void endFormals() {
        if (this.seenFormalParameter) {
            this.declaration.append('>');
            this.seenFormalParameter = false;
        }
    }
    
    private void startType() {
        this.arrayStack *= 2;
    }
    
    private void endType() {
        if (this.arrayStack % 2 == 0) {
            this.arrayStack /= 2;
        }
        else {
            while (this.arrayStack % 2 != 0) {
                this.arrayStack /= 2;
                this.declaration.append("[]");
            }
        }
    }
}
