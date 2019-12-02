package javassist.compiler;

import javassist.*;
import javassist.bytecode.*;
import javassist.compiler.ast.*;

public class Javac
{
    JvstCodeGen gen;
    SymbolTable stable;
    private Bytecode bytecode;
    public static final String param0Name = "$0";
    public static final String resultVarName = "$_";
    public static final String proceedName = "$proceed";
    
    public Javac(final CtClass a1) {
        this(new Bytecode(a1.getClassFile2().getConstPool(), 0, 0), a1);
    }
    
    public Javac(final Bytecode a1, final CtClass a2) {
        super();
        this.gen = new JvstCodeGen(a1, a2, a2.getClassPool());
        this.stable = new SymbolTable();
        this.bytecode = a1;
    }
    
    public Bytecode getBytecode() {
        return this.bytecode;
    }
    
    public CtMember compile(final String v-3) throws CompileError {
        final Parser v-4 = new Parser(new Lex(v-3));
        final ASTList member1 = v-4.parseMember1(this.stable);
        try {
            if (member1 instanceof FieldDecl) {
                return this.compileField((FieldDecl)member1);
            }
            final CtBehavior a1 = this.compileMethod(v-4, (MethodDecl)member1);
            final CtClass v1 = a1.getDeclaringClass();
            a1.getMethodInfo2().rebuildStackMapIf6(v1.getClassPool(), v1.getClassFile2());
            return a1;
        }
        catch (BadBytecode v2) {
            throw new CompileError(v2.getMessage());
        }
        catch (CannotCompileException v3) {
            throw new CompileError(v3.getMessage());
        }
    }
    
    private CtField compileField(final FieldDecl a1) throws CompileError, CannotCompileException {
        final Declarator v2 = a1.getDeclarator();
        final CtFieldWithInit v3 = new CtFieldWithInit(this.gen.resolver.lookupClass(v2), v2.getVariable().get(), this.gen.getThisClass());
        v3.setModifiers(MemberResolver.getModifiers(a1.getModifiers()));
        if (a1.getInit() != null) {
            v3.setInit(a1.getInit());
        }
        return v3;
    }
    
    private CtBehavior compileMethod(final Parser v-5, MethodDecl v-4) throws CompileError {
        final int modifiers = MemberResolver.getModifiers(v-4.getModifiers());
        final CtClass[] paramList = this.gen.makeParamList(v-4);
        final CtClass[] throwsList = this.gen.makeThrowsList(v-4);
        this.recordParams(paramList, Modifier.isStatic(modifiers));
        v-4 = v-5.parseMethod2(this.stable, v-4);
        try {
            if (v-4.isConstructor()) {
                final CtConstructor a1 = new CtConstructor(paramList, this.gen.getThisClass());
                a1.setModifiers(modifiers);
                v-4.accept(this.gen);
                a1.getMethodInfo().setCodeAttribute(this.bytecode.toCodeAttribute());
                a1.setExceptionTypes(throwsList);
                return a1;
            }
            final Declarator a2 = v-4.getReturn();
            final CtClass v1 = this.gen.resolver.lookupClass(a2);
            this.recordReturnType(v1, false);
            final CtMethod v2 = new CtMethod(v1, a2.getVariable().get(), paramList, this.gen.getThisClass());
            v2.setModifiers(modifiers);
            this.gen.setThisMethod(v2);
            v-4.accept(this.gen);
            if (v-4.getBody() != null) {
                v2.getMethodInfo().setCodeAttribute(this.bytecode.toCodeAttribute());
            }
            else {
                v2.setModifiers(modifiers | 0x400);
            }
            v2.setExceptionTypes(throwsList);
            return v2;
        }
        catch (NotFoundException v3) {
            throw new CompileError(v3.toString());
        }
    }
    
