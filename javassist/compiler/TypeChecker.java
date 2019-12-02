package javassist.compiler;

import javassist.compiler.ast.*;
import javassist.bytecode.*;
import javassist.*;

public class TypeChecker extends Visitor implements Opcode, TokenId
{
    static final String javaLangObject = "java.lang.Object";
    static final String jvmJavaLangObject = "java/lang/Object";
    static final String jvmJavaLangString = "java/lang/String";
    static final String jvmJavaLangClass = "java/lang/Class";
    protected int exprType;
    protected int arrayDim;
    protected String className;
    protected MemberResolver resolver;
    protected CtClass thisClass;
    protected MethodInfo thisMethod;
    
    public TypeChecker(final CtClass a1, final ClassPool a2) {
        super();
        this.resolver = new MemberResolver(a2);
        this.thisClass = a1;
        this.thisMethod = null;
    }
    
    protected static String argTypesToString(final int[] a2, final int[] a3, final String[] v1) {
        final StringBuffer v2 = new StringBuffer();
        v2.append('(');
        final int v3 = a2.length;
        if (v3 > 0) {
            int a4 = 0;
            while (true) {
                typeToString(v2, a2[a4], a3[a4], v1[a4]);
                if (++a4 >= v3) {
                    break;
                }
                v2.append(',');
            }
        }
        v2.append(')');
        return v2.toString();
    }
    
    protected static StringBuffer typeToString(final StringBuffer v1, final int v2, int v3, final String v4) {
        String v5 = null;
        if (v2 == 307) {
            final String a1 = MemberResolver.jvmToJavaName(v4);
        }
        else if (v2 == 412) {
            final String a2 = "Object";
        }
        else {
            try {
                final String a3 = MemberResolver.getTypeName(v2);
            }
            catch (CompileError a4) {
                v5 = "?";
            }
        }
        v1.append(v5);
        while (v3-- > 0) {
            v1.append("[]");
        }
        return v1;
    }
    
    public void setThisMethod(final MethodInfo a1) {
        this.thisMethod = a1;
    }
    
    protected static void fatal() throws CompileError {
        throw new CompileError("fatal");
    }
    
    protected String getThisName() {
        return MemberResolver.javaToJvmName(this.thisClass.getName());
    }
    
    protected String getSuperName() throws CompileError {
        return MemberResolver.javaToJvmName(MemberResolver.getSuperclass(this.thisClass).getName());
    }
    
    protected String resolveClassName(final ASTList a1) throws CompileError {
        return this.resolver.resolveClassName(a1);
    }
    
    protected String resolveClassName(final String a1) throws CompileError {
        return this.resolver.resolveJvmClassName(a1);
    }
    
    @Override
    public void atNewExpr(final NewExpr v-1) throws CompileError {
        if (v-1.isArray()) {
            this.atNewArrayExpr(v-1);
        }
        else {
            final CtClass a1 = this.resolver.lookupClassByName(v-1.getClassName());
            final String v1 = a1.getName();
            final ASTList v2 = v-1.getArguments();
            this.atMethodCallCore(a1, "<init>", v2);
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = MemberResolver.javaToJvmName(v1);
        }
    }
    
    public void atNewArrayExpr(final NewExpr v2) throws CompileError {
        final int v3 = v2.getArrayType();
        final ASTList v4 = v2.getArraySize();
        final ASTList v5 = v2.getClassName();
        final ASTree v6 = v2.getInitializer();
        if (v6 != null) {
            v6.accept(this);
        }
        if (v4.length() > 1) {
            this.atMultiNewArray(v3, v5, v4);
        }
        else {
            final ASTree a1 = v4.head();
            if (a1 != null) {
                a1.accept(this);
            }
            this.exprType = v3;
            this.arrayDim = 1;
            if (v3 == 307) {
                this.className = this.resolveClassName(v5);
            }
            else {
                this.className = null;
            }
        }
    }
    
    @Override
    public void atArrayInit(final ArrayInit v2) throws CompileError {
        ASTList v3 = v2;
        while (v3 != null) {
            final ASTree a1 = v3.head();
            v3 = v3.tail();
            if (a1 != null) {
                a1.accept(this);
            }
        }
    }
    
