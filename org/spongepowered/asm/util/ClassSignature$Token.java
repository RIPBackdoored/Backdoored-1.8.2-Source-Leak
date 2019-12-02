package org.spongepowered.asm.util;

import java.util.*;

static class Token implements IToken
{
    static final String SYMBOLS = "+-*";
    private final boolean inner;
    private boolean array;
    private char symbol;
    private String type;
    private List<Token> classBound;
    private List<Token> ifaceBound;
    private List<IToken> signature;
    private List<IToken> suffix;
    private Token tail;
    
    Token() {
        this(false);
    }
    
    Token(final String a1) {
        this(a1, false);
    }
    
    Token(final char a1) {
        this();
        this.symbol = a1;
    }
    
    Token(final boolean a1) {
        this(null, a1);
    }
    
    Token(final String a1, final boolean a2) {
        super();
        this.symbol = '\0';
        this.inner = a2;
        this.type = a1;
    }
    
    Token setSymbol(final char a1) {
        if (this.symbol == '\0' && "+-*".indexOf(a1) > -1) {
            this.symbol = a1;
        }
        return this;
    }
    
    Token setType(final String a1) {
        if (this.type == null) {
            this.type = a1;
        }
        return this;
    }
    
    boolean hasClassBound() {
        return this.classBound != null;
    }
    
    boolean hasInterfaceBound() {
        return this.ifaceBound != null;
    }
    
    @Override
    public IToken setArray(final boolean a1) {
        this.array |= a1;
        return this;
    }
    
    @Override
    public IToken setWildcard(final char a1) {
        if ("+-".indexOf(a1) == -1) {
            return this;
        }
        return this.setSymbol(a1);
    }
    
    private List<Token> getClassBound() {
        if (this.classBound == null) {
            this.classBound = new ArrayList<Token>();
        }
        return this.classBound;
    }
    
    private List<Token> getIfaceBound() {
        if (this.ifaceBound == null) {
            this.ifaceBound = new ArrayList<Token>();
        }
        return this.ifaceBound;
    }
    
    private List<IToken> getSignature() {
        if (this.signature == null) {
            this.signature = new ArrayList<IToken>();
        }
        return this.signature;
    }
    
    private List<IToken> getSuffix() {
        if (this.suffix == null) {
            this.suffix = new ArrayList<IToken>();
        }
        return this.suffix;
    }
    
    IToken addTypeArgument(final char a1) {
        if (this.tail != null) {
            return this.tail.addTypeArgument(a1);
        }
        final Token v1 = new Token(a1);
        this.getSignature().add(v1);
        return v1;
    }
    
    IToken addTypeArgument(final String a1) {
        if (this.tail != null) {
            return this.tail.addTypeArgument(a1);
        }
        final Token v1 = new Token(a1);
        this.getSignature().add(v1);
        return v1;
    }
    
    IToken addTypeArgument(final Token a1) {
        if (this.tail != null) {
            return this.tail.addTypeArgument(a1);
        }
        this.getSignature().add(a1);
        return a1;
    }
    
    IToken addTypeArgument(final TokenHandle a1) {
        if (this.tail != null) {
            return this.tail.addTypeArgument(a1);
        }
        final TokenHandle v1 = a1.clone();
        this.getSignature().add(v1);
        return v1;
    }
    
    Token addBound(final String a1, final boolean a2) {
        if (a2) {
            return this.addClassBound(a1);
        }
        return this.addInterfaceBound(a1);
    }
    
    Token addClassBound(final String a1) {
        final Token v1 = new Token(a1);
        this.getClassBound().add(v1);
        return v1;
    }
    
    Token addInterfaceBound(final String a1) {
        final Token v1 = new Token(a1);
        this.getIfaceBound().add(v1);
        return v1;
    }
    
    Token addInnerClass(final String a1) {
        this.tail = new Token(a1, true);
        this.getSuffix().add(this.tail);
        return this.tail;
    }
    
    @Override
    public String toString() {
        return this.asType();
    }
    
    @Override
    public String asBound() {
        final StringBuilder sb = new StringBuilder();
        if (this.type != null) {
            sb.append(this.type);
        }
        if (this.classBound != null) {
            for (final Token v1 : this.classBound) {
                sb.append(v1.asType());
            }
        }
        if (this.ifaceBound != null) {
            for (final Token v1 : this.ifaceBound) {
                sb.append(':').append(v1.asType());
            }
        }
        return sb.toString();
    }
    
    @Override
    public String asType() {
        return this.asType(false);
    }
    
    public String asType(final boolean v-2) {
        final StringBuilder sb = new StringBuilder();
        if (this.array) {
            sb.append('[');
        }
        if (this.symbol != '\0') {
            sb.append(this.symbol);
        }
        if (this.type == null) {
            return sb.toString();
        }
        if (!this.inner) {
            sb.append('L');
        }
        sb.append(this.type);
        if (!v-2) {
            if (this.signature != null) {
                sb.append('<');
                for (final IToken a1 : this.signature) {
                    sb.append(a1.asType());
                }
                sb.append('>');
            }
            if (this.suffix != null) {
                for (final IToken v1 : this.suffix) {
                    sb.append('.').append(v1.asType());
                }
            }
        }
        if (!this.inner) {
            sb.append(';');
        }
        return sb.toString();
    }
    
    boolean isRaw() {
        return this.signature == null;
    }
    
    String getClassType() {
        return (this.type != null) ? this.type : "java/lang/Object";
    }
    
    @Override
    public Token asToken() {
        return this;
    }
}