    public Bytecode compileBody(final CtBehavior v-5, final String v-4) throws CompileError {
        try {
            final int modifiers = v-5.getModifiers();
            this.recordParams(v-5.getParameterTypes(), Modifier.isStatic(modifiers));
            CtClass voidType = null;
            if (v-5 instanceof CtMethod) {
                this.gen.setThisMethod((CtMethod)v-5);
                final CtClass a1 = ((CtMethod)v-5).getReturnType();
            }
            else {
                voidType = CtClass.voidType;
            }
            this.recordReturnType(voidType, false);
            final boolean a3 = voidType == CtClass.voidType;
            if (v-4 == null) {
                makeDefaultBody(this.bytecode, voidType);
            }
            else {
                final Parser a2 = new Parser(new Lex(v-4));
                final SymbolTable v1 = new SymbolTable(this.stable);
                final Stmnt v2 = a2.parseStatement(v1);
                if (a2.hasMore()) {
                    throw new CompileError("the method/constructor body must be surrounded by {}");
                }
                boolean v3 = false;
                if (v-5 instanceof CtConstructor) {
                    v3 = !((CtConstructor)v-5).isClassInitializer();
                }
                this.gen.atMethodBody(v2, v3, a3);
            }
            return this.bytecode;
        }
        catch (NotFoundException ex) {
            throw new CompileError(ex.toString());
        }
    }
    
    private static void makeDefaultBody(final Bytecode v-2, final CtClass v-1) {
        int v3;
        int v4 = 0;
        if (v-1 instanceof CtPrimitiveType) {
            final CtPrimitiveType v2 = (CtPrimitiveType)v-1;
            v3 = v2.getReturnOp();
            if (v3 == 175) {
                final int a1 = 14;
            }
            else if (v3 == 174) {
                final int a2 = 11;
            }
            else if (v3 == 173) {
                v4 = 9;
            }
            else if (v3 == 177) {
                v4 = 0;
            }
            else {
                v4 = 3;
            }
        }
        else {
            v3 = 176;
            v4 = 1;
        }
        if (v4 != 0) {
            v-2.addOpcode(v4);
        }
        v-2.addOpcode(v3);
    }
    