    protected void atMultiNewArray(final int a3, final ASTList v1, ASTList v2) throws CompileError {
        final int v3 = v2.length();
        int v4 = 0;
        while (v2 != null) {
            final ASTree a4 = v2.head();
            if (a4 == null) {
                break;
            }
            ++v4;
            a4.accept(this);
            v2 = v2.tail();
        }
        this.exprType = a3;
        this.arrayDim = v3;
        if (a3 == 307) {
            this.className = this.resolveClassName(v1);
        }
        else {
            this.className = null;
        }
    }
    
    @Override
    public void atAssignExpr(final AssignExpr v2) throws CompileError {
        final int v3 = v2.getOperator();
        final ASTree v4 = v2.oprand1();
        final ASTree v5 = v2.oprand2();
        if (v4 instanceof Variable) {
            this.atVariableAssign(v2, v3, (Variable)v4, ((Variable)v4).getDeclarator(), v5);
        }
        else {
            if (v4 instanceof Expr) {
                final Expr a1 = (Expr)v4;
                if (a1.getOperator() == 65) {
                    this.atArrayAssign(v2, v3, (Expr)v4, v5);
                    return;
                }
            }
            this.atFieldAssign(v2, v3, v4, v5);
        }
    }
    
    private void atVariableAssign(final Expr a1, final int a2, final Variable a3, final Declarator a4, final ASTree a5) throws CompileError {
        final int v1 = a4.getType();
        final int v2 = a4.getArrayDim();
        final String v3 = a4.getClassName();
        if (a2 != 61) {
            this.atVariable(a3);
        }
        a5.accept(this);
        this.exprType = v1;
        this.arrayDim = v2;
        this.className = v3;
    }
    
    private void atArrayAssign(final Expr a1, final int a2, final Expr a3, final ASTree a4) throws CompileError {
        this.atArrayRead(a3.oprand1(), a3.oprand2());
        final int v1 = this.exprType;
        final int v2 = this.arrayDim;
        final String v3 = this.className;
        a4.accept(this);
        this.exprType = v1;
        this.arrayDim = v2;
        this.className = v3;
    }
    
    protected void atFieldAssign(final Expr a1, final int a2, final ASTree a3, final ASTree a4) throws CompileError {
        final CtField v1 = this.fieldAccess(a3);
        this.atFieldRead(v1);
        final int v2 = this.exprType;
        final int v3 = this.arrayDim;
        final String v4 = this.className;
        a4.accept(this);
        this.exprType = v2;
        this.arrayDim = v3;
        this.className = v4;
    }
    
    @Override
    public void atCondExpr(final CondExpr a1) throws CompileError {
        this.booleanExpr(a1.condExpr());
        a1.thenExpr().accept(this);
        final int v1 = this.exprType;
        final int v2 = this.arrayDim;
        final String v3 = this.className;
        a1.elseExpr().accept(this);
        if (v2 == 0 && v2 == this.arrayDim) {
            if (CodeGen.rightIsStrong(v1, this.exprType)) {
                a1.setThen(new CastExpr(this.exprType, 0, a1.thenExpr()));
            }
            else if (CodeGen.rightIsStrong(this.exprType, v1)) {
                a1.setElse(new CastExpr(v1, 0, a1.elseExpr()));
                this.exprType = v1;
            }
        }
    }
    
    @Override
    public void atBinExpr(final BinExpr v-2) throws CompileError {
        final int operator = v-2.getOperator();
        final int v0 = CodeGen.lookupBinOp(operator);
        if (v0 >= 0) {
            if (operator == 43) {
                Expr a1 = this.atPlusExpr(v-2);
                if (a1 != null) {
                    a1 = CallExpr.makeCall(Expr.make(46, a1, new Member("toString")), null);
                    v-2.setOprand1(a1);
                    v-2.setOprand2(null);
                    this.className = "java/lang/String";
                }
            }
            else {
                final ASTree v2 = v-2.oprand1();
                final ASTree v3 = v-2.oprand2();
                v2.accept(this);
                final int v4 = this.exprType;
                v3.accept(this);
                if (!this.isConstant(v-2, operator, v2, v3)) {
                    this.computeBinExprType(v-2, operator, v4);
                }
            }
        }
        else {
            this.booleanExpr(v-2);
        }
    }
    
