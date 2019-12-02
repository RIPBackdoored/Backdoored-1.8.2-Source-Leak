package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.*;

class SignatureParser extends SignatureVisitor
{
    private FormalParamElement param;
    final /* synthetic */ ClassSignature this$0;
    
    SignatureParser(final ClassSignature a1) {
        this.this$0 = a1;
        super(327680);
    }
    
    @Override
    public void visitFormalTypeParameter(final String a1) {
        this.param = new FormalParamElement(a1);
    }
    
    @Override
    public SignatureVisitor visitClassBound() {
        return this.param.visitClassBound();
    }
    
    @Override
    public SignatureVisitor visitInterfaceBound() {
        return this.param.visitInterfaceBound();
    }
    
    @Override
    public SignatureVisitor visitSuperclass() {
        return new SuperClassElement();
    }
    
    @Override
    public SignatureVisitor visitInterface() {
        return new InterfaceElement();
    }
    
    abstract class SignatureElement extends SignatureVisitor
    {
        final /* synthetic */ SignatureParser this$1;
        
        public SignatureElement(final SignatureParser a1) {
            this.this$1 = a1;
            super(327680);
        }
    }
    
    abstract class TokenElement extends SignatureElement
    {
        protected Token token;
        private boolean array;
        final /* synthetic */ SignatureParser this$1;
        
        TokenElement(final SignatureParser a1) {
            this.this$1 = a1;
            a1.super();
        }
        
        public Token getToken() {
            if (this.token == null) {
                this.token = new Token();
            }
            return this.token;
        }
        
        protected void setArray() {
            this.array = true;
        }
        
        private boolean getArray() {
            final boolean v1 = this.array;
            this.array = false;
            return v1;
        }
        
        @Override
        public void visitClassType(final String a1) {
            this.getToken().setType(a1);
        }
        
        @Override
        public SignatureVisitor visitClassBound() {
            this.getToken();
            return this.this$1.new BoundElement(this, true);
        }
        
        @Override
        public SignatureVisitor visitInterfaceBound() {
            this.getToken();
            return this.this$1.new BoundElement(this, false);
        }
        
        @Override
        public void visitInnerClassType(final String a1) {
            this.token.addInnerClass(a1);
        }
        
        @Override
        public SignatureVisitor visitArrayType() {
            this.setArray();
            return this;
        }
        
        @Override
        public SignatureVisitor visitTypeArgument(final char a1) {
            return this.this$1.new TypeArgElement(this, a1);
        }
        
        Token addTypeArgument() {
            return this.token.addTypeArgument('*').asToken();
        }
        
        IToken addTypeArgument(final char a1) {
            return this.token.addTypeArgument(a1).setArray(this.getArray());
        }
        
        IToken addTypeArgument(final String a1) {
            return this.token.addTypeArgument(a1).setArray(this.getArray());
        }
        
        IToken addTypeArgument(final Token a1) {
            return this.token.addTypeArgument(a1).setArray(this.getArray());
        }
        
        IToken addTypeArgument(final TokenHandle a1) {
            return this.token.addTypeArgument(a1).setArray(this.getArray());
        }
    }
    
    class FormalParamElement extends TokenElement
    {
        private final TokenHandle handle;
        final /* synthetic */ SignatureParser this$1;
        
        FormalParamElement(final SignatureParser a1, final String a2) {
            this.this$1 = a1;
            a1.super();
            this.handle = a1.this$0.getType(a2);
            this.token = this.handle.asToken();
        }
    }
    
    class TypeArgElement extends TokenElement
    {
        private final TokenElement type;
        private final char wildcard;
        final /* synthetic */ SignatureParser this$1;
        
        TypeArgElement(final SignatureParser a1, final TokenElement a2, final char a3) {
            this.this$1 = a1;
            a1.super();
            this.type = a2;
            this.wildcard = a3;
        }
        
        @Override
        public SignatureVisitor visitArrayType() {
            this.type.setArray();
            return this;
        }
        
        @Override
        public void visitBaseType(final char a1) {
            this.token = this.type.addTypeArgument(a1).asToken();
        }
        
        @Override
        public void visitTypeVariable(final String a1) {
            final TokenHandle v1 = this.this$1.this$0.getType(a1);
            this.token = this.type.addTypeArgument(v1).setWildcard(this.wildcard).asToken();
        }
        
        @Override
        public void visitClassType(final String a1) {
            this.token = this.type.addTypeArgument(a1).setWildcard(this.wildcard).asToken();
        }
        
        @Override
        public void visitTypeArgument() {
            this.token.addTypeArgument('*');
        }
        
        @Override
        public SignatureVisitor visitTypeArgument(final char a1) {
            return this.this$1.new TypeArgElement(this, a1);
        }
        
        @Override
        public void visitEnd() {
        }
    }
    
    class BoundElement extends TokenElement
    {
        private final TokenElement type;
        private final boolean classBound;
        final /* synthetic */ SignatureParser this$1;
        
        BoundElement(final SignatureParser a1, final TokenElement a2, final boolean a3) {
            this.this$1 = a1;
            a1.super();
            this.type = a2;
            this.classBound = a3;
        }
        
        @Override
        public void visitClassType(final String a1) {
            this.token = this.type.token.addBound(a1, this.classBound);
        }
        
        @Override
        public void visitTypeArgument() {
            this.token.addTypeArgument('*');
        }
        
        @Override
        public SignatureVisitor visitTypeArgument(final char a1) {
            return this.this$1.new TypeArgElement(this, a1);
        }
    }
    
    class SuperClassElement extends TokenElement
    {
        final /* synthetic */ SignatureParser this$1;
        
        SuperClassElement(final SignatureParser a1) {
            this.this$1 = a1;
            a1.super();
        }
        
        @Override
        public void visitEnd() {
            this.this$1.this$0.setSuperClass(this.token);
        }
    }
    
    class InterfaceElement extends TokenElement
    {
        final /* synthetic */ SignatureParser this$1;
        
        InterfaceElement(final SignatureParser a1) {
            this.this$1 = a1;
            a1.super();
        }
        
        @Override
        public void visitEnd() {
            this.this$1.this$0.addInterface(this.token);
        }
    }
}
