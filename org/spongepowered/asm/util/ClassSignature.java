package org.spongepowered.asm.util;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.signature.*;

public class ClassSignature
{
    protected static final String OBJECT = "java/lang/Object";
    private final Map<TypeVar, TokenHandle> types;
    private Token superClass;
    private final List<Token> interfaces;
    private final Deque<String> rawInterfaces;
    
    ClassSignature() {
        super();
        this.types = new LinkedHashMap<TypeVar, TokenHandle>();
        this.superClass = new Token("java/lang/Object");
        this.interfaces = new ArrayList<Token>();
        this.rawInterfaces = new LinkedList<String>();
    }
    
    private ClassSignature read(final String v2) {
        if (v2 != null) {
            try {
                new SignatureReader(v2).accept(new SignatureParser());
            }
            catch (Exception a1) {
                a1.printStackTrace();
            }
        }
        return this;
    }
    
    protected TypeVar getTypeVar(final String v2) {
        for (final TypeVar a1 : this.types.keySet()) {
            if (a1.matches(v2)) {
                return a1;
            }
        }
        return null;
    }
    
    protected TokenHandle getType(final String v2) {
        for (final TypeVar a1 : this.types.keySet()) {
            if (a1.matches(v2)) {
                return this.types.get(a1);
            }
        }
        final TokenHandle v3 = new TokenHandle();
        this.types.put(new TypeVar(v2), v3);
        return v3;
    }
    
    protected String getTypeVar(final TokenHandle v-3) {
        for (final Map.Entry<TypeVar, TokenHandle> entry : this.types.entrySet()) {
            final TypeVar a1 = entry.getKey();
            final TokenHandle v1 = entry.getValue();
            if (v-3 == v1 || v-3.asToken() == v1.asToken()) {
                return "T" + a1 + ";";
            }
        }
        return v-3.token.asType();
    }
    
    protected void addTypeVar(final TypeVar a1, final TokenHandle a2) throws IllegalArgumentException {
        if (this.types.containsKey(a1)) {
            throw new IllegalArgumentException("TypeVar " + a1 + " is already present on " + this);
        }
        this.types.put(a1, a2);
    }
    
    protected void setSuperClass(final Token a1) {
        this.superClass = a1;
    }
    
    public String getSuperClass() {
        return this.superClass.asType(true);
    }
    
    protected void addInterface(final Token v-1) {
        if (!v-1.isRaw()) {
            final String v0 = v-1.asType(true);
            final ListIterator<Token> v2 = this.interfaces.listIterator();
            while (v2.hasNext()) {
                final Token a1 = v2.next();
                if (a1.isRaw() && a1.asType(true).equals(v0)) {
                    v2.set(v-1);
                    return;
                }
            }
        }
        this.interfaces.add(v-1);
    }
    
    public void addInterface(final String a1) {
        this.rawInterfaces.add(a1);
    }
    
    protected void addRawInterface(final String v2) {
        final Token v3 = new Token(v2);
        final String v4 = v3.asType(true);
        for (final Token a1 : this.interfaces) {
            if (a1.asType(true).equals(v4)) {
                return;
            }
        }
        this.interfaces.add(v3);
    }
    
    public void merge(final ClassSignature v0) {
        try {
            final Set<String> v = new HashSet<String>();
            for (final TypeVar a1 : this.types.keySet()) {
                v.add(a1.toString());
            }
            v0.conform(v);
        }
        catch (IllegalStateException v2) {
            v2.printStackTrace();
            return;
        }
        for (final Map.Entry<TypeVar, TokenHandle> v3 : v0.types.entrySet()) {
            this.addTypeVar(v3.getKey(), v3.getValue());
        }
        for (final Token v4 : v0.interfaces) {
            this.addInterface(v4);
        }
    }
    
    private void conform(final Set<String> v-1) {
        for (final TypeVar v1 : this.types.keySet()) {
            final String a1 = this.findUniqueName(v1.getOriginalName(), v-1);
            v1.rename(a1);
            v-1.add(a1);
        }
    }
    
    private String findUniqueName(final String v1, final Set<String> v2) {
        if (!v2.contains(v1)) {
            return v1;
        }
        if (v1.length() == 1) {
            final String a1 = this.findOffsetName(v1.charAt(0), v2);
            if (a1 != null) {
                return a1;
            }
        }
        String v3 = this.findOffsetName('T', v2, "", v1);
        if (v3 != null) {
            return v3;
        }
        v3 = this.findOffsetName('T', v2, v1, "");
        if (v3 != null) {
            return v3;
        }
        v3 = this.findOffsetName('T', v2, "T", v1);
        if (v3 != null) {
            return v3;
        }
        v3 = this.findOffsetName('T', v2, "", v1 + "Type");
        if (v3 != null) {
            return v3;
        }
        throw new IllegalStateException("Failed to conform type var: " + v1);
    }
    
    private String findOffsetName(final char a1, final Set<String> a2) {
        return this.findOffsetName(a1, a2, "", "");
    }
    
    private String findOffsetName(final char a3, final Set<String> a4, final String v1, final String v2) {
        String v3 = String.format("%s%s%s", v1, a3, v2);
        if (!a4.contains(v3)) {
            return v3;
        }
        if (a3 > '@' && a3 < '[') {
            for (int a5 = a3 - '@'; a5 + 65 != a3; a5 = ++a5 % 26) {
                v3 = String.format("%s%s%s", v1, (char)(a5 + 65), v2);
                if (!a4.contains(v3)) {
                    return v3;
                }
            }
        }
        return null;
    }
    