    private Expr atPlusExpr(final BinExpr v-5) throws CompileError {
        final ASTree oprand1 = v-5.oprand1();
        final ASTree oprand2 = v-5.oprand2();
        if (oprand2 == null) {
            oprand1.accept(this);
            return null;
        }
        if (isPlusExpr(oprand1)) {
            final Expr a1 = this.atPlusExpr((BinExpr)oprand1);
            if (a1 != null) {
                oprand2.accept(this);
                this.exprType = 307;
                this.arrayDim = 0;
                this.className = "java/lang/StringBuffer";
                return makeAppendCall(a1, oprand2);
            }
        }
        else {
            oprand1.accept(this);
        }
        final int exprType = this.exprType;
        final int arrayDim = this.arrayDim;
        final String v0 = this.className;
        oprand2.accept(this);
        if (this.isConstant(v-5, 43, oprand1, oprand2)) {
            return null;
        }
        if ((exprType == 307 && arrayDim == 0 && "java/lang/String".equals(v0)) || (this.exprType == 307 && this.arrayDim == 0 && "java/lang/String".equals(this.className))) {
            final ASTList v2 = ASTList.make(new Symbol("java"), new Symbol("lang"), new Symbol("StringBuffer"));
            final ASTree v3 = new NewExpr(v2, null);
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/StringBuffer";
            return makeAppendCall(makeAppendCall(v3, oprand1), oprand2);
        }
        this.computeBinExprType(v-5, 43, exprType);
        return null;
    }
    
    private boolean isConstant(final BinExpr a1, final int a2, ASTree a3, ASTree a4) throws CompileError {
        a3 = stripPlusExpr(a3);
        a4 = stripPlusExpr(a4);
        ASTree v1 = null;
        if (a3 instanceof StringL && a4 instanceof StringL && a2 == 43) {
            v1 = new StringL(((StringL)a3).get() + ((StringL)a4).get());
        }
        else if (a3 instanceof IntConst) {
            v1 = ((IntConst)a3).compute(a2, a4);
        }
        else if (a3 instanceof DoubleConst) {
            v1 = ((DoubleConst)a3).compute(a2, a4);
        }
        if (v1 == null) {
            return false;
        }
        a1.setOperator(43);
        a1.setOprand1(v1);
        a1.setOprand2(null);
        v1.accept(this);
        return true;
    }
    
    static ASTree stripPlusExpr(final ASTree v-2) {
        if (v-2 instanceof BinExpr) {
            final BinExpr a1 = (BinExpr)v-2;
            if (a1.getOperator() == 43 && a1.oprand2() == null) {
                return a1.getLeft();
            }
        }
        else if (v-2 instanceof Expr) {
            final Expr expr = (Expr)v-2;
            final int v0 = expr.getOperator();
            if (v0 == 35) {
                final ASTree v2 = getConstantFieldValue((Member)expr.oprand2());
                if (v2 != null) {
                    return v2;
                }
            }
            else if (v0 == 43 && expr.getRight() == null) {
                return expr.getLeft();
            }
        }
        else if (v-2 instanceof Member) {
            final ASTree constantFieldValue = getConstantFieldValue((Member)v-2);
            if (constantFieldValue != null) {
                return constantFieldValue;
            }
        }
        return v-2;
    }
    
    private static ASTree getConstantFieldValue(final Member a1) {
        return getConstantFieldValue(a1.getField());
    }
    
    public static ASTree getConstantFieldValue(final CtField v-1) {
        if (v-1 == null) {
            return null;
        }
        final Object v0 = v-1.getConstantValue();
        if (v0 == null) {
            return null;
        }
        if (v0 instanceof String) {
            return new StringL((String)v0);
        }
        if (v0 instanceof Double || v0 instanceof Float) {
            final int a1 = (v0 instanceof Double) ? 405 : 404;
            return new DoubleConst(((Number)v0).doubleValue(), a1);
        }
        if (v0 instanceof Number) {
            final int v2 = (v0 instanceof Long) ? 403 : 402;
            return new IntConst(((Number)v0).longValue(), v2);
        }
        if (v0 instanceof Boolean) {
            return new Keyword(v0 ? 410 : 411);
        }
        return null;
    }
    
