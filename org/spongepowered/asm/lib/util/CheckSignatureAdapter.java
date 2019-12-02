package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.signature.*;

public class CheckSignatureAdapter extends SignatureVisitor
{
    public static final int CLASS_SIGNATURE = 0;
    public static final int METHOD_SIGNATURE = 1;
    public static final int TYPE_SIGNATURE = 2;
    private static final int EMPTY = 1;
    private static final int FORMAL = 2;
    private static final int BOUND = 4;
    private static final int SUPER = 8;
    private static final int PARAM = 16;
    private static final int RETURN = 32;
    private static final int SIMPLE_TYPE = 64;
    private static final int CLASS_TYPE = 128;
    private static final int END = 256;
    private final int type;
    private int state;
    private boolean canBeVoid;
    private final SignatureVisitor sv;
    
    public CheckSignatureAdapter(final int a1, final SignatureVisitor a2) {
        this(327680, a1, a2);
    }
    
    protected CheckSignatureAdapter(final int a1, final int a2, final SignatureVisitor a3) {
        super(a1);
        this.type = a2;
        this.state = 1;
        this.sv = a3;
    }
    
    @Override
    public void visitFormalTypeParameter(final String a1) {
        if (this.type == 2 || (this.state != 1 && this.state != 2 && this.state != 4)) {
            throw new IllegalStateException();
        }
        CheckMethodAdapter.checkIdentifier(a1, "formal type parameter");
        this.state = 2;
        if (this.sv != null) {
            this.sv.visitFormalTypeParameter(a1);
        }
    }
    
    @Override
    public SignatureVisitor visitClassBound() {
        if (this.state != 2) {
            throw new IllegalStateException();
        }
        this.state = 4;
        final SignatureVisitor v1 = (this.sv == null) ? null : this.sv.visitClassBound();
        return new CheckSignatureAdapter(2, v1);
    }
    
    @Override
    public SignatureVisitor visitInterfaceBound() {
        if (this.state != 2 && this.state != 4) {
            throw new IllegalArgumentException();
        }
        final SignatureVisitor v1 = (this.sv == null) ? null : this.sv.visitInterfaceBound();
        return new CheckSignatureAdapter(2, v1);
    }
    
    @Override
    public SignatureVisitor visitSuperclass() {
        if (this.type != 0 || (this.state & 0x7) == 0x0) {
            throw new IllegalArgumentException();
        }
        this.state = 8;
        final SignatureVisitor v1 = (this.sv == null) ? null : this.sv.visitSuperclass();
        return new CheckSignatureAdapter(2, v1);
    }
    
    @Override
    public SignatureVisitor visitInterface() {
        if (this.state != 8) {
            throw new IllegalStateException();
        }
        final SignatureVisitor v1 = (this.sv == null) ? null : this.sv.visitInterface();
        return new CheckSignatureAdapter(2, v1);
    }
    
    @Override
    public SignatureVisitor visitParameterType() {
        if (this.type != 1 || (this.state & 0x17) == 0x0) {
            throw new IllegalArgumentException();
        }
        this.state = 16;
        final SignatureVisitor v1 = (this.sv == null) ? null : this.sv.visitParameterType();
        return new CheckSignatureAdapter(2, v1);
    }
    
    @Override
    public SignatureVisitor visitReturnType() {
        if (this.type != 1 || (this.state & 0x17) == 0x0) {
            throw new IllegalArgumentException();
        }
        this.state = 32;
        final SignatureVisitor v1 = (this.sv == null) ? null : this.sv.visitReturnType();
        final CheckSignatureAdapter v2 = new CheckSignatureAdapter(2, v1);
        v2.canBeVoid = true;
        return v2;
    }
    
    @Override
    public SignatureVisitor visitExceptionType() {
        if (this.state != 32) {
            throw new IllegalStateException();
        }
        final SignatureVisitor v1 = (this.sv == null) ? null : this.sv.visitExceptionType();
        return new CheckSignatureAdapter(2, v1);
    }
    
    @Override
    public void visitBaseType(final char a1) {
        if (this.type != 2 || this.state != 1) {
            throw new IllegalStateException();
        }
        if (a1 == 'V') {
            if (!this.canBeVoid) {
                throw new IllegalArgumentException();
            }
        }
        else if ("ZCBSIFJD".indexOf(a1) == -1) {
            throw new IllegalArgumentException();
        }
        this.state = 64;
        if (this.sv != null) {
            this.sv.visitBaseType(a1);
        }
    }
    
    @Override
    public void visitTypeVariable(final String a1) {
        if (this.type != 2 || this.state != 1) {
            throw new IllegalStateException();
        }
        CheckMethodAdapter.checkIdentifier(a1, "type variable");
        this.state = 64;
        if (this.sv != null) {
            this.sv.visitTypeVariable(a1);
        }
    }
    
    @Override
    public SignatureVisitor visitArrayType() {
        if (this.type != 2 || this.state != 1) {
            throw new IllegalStateException();
        }
        this.state = 64;
        final SignatureVisitor v1 = (this.sv == null) ? null : this.sv.visitArrayType();
        return new CheckSignatureAdapter(2, v1);
    }
    
    @Override
    public void visitClassType(final String a1) {
        if (this.type != 2 || this.state != 1) {
            throw new IllegalStateException();
        }
        CheckMethodAdapter.checkInternalName(a1, "class name");
        this.state = 128;
        if (this.sv != null) {
            this.sv.visitClassType(a1);
        }
    }
    
    @Override
    public void visitInnerClassType(final String a1) {
        if (this.state != 128) {
            throw new IllegalStateException();
        }
        CheckMethodAdapter.checkIdentifier(a1, "inner class name");
        if (this.sv != null) {
            this.sv.visitInnerClassType(a1);
        }
    }
    
    @Override
    public void visitTypeArgument() {
        if (this.state != 128) {
            throw new IllegalStateException();
        }
        if (this.sv != null) {
            this.sv.visitTypeArgument();
        }
    }
    
    @Override
    public SignatureVisitor visitTypeArgument(final char a1) {
        if (this.state != 128) {
            throw new IllegalStateException();
        }
        if ("+-=".indexOf(a1) == -1) {
            throw new IllegalArgumentException();
        }
        final SignatureVisitor v1 = (this.sv == null) ? null : this.sv.visitTypeArgument(a1);
        return new CheckSignatureAdapter(2, v1);
    }
    
    @Override
    public void visitEnd() {
        if (this.state != 128) {
            throw new IllegalStateException();
        }
        this.state = 256;
        if (this.sv != null) {
            this.sv.visitEnd();
        }
    }
}