    public SignatureVisitor getRemapper() {
        return new SignatureRemapper();
    }
    
    @Override
    public String toString() {
        while (this.rawInterfaces.size() > 0) {
            this.addRawInterface(this.rawInterfaces.remove());
        }
        final StringBuilder sb = new StringBuilder();
        if (this.types.size() > 0) {
            boolean b = false;
            final StringBuilder sb2 = new StringBuilder();
            for (final Map.Entry<TypeVar, TokenHandle> v0 : this.types.entrySet()) {
                final String v2 = v0.getValue().asBound();
                if (!v2.isEmpty()) {
                    sb2.append(v0.getKey()).append(':').append(v2);
                    b = true;
                }
            }
            if (b) {
                sb.append('<').append((CharSequence)sb2).append('>');
            }
        }
        sb.append(this.superClass.asType());
        for (final Token token : this.interfaces) {
            sb.append(token.asType());
        }
        return sb.toString();
    }
    
    public ClassSignature wake() {
        return this;
    }
    
    public static ClassSignature of(final String a1) {
        return new ClassSignature().read(a1);
    }
    
    public static ClassSignature of(final ClassNode a1) {
        if (a1.signature != null) {
            return of(a1.signature);
        }
        return generate(a1);
    }
    
    public static ClassSignature ofLazy(final ClassNode a1) {
        if (a1.signature != null) {
            return new Lazy(a1.signature);
        }
        return generate(a1);
    }
    
    private static ClassSignature generate(final ClassNode v1) {
        final ClassSignature v2 = new ClassSignature();
        v2.setSuperClass(new Token((v1.superName != null) ? v1.superName : "java/lang/Object"));
        for (final String a1 : v1.interfaces) {
            v2.addInterface(new Token(a1));
        }
        return v2;
    }
    
    static class Lazy extends ClassSignature
    {
        private final String sig;
        private ClassSignature generated;
        
        Lazy(final String a1) {
            super();
            this.sig = a1;
        }
        
        @Override
        public ClassSignature wake() {
            if (this.generated == null) {
                this.generated = ClassSignature.of(this.sig);
            }
            return this.generated;
        }
    }
    
    static class TypeVar implements Comparable<TypeVar>
    {
        private final String originalName;
        private String currentName;
        
        TypeVar(final String a1) {
            super();
            this.originalName = a1;
            this.currentName = a1;
        }
        
        @Override
        public int compareTo(final TypeVar a1) {
            return this.currentName.compareTo(a1.currentName);
        }
        
        @Override
        public String toString() {
            return this.currentName;
        }
        
        String getOriginalName() {
            return this.originalName;
        }
        
        void rename(final String a1) {
            this.currentName = a1;
        }
        
        public boolean matches(final String a1) {
            return this.originalName.equals(a1);
        }
        
        @Override
        public boolean equals(final Object a1) {
            return this.currentName.equals(a1);
        }
        
        @Override
        public int hashCode() {
            return this.currentName.hashCode();
        }
        
        @Override
        public /* bridge */ int compareTo(final Object o) {
            return this.compareTo((TypeVar)o);
        }
    }
    
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
    
    class TokenHandle implements IToken
    {
        final Token token;
        boolean array;
        char wildcard;
        final /* synthetic */ ClassSignature this$0;
        
        TokenHandle(final ClassSignature a1) {
            this(a1, new Token());
        }
        
        TokenHandle(final ClassSignature a1, final Token a2) {
            this.this$0 = a1;
            super();
            this.token = a2;
        }
        
        @Override
        public IToken setArray(final boolean a1) {
            this.array |= a1;
            return this;
        }
        
        @Override
        public IToken setWildcard(final char a1) {
            if ("+-".indexOf(a1) > -1) {
                this.wildcard = a1;
            }
            return this;
        }
        
        @Override
        public String asBound() {
            return this.token.asBound();
        }
        
        @Override
        public String asType() {
            final StringBuilder v1 = new StringBuilder();
            if (this.wildcard > '\0') {
                v1.append(this.wildcard);
            }
            if (this.array) {
                v1.append('[');
            }
            return v1.append(this.this$0.getTypeVar(this)).toString();
        }
        
        @Override
        public Token asToken() {
            return this.token;
        }
        
        @Override
        public String toString() {
            return this.token.toString();
        }
        
        public TokenHandle clone() {
            return this.this$0.new TokenHandle(this.token);
        }
        
        public /* bridge */ Object clone() throws CloneNotSupportedException {
            return this.clone();
        }
    }
    
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
    
    class SignatureRemapper extends SignatureWriter
    {
        private final Set<String> localTypeVars;
        final /* synthetic */ ClassSignature this$0;
        
        SignatureRemapper(final ClassSignature a1) {
            this.this$0 = a1;
            super();
            this.localTypeVars = new HashSet<String>();
        }
        
        @Override
        public void visitFormalTypeParameter(final String a1) {
            this.localTypeVars.add(a1);
            super.visitFormalTypeParameter(a1);
        }
        
        @Override
        public void visitTypeVariable(final String v2) {
            if (!this.localTypeVars.contains(v2)) {
                final TypeVar a1 = this.this$0.getTypeVar(v2);
                if (a1 != null) {
                    super.visitTypeVariable(a1.toString());
                    return;
                }
            }
            super.visitTypeVariable(v2);
        }
    }
    
    interface IToken
    {
        public static final String WILDCARDS = "+-";
        
        String asType();
        
        String asBound();
        
        Token asToken();
        
        IToken setArray(final boolean p0);
        
        IToken setWildcard(final char p0);
    }
}