    private static boolean isPlusExpr(final ASTree v-1) {
        if (v-1 instanceof BinExpr) {
            final BinExpr a1 = (BinExpr)v-1;
            final int v1 = a1.getOperator();
            return v1 == 43;
        }
        return false;
    }
    
    private static Expr makeAppendCall(final ASTree a1, final ASTree a2) {
        return CallExpr.makeCall(Expr.make(46, a1, new Member("append")), new ASTList(a2));
    }
    
    private void computeBinExprType(final BinExpr a1, final int a2, final int a3) throws CompileError {
        final int v1 = this.exprType;
        if (a2 == 364 || a2 == 366 || a2 == 370) {
            this.exprType = a3;
        }
        else {
            this.insertCast(a1, a3, v1);
        }
        if (CodeGen.isP_INT(this.exprType) && this.exprType != 301) {
            this.exprType = 324;
        }
    }
    
    private void booleanExpr(final ASTree v-2) throws CompileError {
        final int compOperator = CodeGen.getCompOperator(v-2);
        if (compOperator == 358) {
            final BinExpr a1 = (BinExpr)v-2;
            a1.oprand1().accept(this);
            final int v1 = this.exprType;
            final int v2 = this.arrayDim;
            a1.oprand2().accept(this);
            if (v2 == 0 && this.arrayDim == 0) {
                this.insertCast(a1, v1, this.exprType);
            }
        }
        else if (compOperator == 33) {
            ((Expr)v-2).oprand1().accept(this);
        }
        else if (compOperator == 369 || compOperator == 368) {
            final BinExpr v3 = (BinExpr)v-2;
            v3.oprand1().accept(this);
            v3.oprand2().accept(this);
        }
        else {
            v-2.accept(this);
        }
        this.exprType = 301;
        this.arrayDim = 0;
    }
    
    private void insertCast(final BinExpr a1, final int a2, final int a3) throws CompileError {
        if (CodeGen.rightIsStrong(a2, a3)) {
            a1.setLeft(new CastExpr(a3, 0, a1.oprand1()));
        }
        else {
            this.exprType = a2;
        }
    }
    
    @Override
    public void atCastExpr(final CastExpr a1) throws CompileError {
        final String v1 = this.resolveClassName(a1.getClassName());
        a1.getOprand().accept(this);
        this.exprType = a1.getType();
        this.arrayDim = a1.getArrayDim();
        this.className = v1;
    }
    
    @Override
    public void atInstanceOfExpr(final InstanceOfExpr a1) throws CompileError {
        a1.getOprand().accept(this);
        this.exprType = 301;
        this.arrayDim = 0;
    }
    
    @Override
    public void atExpr(final Expr v-2) throws CompileError {
        final int operator = v-2.getOperator();
        final ASTree v0 = v-2.oprand1();
        if (operator == 46) {
            final String v2 = ((Symbol)v-2.oprand2()).get();
            if (v2.equals("length")) {
                try {
                    this.atArrayLength(v-2);
                }
                catch (NoFieldException a1) {
                    this.atFieldRead(v-2);
                }
            }
            else if (v2.equals("class")) {
                this.atClassObject(v-2);
            }
            else {
                this.atFieldRead(v-2);
            }
        }
        else if (operator == 35) {
            final String v2 = ((Symbol)v-2.oprand2()).get();
            if (v2.equals("class")) {
                this.atClassObject(v-2);
            }
            else {
                this.atFieldRead(v-2);
            }
        }
        else if (operator == 65) {
            this.atArrayRead(v0, v-2.oprand2());
        }
        else if (operator == 362 || operator == 363) {
            this.atPlusPlus(operator, v0, v-2);
        }
        else if (operator == 33) {
            this.booleanExpr(v-2);
        }
        else if (operator == 67) {
            fatal();
        }
        else {
            v0.accept(this);
            if (!this.isConstant(v-2, operator, v0) && (operator == 45 || operator == 126) && CodeGen.isP_INT(this.exprType)) {
                this.exprType = 324;
            }
        }
    }
    