    public boolean recordLocalVariables(final CodeAttribute v-3, final int v-2) throws CompileError {
        final LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute)v-3.getAttribute("LocalVariableTable");
        if (localVariableAttribute == null) {
            return false;
        }
        for (int v0 = localVariableAttribute.tableLength(), v2 = 0; v2 < v0; ++v2) {
            final int a1 = localVariableAttribute.startPc(v2);
            final int a2 = localVariableAttribute.codeLength(v2);
            if (a1 <= v-2 && v-2 < a1 + a2) {
                this.gen.recordVariable(localVariableAttribute.descriptor(v2), localVariableAttribute.variableName(v2), localVariableAttribute.index(v2), this.stable);
            }
        }
        return true;
    }
    
    public boolean recordParamNames(final CodeAttribute v2, final int v3) throws CompileError {
        final LocalVariableAttribute v4 = (LocalVariableAttribute)v2.getAttribute("LocalVariableTable");
        if (v4 == null) {
            return false;
        }
        for (int v5 = v4.tableLength(), a2 = 0; a2 < v5; ++a2) {
            final int a3 = v4.index(a2);
            if (a3 < v3) {
                this.gen.recordVariable(v4.descriptor(a2), v4.variableName(a2), a3, this.stable);
            }
        }
        return true;
    }
    
    public int recordParams(final CtClass[] a1, final boolean a2) throws CompileError {
        return this.gen.recordParams(a1, a2, "$", "$args", "$$", this.stable);
    }
    
    public int recordParams(final String a1, final CtClass[] a2, final boolean a3, final int a4, final boolean a5) throws CompileError {
        return this.gen.recordParams(a2, a5, "$", "$args", "$$", a3, a4, a1, this.stable);
    }
    
    public void setMaxLocals(final int a1) {
        this.gen.setMaxLocals(a1);
    }
    
    public int recordReturnType(final CtClass a1, final boolean a2) throws CompileError {
        this.gen.recordType(a1);
        return this.gen.recordReturnType(a1, "$r", a2 ? "$_" : null, this.stable);
    }
    
    public void recordType(final CtClass a1) {
        this.gen.recordType(a1);
    }
    
    public int recordVariable(final CtClass a1, final String a2) throws CompileError {
        return this.gen.recordVariable(a1, a2, this.stable);
    }
    
    public void recordProceed(final String a1, final String a2) throws CompileError {
        final Parser v1 = new Parser(new Lex(a1));
        final ASTree v2 = v1.parseExpression(this.stable);
        final String v3 = a2;
        final ProceedHandler v4 = new ProceedHandler() {
            final /* synthetic */ String val$m;
            final /* synthetic */ ASTree val$texpr;
            final /* synthetic */ Javac this$0;
            
            Javac$1() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public void doit(final JvstCodeGen a1, final Bytecode a2, final ASTList a3) throws CompileError {
                ASTree v1 = new Member(v3);
                if (v2 != null) {
                    v1 = Expr.make(46, v2, v1);
                }
                v1 = CallExpr.makeCall(v1, a3);
                a1.compileExpr(v1);
                a1.addNullIfVoid();
            }
            
            @Override
            public void setReturnType(final JvstTypeChecker a1, final ASTList a2) throws CompileError {
                ASTree v1 = new Member(v3);
                if (v2 != null) {
                    v1 = Expr.make(46, v2, v1);
                }
                v1 = CallExpr.makeCall(v1, a2);
                v1.accept(a1);
                a1.addNullIfVoid();
            }
        };
        this.gen.setProceedHandler(v4, "$proceed");
    }
    
    public void recordStaticProceed(final String a1, final String a2) throws CompileError {
        final String v1 = a1;
        final String v2 = a2;
        final ProceedHandler v3 = new ProceedHandler() {
            final /* synthetic */ String val$c;
            final /* synthetic */ String val$m;
            final /* synthetic */ Javac this$0;
            
            Javac$2() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public void doit(final JvstCodeGen a1, final Bytecode a2, final ASTList a3) throws CompileError {
                Expr v1 = Expr.make(35, new Symbol(v1), new Member(v2));
                v1 = CallExpr.makeCall(v1, a3);
                a1.compileExpr(v1);
                a1.addNullIfVoid();
            }
            
            @Override
            public void setReturnType(final JvstTypeChecker a1, final ASTList a2) throws CompileError {
                Expr v1 = Expr.make(35, new Symbol(v1), new Member(v2));
                v1 = CallExpr.makeCall(v1, a2);
                v1.accept(a1);
                a1.addNullIfVoid();
            }
        };
        this.gen.setProceedHandler(v3, "$proceed");
    }
    
    public void recordSpecialProceed(final String a1, final String a2, final String a3, final String a4, final int a5) throws CompileError {
        final Parser v1 = new Parser(new Lex(a1));
        final ASTree v2 = v1.parseExpression(this.stable);
        final ProceedHandler v3 = new ProceedHandler() {
            final /* synthetic */ ASTree val$texpr;
            final /* synthetic */ int val$methodIndex;
            final /* synthetic */ String val$descriptor;
            final /* synthetic */ String val$classname;
            final /* synthetic */ String val$methodname;
            final /* synthetic */ Javac this$0;
            
            Javac$3() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public void doit(final JvstCodeGen a1, final Bytecode a2, final ASTList a3) throws CompileError {
                a1.compileInvokeSpecial(v2, a5, a4, a3);
            }
            
            @Override
            public void setReturnType(final JvstTypeChecker a1, final ASTList a2) throws CompileError {
                a1.compileInvokeSpecial(v2, a2, a3, a4, a2);
            }
        };
        this.gen.setProceedHandler(v3, "$proceed");
    }
    
    public void recordProceed(final ProceedHandler a1) {
        this.gen.setProceedHandler(a1, "$proceed");
    }
    
    public void compileStmnt(final String v2) throws CompileError {
        final Parser v3 = new Parser(new Lex(v2));
        final SymbolTable v4 = new SymbolTable(this.stable);
        while (v3.hasMore()) {
            final Stmnt a1 = v3.parseStatement(v4);
            if (a1 != null) {
                a1.accept(this.gen);
            }
        }
    }
    
    public void compileExpr(final String a1) throws CompileError {
        final ASTree v1 = parseExpr(a1, this.stable);
        this.compileExpr(v1);
    }
    
    public static ASTree parseExpr(final String a1, final SymbolTable a2) throws CompileError {
        final Parser v1 = new Parser(new Lex(a1));
        return v1.parseExpression(a2);
    }
    
    public void compileExpr(final ASTree a1) throws CompileError {
        if (a1 != null) {
            this.gen.compileExpr(a1);
        }
    }
    
    public static class CtFieldWithInit extends CtField
    {
        private ASTree init;
        
        CtFieldWithInit(final CtClass a1, final String a2, final CtClass a3) throws CannotCompileException {
            super(a1, a2, a3);
            this.init = null;
        }
        
        protected void setInit(final ASTree a1) {
            this.init = a1;
        }
        
        @Override
        protected ASTree getInitAST() {
            return this.init;
        }
    }
}