    private boolean isConstant(final Expr v2, final int v3, ASTree v4) {
        v4 = stripPlusExpr(v4);
        if (v4 instanceof IntConst) {
            final IntConst a1 = (IntConst)v4;
            long a2 = a1.get();
            if (v3 == 45) {
                a2 = -a2;
            }
            else {
                if (v3 != 126) {
                    return false;
                }
                a2 ^= -1L;
            }
            a1.set(a2);
        }
        else {
            if (!(v4 instanceof DoubleConst)) {
                return false;
            }
            final DoubleConst a3 = (DoubleConst)v4;
            if (v3 != 45) {
                return false;
            }
            a3.set(-a3.get());
        }
        v2.setOperator(43);
        return true;
    }
    
    @Override
    public void atCallExpr(final CallExpr v-6) throws CompileError {
        String v-7 = null;
        CtClass v-8 = null;
        final ASTree oprand1 = v-6.oprand1();
        final ASTList v-9 = (ASTList)v-6.oprand2();
        if (oprand1 instanceof Member) {
            v-7 = ((Member)oprand1).get();
            v-8 = this.thisClass;
        }
        else if (oprand1 instanceof Keyword) {
            v-7 = "<init>";
            if (((Keyword)oprand1).get() == 336) {
                v-8 = MemberResolver.getSuperclass(this.thisClass);
            }
            else {
                v-8 = this.thisClass;
            }
        }
        else if (oprand1 instanceof Expr) {
            final Expr expr = (Expr)oprand1;
            v-7 = ((Symbol)expr.oprand2()).get();
            final int v0 = expr.getOperator();
            if (v0 == 35) {
                v-8 = this.resolver.lookupClass(((Symbol)expr.oprand1()).get(), false);
            }
            else if (v0 == 46) {
                final ASTree v2 = expr.oprand1();
                final String v3 = isDotSuper(v2);
                if (v3 != null) {
                    v-8 = MemberResolver.getSuperInterface(this.thisClass, v3);
                }
                else {
                    try {
                        v2.accept(this);
                    }
                    catch (NoFieldException a1) {
                        if (a1.getExpr() != v2) {
                            throw a1;
                        }
                        this.exprType = 307;
                        this.arrayDim = 0;
                        this.className = a1.getField();
                        expr.setOperator(35);
                        expr.setOprand1(new Symbol(MemberResolver.jvmToJavaName(this.className)));
                    }
                    if (this.arrayDim > 0) {
                        v-8 = this.resolver.lookupClass("java.lang.Object", true);
                    }
                    else if (this.exprType == 307) {
                        v-8 = this.resolver.lookupClassByJvmName(this.className);
                    }
                    else {
                        badMethod();
                    }
                }
            }
            else {
                badMethod();
            }
        }
        else {
            fatal();
        }
        final MemberResolver.Method atMethodCallCore = this.atMethodCallCore(v-8, v-7, v-9);
        v-6.setMethod(atMethodCallCore);
    }
    
    private static void badMethod() throws CompileError {
        throw new CompileError("bad method");
    }
    
    static String isDotSuper(final ASTree v0) {
        if (v0 instanceof Expr) {
            final Expr v = (Expr)v0;
            if (v.getOperator() == 46) {
                final ASTree a1 = v.oprand2();
                if (a1 instanceof Keyword && ((Keyword)a1).get() == 336) {
                    return ((Symbol)v.oprand1()).get();
                }
            }
        }
        return null;
    }
    
    public MemberResolver.Method atMethodCallCore(final CtClass v-9, final String v-8, final ASTList v-7) throws CompileError {
        final int methodArgsLength = this.getMethodArgsLength(v-7);
        final int[] a5 = new int[methodArgsLength];
        final int[] a6 = new int[methodArgsLength];
        final String[] v2 = new String[methodArgsLength];
        this.atMethodArgs(v-7, a5, a6, v2);
        final MemberResolver.Method lookupMethod = this.resolver.lookupMethod(v-9, this.thisClass, this.thisMethod, v-8, a5, a6, v2);
        if (lookupMethod == null) {
            final String a2 = v-9.getName();
            final String a3 = argTypesToString(a5, a6, v2);
            String v1 = null;
            if (v-8.equals("<init>")) {
                final String a4 = "cannot find constructor " + a2 + a3;
            }
            else {
                v1 = v-8 + a3 + " not found in " + a2;
            }
            throw new CompileError(v1);
        }
        final String descriptor = lookupMethod.info.getDescriptor();
        this.setReturnType(descriptor);
        return lookupMethod;
    }
    
    public int getMethodArgsLength(final ASTList a1) {
        return ASTList.length(a1);
    }
    
    public void atMethodArgs(ASTList a3, final int[] a4, final int[] v1, final String[] v2) throws CompileError {
        int v3 = 0;
        while (a3 != null) {
            final ASTree a5 = a3.head();
            a5.accept(this);
            a4[v3] = this.exprType;
            v1[v3] = this.arrayDim;
            v2[v3] = this.className;
            ++v3;
            a3 = a3.tail();
        }
    }
    
    void setReturnType(final String v2) throws CompileError {
        int v3 = v2.indexOf(41);
        if (v3 < 0) {
            badMethod();
        }
        char v4 = v2.charAt(++v3);
        int v5 = 0;
        while (v4 == '[') {
            ++v5;
            v4 = v2.charAt(++v3);
        }
        this.arrayDim = v5;
        if (v4 == 'L') {
            final int a1 = v2.indexOf(59, v3 + 1);
            if (a1 < 0) {
                badMethod();
            }
            this.exprType = 307;
            this.className = v2.substring(v3 + 1, a1);
        }
        else {
            this.exprType = MemberResolver.descToType(v4);
            this.className = null;
        }
    }
    
    private void atFieldRead(final ASTree a1) throws CompileError {
        this.atFieldRead(this.fieldAccess(a1));
    }
    
    private void atFieldRead(final CtField a1) throws CompileError {
        final FieldInfo v1 = a1.getFieldInfo2();
        final String v2 = v1.getDescriptor();
        int v3 = 0;
        int v4 = 0;
        char v5;
        for (v5 = v2.charAt(v3); v5 == '['; v5 = v2.charAt(++v3)) {
            ++v4;
        }
        this.arrayDim = v4;
        this.exprType = MemberResolver.descToType(v5);
        if (v5 == 'L') {
            this.className = v2.substring(v3 + 1, v2.indexOf(59, v3 + 1));
        }
        else {
            this.className = null;
        }
    }
    
    protected CtField fieldAccess(final ASTree v-2) throws CompileError {
        if (v-2 instanceof Member) {
            final Member member = (Member)v-2;
            final String v0 = member.get();
            try {
                final CtField a1 = this.thisClass.getField(v0);
                if (Modifier.isStatic(a1.getModifiers())) {
                    member.setField(a1);
                }
                return a1;
            }
            catch (NotFoundException v9) {
                throw new NoFieldException(v0, v-2);
            }
        }
        if (v-2 instanceof Expr) {
            final Expr expr = (Expr)v-2;
            final int v2 = expr.getOperator();
            if (v2 == 35) {
                final Member v3 = (Member)expr.oprand2();
                final CtField v4 = this.resolver.lookupField(((Symbol)expr.oprand1()).get(), v3);
                v3.setField(v4);
                return v4;
            }
            if (v2 == 46) {
                try {
                    expr.oprand1().accept(this);
                }
                catch (NoFieldException v5) {
                    if (v5.getExpr() != expr.oprand1()) {
                        throw v5;
                    }
                    return this.fieldAccess2(expr, v5.getField());
                }
                CompileError v6 = null;
                try {
                    if (this.exprType == 307 && this.arrayDim == 0) {
                        return this.resolver.lookupFieldByJvmName(this.className, (Symbol)expr.oprand2());
                    }
                }
                catch (CompileError v7) {
                    v6 = v7;
                }
                final ASTree v8 = expr.oprand1();
                if (v8 instanceof Symbol) {
                    return this.fieldAccess2(expr, ((Symbol)v8).get());
                }
                if (v6 != null) {
                    throw v6;
                }
            }
        }
        throw new CompileError("bad filed access");
    }
    
    private CtField fieldAccess2(final Expr a1, final String a2) throws CompileError {
        final Member v1 = (Member)a1.oprand2();
        final CtField v2 = this.resolver.lookupFieldByJvmName2(a2, v1, a1);
        a1.setOperator(35);
        a1.setOprand1(new Symbol(MemberResolver.jvmToJavaName(a2)));
        v1.setField(v2);
        return v2;
    }
    
    public void atClassObject(final Expr a1) throws CompileError {
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/Class";
    }
    
    public void atArrayLength(final Expr a1) throws CompileError {
        a1.oprand1().accept(this);
        if (this.arrayDim == 0) {
            throw new NoFieldException("length", a1);
        }
        this.exprType = 324;
        this.arrayDim = 0;
    }
    
    public void atArrayRead(final ASTree a1, final ASTree a2) throws CompileError {
        a1.accept(this);
        final int v1 = this.exprType;
        final int v2 = this.arrayDim;
        final String v3 = this.className;
        a2.accept(this);
        this.exprType = v1;
        this.arrayDim = v2 - 1;
        this.className = v3;
    }
    
    private void atPlusPlus(final int v2, ASTree v3, final Expr v4) throws CompileError {
        final boolean v5 = v3 == null;
        if (v5) {
            v3 = v4.oprand2();
        }
        if (v3 instanceof Variable) {
            final Declarator a1 = ((Variable)v3).getDeclarator();
            this.exprType = a1.getType();
            this.arrayDim = a1.getArrayDim();
        }
        else {
            if (v3 instanceof Expr) {
                final Expr a2 = (Expr)v3;
                if (a2.getOperator() == 65) {
                    this.atArrayRead(a2.oprand1(), a2.oprand2());
                    final int a3 = this.exprType;
                    if (a3 == 324 || a3 == 303 || a3 == 306 || a3 == 334) {
                        this.exprType = 324;
                    }
                    return;
                }
            }
            this.atFieldPlusPlus(v3);
        }
    }
    
    protected void atFieldPlusPlus(final ASTree a1) throws CompileError {
        final CtField v1 = this.fieldAccess(a1);
        this.atFieldRead(v1);
        final int v2 = this.exprType;
        if (v2 == 324 || v2 == 303 || v2 == 306 || v2 == 334) {
            this.exprType = 324;
        }
    }
    
    @Override
    public void atMember(final Member a1) throws CompileError {
        this.atFieldRead(a1);
    }
    
    @Override
    public void atVariable(final Variable a1) throws CompileError {
        final Declarator v1 = a1.getDeclarator();
        this.exprType = v1.getType();
        this.arrayDim = v1.getArrayDim();
        this.className = v1.getClassName();
    }
    
    @Override
    public void atKeyword(final Keyword a1) throws CompileError {
        this.arrayDim = 0;
        final int v1 = a1.get();
        switch (v1) {
            case 410:
            case 411: {
                this.exprType = 301;
                break;
            }
            case 412: {
                this.exprType = 412;
                break;
            }
            case 336:
            case 339: {
                this.exprType = 307;
                if (v1 == 339) {
                    this.className = this.getThisName();
                    break;
                }
                this.className = this.getSuperName();
                break;
            }
            default: {
                fatal();
                break;
            }
        }
    }
    
    @Override
    public void atStringL(final StringL a1) throws CompileError {
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/String";
    }
    
    @Override
    public void atIntConst(final IntConst a1) throws CompileError {
        this.arrayDim = 0;
        final int v1 = a1.getType();
        if (v1 == 402 || v1 == 401) {
            this.exprType = ((v1 == 402) ? 324 : 306);
        }
        else {
            this.exprType = 326;
        }
    }
    
    @Override
    public void atDoubleConst(final DoubleConst a1) throws CompileError {
        this.arrayDim = 0;
        if (a1.getType() == 405) {
            this.exprType = 312;
        }
        else {
            this.exprType = 317;
        }
    }
}
