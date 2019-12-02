package javassist.compiler;

import javassist.bytecode.*;
import java.util.*;
import javassist.compiler.ast.*;

public abstract class CodeGen extends Visitor implements Opcode, TokenId
{
    static final String javaLangObject = "java.lang.Object";
    static final String jvmJavaLangObject = "java/lang/Object";
    static final String javaLangString = "java.lang.String";
    static final String jvmJavaLangString = "java/lang/String";
    protected Bytecode bytecode;
    private int tempVar;
    TypeChecker typeChecker;
    protected boolean hasReturned;
    public boolean inStaticMethod;
    protected ArrayList breakList;
    protected ArrayList continueList;
    protected ReturnHook returnHooks;
    protected int exprType;
    protected int arrayDim;
    protected String className;
    static final int[] binOp;
    private static final int[] ifOp;
    private static final int[] ifOp2;
    private static final int P_DOUBLE = 0;
    private static final int P_FLOAT = 1;
    private static final int P_LONG = 2;
    private static final int P_INT = 3;
    private static final int P_OTHER = -1;
    private static final int[] castOp;
    
    public CodeGen(final Bytecode a1) {
        super();
        this.bytecode = a1;
        this.tempVar = -1;
        this.typeChecker = null;
        this.hasReturned = false;
        this.inStaticMethod = false;
        this.breakList = null;
        this.continueList = null;
        this.returnHooks = null;
    }
    
    public void setTypeChecker(final TypeChecker a1) {
        this.typeChecker = a1;
    }
    
    protected static void fatal() throws CompileError {
        throw new CompileError("fatal");
    }
    
    public static boolean is2word(final int a1, final int a2) {
        return a2 == 0 && (a1 == 312 || a1 == 326);
    }
    
    public int getMaxLocals() {
        return this.bytecode.getMaxLocals();
    }
    
    public void setMaxLocals(final int a1) {
        this.bytecode.setMaxLocals(a1);
    }
    
    protected void incMaxLocals(final int a1) {
        this.bytecode.incMaxLocals(a1);
    }
    
    protected int getTempVar() {
        if (this.tempVar < 0) {
            this.tempVar = this.getMaxLocals();
            this.incMaxLocals(2);
        }
        return this.tempVar;
    }
    
    protected int getLocalVar(final Declarator a1) {
        int v1 = a1.getLocalVar();
        if (v1 < 0) {
            v1 = this.getMaxLocals();
            a1.setLocalVar(v1);
            this.incMaxLocals(1);
        }
        return v1;
    }
    
    protected abstract String getThisName();
    
    protected abstract String getSuperName() throws CompileError;
    
    protected abstract String resolveClassName(final ASTList p0) throws CompileError;
    
    protected abstract String resolveClassName(final String p0) throws CompileError;
    
    protected static String toJvmArrayName(final String v1, final int v2) {
        if (v1 == null) {
            return null;
        }
        if (v2 == 0) {
            return v1;
        }
        final StringBuffer a1 = new StringBuffer();
        int a2 = v2;
        while (a2-- > 0) {
            a1.append('[');
        }
        a1.append('L');
        a1.append(v1);
        a1.append(';');
        return a1.toString();
    }
    
    protected static String toJvmTypeName(final int a1, int a2) {
        char v1 = 'I';
        switch (a1) {
            case 301: {
                v1 = 'Z';
                break;
            }
            case 303: {
                v1 = 'B';
                break;
            }
            case 306: {
                v1 = 'C';
                break;
            }
            case 334: {
                v1 = 'S';
                break;
            }
            case 324: {
                v1 = 'I';
                break;
            }
            case 326: {
                v1 = 'J';
                break;
            }
            case 317: {
                v1 = 'F';
                break;
            }
            case 312: {
                v1 = 'D';
                break;
            }
            case 344: {
                v1 = 'V';
                break;
            }
        }
        final StringBuffer v2 = new StringBuffer();
        while (a2-- > 0) {
            v2.append('[');
        }
        v2.append(v1);
        return v2.toString();
    }
    
    public void compileExpr(final ASTree a1) throws CompileError {
        this.doTypeCheck(a1);
        a1.accept(this);
    }
    
    public boolean compileBooleanExpr(final boolean a1, final ASTree a2) throws CompileError {
        this.doTypeCheck(a2);
        return this.booleanExpr(a1, a2);
    }
    
    public void doTypeCheck(final ASTree a1) throws CompileError {
        if (this.typeChecker != null) {
            a1.accept(this.typeChecker);
        }
    }
    
    @Override
    public void atASTList(final ASTList a1) throws CompileError {
        fatal();
    }
    
    @Override
    public void atPair(final Pair a1) throws CompileError {
        fatal();
    }
    
    @Override
    public void atSymbol(final Symbol a1) throws CompileError {
        fatal();
    }
    
    @Override
    public void atFieldDecl(final FieldDecl a1) throws CompileError {
        a1.getInit().accept(this);
    }
    
    @Override
    public void atMethodDecl(final MethodDecl v2) throws CompileError {
        ASTList v3 = v2.getModifiers();
        this.setMaxLocals(1);
        while (v3 != null) {
            final Keyword a1 = (Keyword)v3.head();
            v3 = v3.tail();
            if (a1.get() == 335) {
                this.setMaxLocals(0);
                this.inStaticMethod = true;
            }
        }
        for (ASTList v4 = v2.getParams(); v4 != null; v4 = v4.tail()) {
            this.atDeclarator((Declarator)v4.head());
        }
        final Stmnt v5 = v2.getBody();
        this.atMethodBody(v5, v2.isConstructor(), v2.getReturn().getType() == 344);
    }
    
    public void atMethodBody(final Stmnt a1, final boolean a2, final boolean a3) throws CompileError {
        if (a1 == null) {
            return;
        }
        if (a2 && this.needsSuperCall(a1)) {
            this.insertDefaultSuperCall();
        }
        this.hasReturned = false;
        a1.accept(this);
        if (!this.hasReturned) {
            if (!a3) {
                throw new CompileError("no return statement");
            }
            this.bytecode.addOpcode(177);
            this.hasReturned = true;
        }
    }
    
    private boolean needsSuperCall(Stmnt v-1) throws CompileError {
        if (v-1.getOperator() == 66) {
            v-1 = (Stmnt)v-1.head();
        }
        if (v-1 != null && v-1.getOperator() == 69) {
            final ASTree v0 = v-1.head();
            if (v0 != null && v0 instanceof Expr && ((Expr)v0).getOperator() == 67) {
                final ASTree v2 = ((Expr)v0).head();
                if (v2 instanceof Keyword) {
                    final int a1 = ((Keyword)v2).get();
                    return a1 != 339 && a1 != 336;
                }
            }
        }
        return true;
    }
    
    protected abstract void insertDefaultSuperCall() throws CompileError;
    
    @Override
    public void atStmnt(final Stmnt v-1) throws CompileError {
        if (v-1 == null) {
            return;
        }
        final int v0 = v-1.getOperator();
        if (v0 == 69) {
            final ASTree v2 = v-1.getLeft();
            this.doTypeCheck(v2);
            if (v2 instanceof AssignExpr) {
                this.atAssignExpr((AssignExpr)v2, false);
            }
            else if (isPlusPlusExpr(v2)) {
                final Expr a1 = (Expr)v2;
                this.atPlusPlus(a1.getOperator(), a1.oprand1(), a1, false);
            }
            else {
                v2.accept(this);
                if (is2word(this.exprType, this.arrayDim)) {
                    this.bytecode.addOpcode(88);
                }
                else if (this.exprType != 344) {
                    this.bytecode.addOpcode(87);
                }
            }
        }
        else if (v0 == 68 || v0 == 66) {
            ASTList v3 = v-1;
            while (v3 != null) {
                final ASTree v4 = v3.head();
                v3 = v3.tail();
                if (v4 != null) {
                    v4.accept(this);
                }
            }
        }
        else if (v0 == 320) {
            this.atIfStmnt(v-1);
        }
        else if (v0 == 346 || v0 == 311) {
            this.atWhileStmnt(v-1, v0 == 346);
        }
        else if (v0 == 318) {
            this.atForStmnt(v-1);
        }
        else if (v0 == 302 || v0 == 309) {
            this.atBreakStmnt(v-1, v0 == 302);
        }
        else if (v0 == 333) {
            this.atReturnStmnt(v-1);
        }
        else if (v0 == 340) {
            this.atThrowStmnt(v-1);
        }
        else if (v0 == 343) {
            this.atTryStmnt(v-1);
        }
        else if (v0 == 337) {
            this.atSwitchStmnt(v-1);
        }
        else {
            if (v0 != 338) {
                this.hasReturned = false;
                throw new CompileError("sorry, not supported statement: TokenId " + v0);
            }
            this.atSyncStmnt(v-1);
        }
    }
    
    private void atIfStmnt(final Stmnt a1) throws CompileError {
        final ASTree v1 = a1.head();
        final Stmnt v2 = (Stmnt)a1.tail().head();
        final Stmnt v3 = (Stmnt)a1.tail().tail().head();
        if (this.compileBooleanExpr(false, v1)) {
            this.hasReturned = false;
            if (v3 != null) {
                v3.accept(this);
            }
            return;
        }
        final int v4 = this.bytecode.currentPc();
        int v5 = 0;
        this.bytecode.addIndex(0);
        this.hasReturned = false;
        if (v2 != null) {
            v2.accept(this);
        }
        final boolean v6 = this.hasReturned;
        this.hasReturned = false;
        if (v3 != null && !v6) {
            this.bytecode.addOpcode(167);
            v5 = this.bytecode.currentPc();
            this.bytecode.addIndex(0);
        }
        this.bytecode.write16bit(v4, this.bytecode.currentPc() - v4 + 1);
        if (v3 != null) {
            v3.accept(this);
            if (!v6) {
                this.bytecode.write16bit(v5, this.bytecode.currentPc() - v5 + 1);
            }
            this.hasReturned = (v6 && this.hasReturned);
        }
    }
    
    private void atWhileStmnt(final Stmnt a1, final boolean a2) throws CompileError {
        final ArrayList v1 = this.breakList;
        final ArrayList v2 = this.continueList;
        this.breakList = new ArrayList();
        this.continueList = new ArrayList();
        final ASTree v3 = a1.head();
        final Stmnt v4 = (Stmnt)a1.tail();
        int v5 = 0;
        if (a2) {
            this.bytecode.addOpcode(167);
            v5 = this.bytecode.currentPc();
            this.bytecode.addIndex(0);
        }
        final int v6 = this.bytecode.currentPc();
        if (v4 != null) {
            v4.accept(this);
        }
        final int v7 = this.bytecode.currentPc();
        if (a2) {
            this.bytecode.write16bit(v5, v7 - v5 + 1);
        }
        boolean v8 = this.compileBooleanExpr(true, v3);
        if (v8) {
            this.bytecode.addOpcode(167);
            v8 = (this.breakList.size() == 0);
        }
        this.bytecode.addIndex(v6 - this.bytecode.currentPc() + 1);
        this.patchGoto(this.breakList, this.bytecode.currentPc());
        this.patchGoto(this.continueList, v7);
        this.continueList = v2;
        this.breakList = v1;
        this.hasReturned = v8;
    }
    
    protected void patchGoto(final ArrayList v2, final int v3) {
        for (int v4 = v2.size(), a2 = 0; a2 < v4; ++a2) {
            final int a3 = v2.get(a2);
            this.bytecode.write16bit(a3, v3 - a3 + 1);
        }
    }
    
    private void atForStmnt(final Stmnt a1) throws CompileError {
        final ArrayList v1 = this.breakList;
        final ArrayList v2 = this.continueList;
        this.breakList = new ArrayList();
        this.continueList = new ArrayList();
        final Stmnt v3 = (Stmnt)a1.head();
        ASTList v4 = a1.tail();
        final ASTree v5 = v4.head();
        v4 = v4.tail();
        final Stmnt v6 = (Stmnt)v4.head();
        final Stmnt v7 = (Stmnt)v4.tail();
        if (v3 != null) {
            v3.accept(this);
        }
        final int v8 = this.bytecode.currentPc();
        int v9 = 0;
        if (v5 != null) {
            if (this.compileBooleanExpr(false, v5)) {
                this.continueList = v2;
                this.breakList = v1;
                this.hasReturned = false;
                return;
            }
            v9 = this.bytecode.currentPc();
            this.bytecode.addIndex(0);
        }
        if (v7 != null) {
            v7.accept(this);
        }
        final int v10 = this.bytecode.currentPc();
        if (v6 != null) {
            v6.accept(this);
        }
        this.bytecode.addOpcode(167);
        this.bytecode.addIndex(v8 - this.bytecode.currentPc() + 1);
        final int v11 = this.bytecode.currentPc();
        if (v5 != null) {
            this.bytecode.write16bit(v9, v11 - v9 + 1);
        }
        this.patchGoto(this.breakList, v11);
        this.patchGoto(this.continueList, v10);
        this.continueList = v2;
        this.breakList = v1;
        this.hasReturned = false;
    }
    
    private void atSwitchStmnt(final Stmnt v-10) throws CompileError {
        this.compileExpr(v-10.head());
        final ArrayList breakList = this.breakList;
        this.breakList = new ArrayList();
        final int currentPc = this.bytecode.currentPc();
        this.bytecode.addOpcode(171);
        int n = 3 - (currentPc & 0x3);
        while (n-- > 0) {
            this.bytecode.add(0);
        }
        final Stmnt stmnt = (Stmnt)v-10.tail();
        int a2 = 0;
        for (ASTList a1 = stmnt; a1 != null; a1 = a1.tail()) {
            if (((Stmnt)a1.head()).getOperator() == 304) {
                ++a2;
            }
        }
        final int currentPc2 = this.bytecode.currentPc();
        this.bytecode.addGap(4);
        this.bytecode.add32bit(a2);
        this.bytecode.addGap(a2 * 8);
        final long[] array = new long[a2];
        int n2 = 0;
        int currentPc3 = -1;
        for (ASTList v0 = stmnt; v0 != null; v0 = v0.tail()) {
            final Stmnt v2 = (Stmnt)v0.head();
            final int v3 = v2.getOperator();
            if (v3 == 310) {
                currentPc3 = this.bytecode.currentPc();
            }
            else if (v3 != 304) {
                fatal();
            }
            else {
                array[n2++] = ((long)this.computeLabel(v2.head()) << 32) + ((long)(this.bytecode.currentPc() - currentPc) & -1L);
            }
            this.hasReturned = false;
            ((Stmnt)v2.tail()).accept(this);
        }
        Arrays.sort(array);
        int v4 = currentPc2 + 8;
        for (int v5 = 0; v5 < a2; ++v5) {
            this.bytecode.write32bit(v4, (int)(array[v5] >>> 32));
            this.bytecode.write32bit(v4 + 4, (int)array[v5]);
            v4 += 8;
        }
        if (currentPc3 < 0 || this.breakList.size() > 0) {
            this.hasReturned = false;
        }
        int v5 = this.bytecode.currentPc();
        if (currentPc3 < 0) {
            currentPc3 = v5;
        }
        this.bytecode.write32bit(currentPc2, currentPc3 - currentPc);
        this.patchGoto(this.breakList, v5);
        this.breakList = breakList;
    }
    
    private int computeLabel(ASTree a1) throws CompileError {
        this.doTypeCheck(a1);
        a1 = TypeChecker.stripPlusExpr(a1);
        if (a1 instanceof IntConst) {
            return (int)((IntConst)a1).get();
        }
        throw new CompileError("bad case label");
    }
    
    private void atBreakStmnt(final Stmnt a1, final boolean a2) throws CompileError {
        if (a1.head() != null) {
            throw new CompileError("sorry, not support labeled break or continue");
        }
        this.bytecode.addOpcode(167);
        final Integer v1 = new Integer(this.bytecode.currentPc());
        this.bytecode.addIndex(0);
        if (a2) {
            this.breakList.add(v1);
        }
        else {
            this.continueList.add(v1);
        }
    }
    
    protected void atReturnStmnt(final Stmnt a1) throws CompileError {
        this.atReturnStmnt2(a1.getLeft());
    }
    
    protected final void atReturnStmnt2(final ASTree v0) throws CompileError {
        int v = 0;
        if (v0 == null) {
            final int a1 = 177;
        }
        else {
            this.compileExpr(v0);
            if (this.arrayDim > 0) {
                v = 176;
            }
            else {
                final int v2 = this.exprType;
                if (v2 == 312) {
                    v = 175;
                }
                else if (v2 == 317) {
                    v = 174;
                }
                else if (v2 == 326) {
                    v = 173;
                }
                else if (isRefType(v2)) {
                    v = 176;
                }
                else {
                    v = 172;
                }
            }
        }
        for (ReturnHook v3 = this.returnHooks; v3 != null; v3 = v3.next) {
            if (v3.doit(this.bytecode, v)) {
                this.hasReturned = true;
                return;
            }
        }
        this.bytecode.addOpcode(v);
        this.hasReturned = true;
    }
    
    private void atThrowStmnt(final Stmnt a1) throws CompileError {
        final ASTree v1 = a1.getLeft();
        this.compileExpr(v1);
        if (this.exprType != 307 || this.arrayDim > 0) {
            throw new CompileError("bad throw statement");
        }
        this.bytecode.addOpcode(191);
        this.hasReturned = true;
    }
    
    protected void atTryStmnt(final Stmnt a1) throws CompileError {
        this.hasReturned = false;
    }
    
    private void atSyncStmnt(final Stmnt v2) throws CompileError {
        final int v3 = getListSize(this.breakList);
        final int v4 = getListSize(this.continueList);
        this.compileExpr(v2.head());
        if (this.exprType != 307 && this.arrayDim == 0) {
            throw new CompileError("bad type expr for synchronized block");
        }
        final Bytecode v5 = this.bytecode;
        final int v6 = v5.getMaxLocals();
        v5.incMaxLocals(1);
        v5.addOpcode(89);
        v5.addAstore(v6);
        v5.addOpcode(194);
        final ReturnHook v7 = new ReturnHook(this) {
            final /* synthetic */ int val$var;
            final /* synthetic */ CodeGen this$0;
            
            CodeGen$1(final CodeGen a2) {
                this.this$0 = a1;
                super(a2);
            }
            
            @Override
            protected boolean doit(final Bytecode a1, final int a2) {
                a1.addAload(v6);
                a1.addOpcode(195);
                return false;
            }
        };
        final int v8 = v5.currentPc();
        final Stmnt v9 = (Stmnt)v2.tail();
        if (v9 != null) {
            v9.accept(this);
        }
        final int v10 = v5.currentPc();
        int v11 = 0;
        if (!this.hasReturned) {
            v7.doit(v5, 0);
            v5.addOpcode(167);
            v11 = v5.currentPc();
            v5.addIndex(0);
        }
        if (v8 < v10) {
            final int a1 = v5.currentPc();
            v7.doit(v5, 0);
            v5.addOpcode(191);
            v5.addExceptionHandler(v8, v10, a1, 0);
        }
        if (!this.hasReturned) {
            v5.write16bit(v11, v5.currentPc() - v11 + 1);
        }
        v7.remove(this);
        if (getListSize(this.breakList) != v3 || getListSize(this.continueList) != v4) {
            throw new CompileError("sorry, cannot break/continue in synchronized block");
        }
    }
    
    private static int getListSize(final ArrayList a1) {
        return (a1 == null) ? 0 : a1.size();
    }
    
    private static boolean isPlusPlusExpr(final ASTree v1) {
        if (v1 instanceof Expr) {
            final int a1 = ((Expr)v1).getOperator();
            return a1 == 362 || a1 == 363;
        }
        return false;
    }
    
    @Override
    public void atDeclarator(final Declarator v2) throws CompileError {
        v2.setLocalVar(this.getMaxLocals());
        v2.setClassName(this.resolveClassName(v2.getClassName()));
        int v3 = 0;
        if (is2word(v2.getType(), v2.getArrayDim())) {
            final int a1 = 2;
        }
        else {
            v3 = 1;
        }
        this.incMaxLocals(v3);
        final ASTree v4 = v2.getInitializer();
        if (v4 != null) {
            this.doTypeCheck(v4);
            this.atVariableAssign(null, 61, null, v2, v4, false);
        }
    }
    
    @Override
    public abstract void atNewExpr(final NewExpr p0) throws CompileError;
    
    @Override
    public abstract void atArrayInit(final ArrayInit p0) throws CompileError;
    
    @Override
    public void atAssignExpr(final AssignExpr a1) throws CompileError {
        this.atAssignExpr(a1, true);
    }
    
    protected void atAssignExpr(final AssignExpr v1, final boolean v2) throws CompileError {
        final int v3 = v1.getOperator();
        final ASTree v4 = v1.oprand1();
        final ASTree v5 = v1.oprand2();
        if (v4 instanceof Variable) {
            this.atVariableAssign(v1, v3, (Variable)v4, ((Variable)v4).getDeclarator(), v5, v2);
        }
        else {
            if (v4 instanceof Expr) {
                final Expr a1 = (Expr)v4;
                if (a1.getOperator() == 65) {
                    this.atArrayAssign(v1, v3, (Expr)v4, v5, v2);
                    return;
                }
            }
            this.atFieldAssign(v1, v3, v4, v5, v2);
        }
    }
    
    protected static void badAssign(final Expr v1) throws CompileError {
        String v2 = null;
        if (v1 == null) {
            final String a1 = "incompatible type for assignment";
        }
        else {
            v2 = "incompatible type for " + v1.getName();
        }
        throw new CompileError(v2);
    }
    
    private void atVariableAssign(final Expr a1, final int a2, final Variable a3, final Declarator a4, final ASTree a5, final boolean a6) throws CompileError {
        final int v1 = a4.getType();
        final int v2 = a4.getArrayDim();
        final String v3 = a4.getClassName();
        final int v4 = this.getLocalVar(a4);
        if (a2 != 61) {
            this.atVariable(a3);
        }
        if (a1 == null && a5 instanceof ArrayInit) {
            this.atArrayVariableAssign((ArrayInit)a5, v1, v2, v3);
        }
        else {
            this.atAssignCore(a1, a2, a5, v1, v2, v3);
        }
        if (a6) {
            if (is2word(v1, v2)) {
                this.bytecode.addOpcode(92);
            }
            else {
                this.bytecode.addOpcode(89);
            }
        }
        if (v2 > 0) {
            this.bytecode.addAstore(v4);
        }
        else if (v1 == 312) {
            this.bytecode.addDstore(v4);
        }
        else if (v1 == 317) {
            this.bytecode.addFstore(v4);
        }
        else if (v1 == 326) {
            this.bytecode.addLstore(v4);
        }
        else if (isRefType(v1)) {
            this.bytecode.addAstore(v4);
        }
        else {
            this.bytecode.addIstore(v4);
        }
        this.exprType = v1;
        this.arrayDim = v2;
        this.className = v3;
    }
    
    protected abstract void atArrayVariableAssign(final ArrayInit p0, final int p1, final int p2, final String p3) throws CompileError;
    
    private void atArrayAssign(final Expr a1, final int a2, final Expr a3, final ASTree a4, final boolean a5) throws CompileError {
        this.arrayAccess(a3.oprand1(), a3.oprand2());
        if (a2 != 61) {
            this.bytecode.addOpcode(92);
            this.bytecode.addOpcode(getArrayReadOp(this.exprType, this.arrayDim));
        }
        final int v1 = this.exprType;
        final int v2 = this.arrayDim;
        final String v3 = this.className;
        this.atAssignCore(a1, a2, a4, v1, v2, v3);
        if (a5) {
            if (is2word(v1, v2)) {
                this.bytecode.addOpcode(94);
            }
            else {
                this.bytecode.addOpcode(91);
            }
        }
        this.bytecode.addOpcode(getArrayWriteOp(v1, v2));
        this.exprType = v1;
        this.arrayDim = v2;
        this.className = v3;
    }
    
    protected abstract void atFieldAssign(final Expr p0, final int p1, final ASTree p2, final ASTree p3, final boolean p4) throws CompileError;
    
    protected void atAssignCore(final Expr a4, final int a5, final ASTree a6, final int v1, final int v2, final String v3) throws CompileError {
        if (a5 == 354 && v2 == 0 && v1 == 307) {
            this.atStringPlusEq(a4, v1, v2, v3, a6);
        }
        else {
            a6.accept(this);
            if (this.invalidDim(this.exprType, this.arrayDim, this.className, v1, v2, v3, false) || (a5 != 61 && v2 > 0)) {
                badAssign(a4);
            }
            if (a5 != 61) {
                final int a7 = CodeGen.assignOps[a5 - 351];
                final int a8 = lookupBinOp(a7);
                if (a8 < 0) {
                    fatal();
                }
                this.atArithBinExpr(a4, a7, a8, v1);
            }
        }
        if (a5 != 61 || (v2 == 0 && !isRefType(v1))) {
            this.atNumCastExpr(this.exprType, v1);
        }
    }
    
    private void atStringPlusEq(final Expr a1, final int a2, final int a3, final String a4, final ASTree a5) throws CompileError {
        if (!"java/lang/String".equals(a4)) {
            badAssign(a1);
        }
        this.convToString(a2, a3);
        a5.accept(this);
        this.convToString(this.exprType, this.arrayDim);
        this.bytecode.addInvokevirtual("java.lang.String", "concat", "(Ljava/lang/String;)Ljava/lang/String;");
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/String";
    }
    
    private boolean invalidDim(final int a1, final int a2, final String a3, final int a4, final int a5, final String a6, final boolean a7) {
        return a2 != a5 && a1 != 412 && (a5 != 0 || a4 != 307 || !"java/lang/Object".equals(a6)) && (!a7 || a2 != 0 || a1 != 307 || !"java/lang/Object".equals(a3));
    }
    
    @Override
    public void atCondExpr(final CondExpr v-1) throws CompileError {
        if (this.booleanExpr(false, v-1.condExpr())) {
            v-1.elseExpr().accept(this);
        }
        else {
            final int a1 = this.bytecode.currentPc();
            this.bytecode.addIndex(0);
            v-1.thenExpr().accept(this);
            final int v1 = this.arrayDim;
            this.bytecode.addOpcode(167);
            final int v2 = this.bytecode.currentPc();
            this.bytecode.addIndex(0);
            this.bytecode.write16bit(a1, this.bytecode.currentPc() - a1 + 1);
            v-1.elseExpr().accept(this);
            if (v1 != this.arrayDim) {
                throw new CompileError("type mismatch in ?:");
            }
            this.bytecode.write16bit(v2, this.bytecode.currentPc() - v2 + 1);
        }
    }
    
    static int lookupBinOp(final int v1) {
        final int[] v2 = CodeGen.binOp;
        for (int v3 = v2.length, a1 = 0; a1 < v3; a1 += 5) {
            if (v2[a1] == v1) {
                return a1;
            }
        }
        return -1;
    }
    
    @Override
    public void atBinExpr(final BinExpr v-3) throws CompileError {
        final int operator = v-3.getOperator();
        final int lookupBinOp = lookupBinOp(operator);
        if (lookupBinOp >= 0) {
            v-3.oprand1().accept(this);
            final ASTree a1 = v-3.oprand2();
            if (a1 == null) {
                return;
            }
            final int v1 = this.exprType;
            final int v2 = this.arrayDim;
            final String v3 = this.className;
            a1.accept(this);
            if (v2 != this.arrayDim) {
                throw new CompileError("incompatible array types");
            }
            if (operator == 43 && v2 == 0 && (v1 == 307 || this.exprType == 307)) {
                this.atStringConcatExpr(v-3, v1, v2, v3);
            }
            else {
                this.atArithBinExpr(v-3, operator, lookupBinOp, v1);
            }
        }
        else {
            if (!this.booleanExpr(true, v-3)) {
                this.bytecode.addIndex(7);
                this.bytecode.addIconst(0);
                this.bytecode.addOpcode(167);
                this.bytecode.addIndex(4);
            }
            this.bytecode.addIconst(1);
        }
    }
    
    private void atArithBinExpr(final Expr a3, final int a4, final int v1, final int v2) throws CompileError {
        if (this.arrayDim != 0) {
            badTypes(a3);
        }
        final int v3 = this.exprType;
        if (a4 == 364 || a4 == 366 || a4 == 370) {
            if (v3 == 324 || v3 == 334 || v3 == 306 || v3 == 303) {
                this.exprType = v2;
            }
            else {
                badTypes(a3);
            }
        }
        else {
            this.convertOprandTypes(v2, v3, a3);
        }
        final int v4 = typePrecedence(this.exprType);
        if (v4 >= 0) {
            final int a5 = CodeGen.binOp[v1 + v4 + 1];
            if (a5 != 0) {
                if (v4 == 3 && this.exprType != 301) {
                    this.exprType = 324;
                }
                this.bytecode.addOpcode(a5);
                return;
            }
        }
        badTypes(a3);
    }
    
    private void atStringConcatExpr(final Expr a1, final int a2, final int a3, final String a4) throws CompileError {
        final int v1 = this.exprType;
        final int v2 = this.arrayDim;
        final boolean v3 = is2word(v1, v2);
        final boolean v4 = v1 == 307 && "java/lang/String".equals(this.className);
        if (v3) {
            this.convToString(v1, v2);
        }
        if (is2word(a2, a3)) {
            this.bytecode.addOpcode(91);
            this.bytecode.addOpcode(87);
        }
        else {
            this.bytecode.addOpcode(95);
        }
        this.convToString(a2, a3);
        this.bytecode.addOpcode(95);
        if (!v3 && !v4) {
            this.convToString(v1, v2);
        }
        this.bytecode.addInvokevirtual("java.lang.String", "concat", "(Ljava/lang/String;)Ljava/lang/String;");
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/String";
    }
    
    private void convToString(final int a1, final int a2) throws CompileError {
        final String v1 = "valueOf";
        if (isRefType(a1) || a2 > 0) {
            this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(Ljava/lang/Object;)Ljava/lang/String;");
        }
        else if (a1 == 312) {
            this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(D)Ljava/lang/String;");
        }
        else if (a1 == 317) {
            this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(F)Ljava/lang/String;");
        }
        else if (a1 == 326) {
            this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(J)Ljava/lang/String;");
        }
        else if (a1 == 301) {
            this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(Z)Ljava/lang/String;");
        }
        else if (a1 == 306) {
            this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(C)Ljava/lang/String;");
        }
        else {
            if (a1 == 344) {
                throw new CompileError("void type expression");
            }
            this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(I)Ljava/lang/String;");
        }
    }
    
    private boolean booleanExpr(final boolean v-4, final ASTree v-3) throws CompileError {
        final int compOperator = getCompOperator(v-3);
        if (compOperator == 358) {
            final BinExpr a1 = (BinExpr)v-3;
            final int a2 = this.compileOprands(a1);
            this.compareExpr(v-4, a1.getOperator(), a2, a1);
        }
        else {
            if (compOperator == 33) {
                return this.booleanExpr(!v-4, ((Expr)v-3).oprand1());
            }
            final boolean v-5;
            if ((v-5 = (compOperator == 369)) || compOperator == 368) {
                final BinExpr v0 = (BinExpr)v-3;
                if (this.booleanExpr(!v-5, v0.oprand1())) {
                    this.exprType = 301;
                    this.arrayDim = 0;
                    return true;
                }
                final int v2 = this.bytecode.currentPc();
                this.bytecode.addIndex(0);
                if (this.booleanExpr(v-5, v0.oprand2())) {
                    this.bytecode.addOpcode(167);
                }
                this.bytecode.write16bit(v2, this.bytecode.currentPc() - v2 + 3);
                if (v-4 != v-5) {
                    this.bytecode.addIndex(6);
                    this.bytecode.addOpcode(167);
                }
            }
            else {
                if (isAlwaysBranch(v-3, v-4)) {
                    this.exprType = 301;
                    this.arrayDim = 0;
                    return true;
                }
                v-3.accept(this);
                if (this.exprType != 301 || this.arrayDim != 0) {
                    throw new CompileError("boolean expr is required");
                }
                this.bytecode.addOpcode(v-4 ? 154 : 153);
            }
        }
        this.exprType = 301;
        this.arrayDim = 0;
        return false;
    }
    
    private static boolean isAlwaysBranch(final ASTree a2, final boolean v1) {
        if (a2 instanceof Keyword) {
            final int a3 = ((Keyword)a2).get();
            return v1 ? (a3 == 410) : (a3 == 411);
        }
        return false;
    }
    
    static int getCompOperator(final ASTree v-1) throws CompileError {
        if (!(v-1 instanceof Expr)) {
            return 32;
        }
        final Expr a1 = (Expr)v-1;
        final int v1 = a1.getOperator();
        if (v1 == 33) {
            return 33;
        }
        if (a1 instanceof BinExpr && v1 != 368 && v1 != 369 && v1 != 38 && v1 != 124) {
            return 358;
        }
        return v1;
    }
    
    private int compileOprands(final BinExpr a1) throws CompileError {
        a1.oprand1().accept(this);
        final int v1 = this.exprType;
        final int v2 = this.arrayDim;
        a1.oprand2().accept(this);
        if (v2 != this.arrayDim) {
            if (v1 != 412 && this.exprType != 412) {
                throw new CompileError("incompatible array types");
            }
            if (this.exprType == 412) {
                this.arrayDim = v2;
            }
        }
        if (v1 == 412) {
            return this.exprType;
        }
        return v1;
    }
    
    private void compareExpr(final boolean v2, final int v3, final int v4, final BinExpr v5) throws CompileError {
        if (this.arrayDim == 0) {
            this.convertOprandTypes(v4, this.exprType, v5);
        }
        final int v6 = typePrecedence(this.exprType);
        if (v6 == -1 || this.arrayDim > 0) {
            if (v3 == 358) {
                this.bytecode.addOpcode(v2 ? 165 : 166);
            }
            else if (v3 == 350) {
                this.bytecode.addOpcode(v2 ? 166 : 165);
            }
            else {
                badTypes(v5);
            }
        }
        else if (v6 == 3) {
            final int[] a2 = CodeGen.ifOp;
            for (int a3 = 0; a3 < a2.length; a3 += 3) {
                if (a2[a3] == v3) {
                    this.bytecode.addOpcode(a2[a3 + (v2 ? 1 : 2)]);
                    return;
                }
            }
            badTypes(v5);
        }
        else {
            if (v6 == 0) {
                if (v3 == 60 || v3 == 357) {
                    this.bytecode.addOpcode(152);
                }
                else {
                    this.bytecode.addOpcode(151);
                }
            }
            else if (v6 == 1) {
                if (v3 == 60 || v3 == 357) {
                    this.bytecode.addOpcode(150);
                }
                else {
                    this.bytecode.addOpcode(149);
                }
            }
            else if (v6 == 2) {
                this.bytecode.addOpcode(148);
            }
            else {
                fatal();
            }
            final int[] a4 = CodeGen.ifOp2;
            for (int a5 = 0; a5 < a4.length; a5 += 3) {
                if (a4[a5] == v3) {
                    this.bytecode.addOpcode(a4[a5 + (v2 ? 1 : 2)]);
                    return;
                }
            }
            badTypes(v5);
        }
    }
    
    protected static void badTypes(final Expr a1) throws CompileError {
        throw new CompileError("invalid types for " + a1.getName());
    }
    
    protected static boolean isRefType(final int a1) {
        return a1 == 307 || a1 == 412;
    }
    
    private static int typePrecedence(final int a1) {
        if (a1 == 312) {
            return 0;
        }
        if (a1 == 317) {
            return 1;
        }
        if (a1 == 326) {
            return 2;
        }
        if (isRefType(a1)) {
            return -1;
        }
        if (a1 == 344) {
            return -1;
        }
        return 3;
    }
    
    static boolean isP_INT(final int a1) {
        return typePrecedence(a1) == 3;
    }
    
    static boolean rightIsStrong(final int a1, final int a2) {
        final int v1 = typePrecedence(a1);
        final int v2 = typePrecedence(a2);
        return v1 >= 0 && v2 >= 0 && v1 > v2;
    }
    
    private void convertOprandTypes(final int v2, final int v3, final Expr v4) throws CompileError {
        final int v5 = typePrecedence(v2);
        final int v6 = typePrecedence(v3);
        if (v6 < 0 && v5 < 0) {
            return;
        }
        if (v6 < 0 || v5 < 0) {
            badTypes(v4);
        }
        boolean v7 = false;
        int v8 = 0;
        int v9 = 0;
        if (v5 <= v6) {
            final boolean a1 = false;
            this.exprType = v2;
            final int a2 = CodeGen.castOp[v6 * 4 + v5];
            final int a3 = v5;
        }
        else {
            v7 = true;
            v8 = CodeGen.castOp[v5 * 4 + v6];
            v9 = v6;
        }
        if (v7) {
            if (v9 == 0 || v9 == 2) {
                if (v5 == 0 || v5 == 2) {
                    this.bytecode.addOpcode(94);
                }
                else {
                    this.bytecode.addOpcode(93);
                }
                this.bytecode.addOpcode(88);
                this.bytecode.addOpcode(v8);
                this.bytecode.addOpcode(94);
                this.bytecode.addOpcode(88);
            }
            else if (v9 == 1) {
                if (v5 == 2) {
                    this.bytecode.addOpcode(91);
                    this.bytecode.addOpcode(87);
                }
                else {
                    this.bytecode.addOpcode(95);
                }
                this.bytecode.addOpcode(v8);
                this.bytecode.addOpcode(95);
            }
            else {
                fatal();
            }
        }
        else if (v8 != 0) {
            this.bytecode.addOpcode(v8);
        }
    }
    
    @Override
    public void atCastExpr(final CastExpr a1) throws CompileError {
        final String v1 = this.resolveClassName(a1.getClassName());
        final String v2 = this.checkCastExpr(a1, v1);
        final int v3 = this.exprType;
        this.exprType = a1.getType();
        this.arrayDim = a1.getArrayDim();
        this.className = v1;
        if (v2 == null) {
            this.atNumCastExpr(v3, this.exprType);
        }
        else {
            this.bytecode.addCheckcast(v2);
        }
    }
    
    @Override
    public void atInstanceOfExpr(final InstanceOfExpr a1) throws CompileError {
        final String v1 = this.resolveClassName(a1.getClassName());
        final String v2 = this.checkCastExpr(a1, v1);
        this.bytecode.addInstanceof(v2);
        this.exprType = 301;
        this.arrayDim = 0;
    }
    
    private String checkCastExpr(final CastExpr a1, final String a2) throws CompileError {
        final String v1 = "invalid cast";
        final ASTree v2 = a1.getOprand();
        final int v3 = a1.getArrayDim();
        final int v4 = a1.getType();
        v2.accept(this);
        final int v5 = this.exprType;
        final int v6 = this.arrayDim;
        if (this.invalidDim(v5, this.arrayDim, this.className, v4, v3, a2, true) || v5 == 344 || v4 == 344) {
            throw new CompileError("invalid cast");
        }
        if (v4 == 307) {
            if (!isRefType(v5) && v6 == 0) {
                throw new CompileError("invalid cast");
            }
            return toJvmArrayName(a2, v3);
        }
        else {
            if (v3 > 0) {
                return toJvmTypeName(v4, v3);
            }
            return null;
        }
    }
    
    void atNumCastExpr(final int v-2, final int v-1) throws CompileError {
        if (v-2 == v-1) {
            return;
        }
        final int v2 = typePrecedence(v-2);
        final int v3 = typePrecedence(v-1);
        int v4 = 0;
        if (0 <= v2 && v2 < 3) {
            final int a1 = CodeGen.castOp[v2 * 4 + v3];
        }
        else {
            v4 = 0;
        }
        int v5 = 0;
        if (v-1 == 312) {
            final int a2 = 135;
        }
        else if (v-1 == 317) {
            v5 = 134;
        }
        else if (v-1 == 326) {
            v5 = 133;
        }
        else if (v-1 == 334) {
            v5 = 147;
        }
        else if (v-1 == 306) {
            v5 = 146;
        }
        else if (v-1 == 303) {
            v5 = 145;
        }
        else {
            v5 = 0;
        }
        if (v4 != 0) {
            this.bytecode.addOpcode(v4);
        }
        if ((v4 == 0 || v4 == 136 || v4 == 139 || v4 == 142) && v5 != 0) {
            this.bytecode.addOpcode(v5);
        }
    }
    
    @Override
    public void atExpr(final Expr v-2) throws CompileError {
        final int operator = v-2.getOperator();
        final ASTree v0 = v-2.oprand1();
        if (operator == 46) {
            final String a1 = ((Symbol)v-2.oprand2()).get();
            if (a1.equals("class")) {
                this.atClassObject(v-2);
            }
            else {
                this.atFieldRead(v-2);
            }
        }
        else if (operator == 35) {
            this.atFieldRead(v-2);
        }
        else if (operator == 65) {
            this.atArrayRead(v0, v-2.oprand2());
        }
        else if (operator == 362 || operator == 363) {
            this.atPlusPlus(operator, v0, v-2, true);
        }
        else if (operator == 33) {
            if (!this.booleanExpr(false, v-2)) {
                this.bytecode.addIndex(7);
                this.bytecode.addIconst(1);
                this.bytecode.addOpcode(167);
                this.bytecode.addIndex(4);
            }
            this.bytecode.addIconst(0);
        }
        else if (operator == 67) {
            fatal();
        }
        else {
            v-2.oprand1().accept(this);
            final int v2 = typePrecedence(this.exprType);
            if (this.arrayDim > 0) {
                badType(v-2);
            }
            if (operator == 45) {
                if (v2 == 0) {
                    this.bytecode.addOpcode(119);
                }
                else if (v2 == 1) {
                    this.bytecode.addOpcode(118);
                }
                else if (v2 == 2) {
                    this.bytecode.addOpcode(117);
                }
                else if (v2 == 3) {
                    this.bytecode.addOpcode(116);
                    this.exprType = 324;
                }
                else {
                    badType(v-2);
                }
            }
            else if (operator == 126) {
                if (v2 == 3) {
                    this.bytecode.addIconst(-1);
                    this.bytecode.addOpcode(130);
                    this.exprType = 324;
                }
                else if (v2 == 2) {
                    this.bytecode.addLconst(-1L);
                    this.bytecode.addOpcode(131);
                }
                else {
                    badType(v-2);
                }
            }
            else if (operator == 43) {
                if (v2 == -1) {
                    badType(v-2);
                }
            }
            else {
                fatal();
            }
        }
    }
    
    protected static void badType(final Expr a1) throws CompileError {
        throw new CompileError("invalid type for " + a1.getName());
    }
    
    @Override
    public abstract void atCallExpr(final CallExpr p0) throws CompileError;
    
    protected abstract void atFieldRead(final ASTree p0) throws CompileError;
    
    public void atClassObject(final Expr v-3) throws CompileError {
        final ASTree oprand1 = v-3.oprand1();
        if (!(oprand1 instanceof Symbol)) {
            throw new CompileError("fatal error: badly parsed .class expr");
        }
        String a2 = ((Symbol)oprand1).get();
        if (a2.startsWith("[")) {
            int v0 = a2.indexOf("[L");
            if (v0 >= 0) {
                final String v2 = a2.substring(v0 + 2, a2.length() - 1);
                String v3 = this.resolveClassName(v2);
                if (!v2.equals(v3)) {
                    v3 = MemberResolver.jvmToJavaName(v3);
                    final StringBuffer a1 = new StringBuffer();
                    while (v0-- >= 0) {
                        a1.append('[');
                    }
                    a1.append('L').append(v3).append(';');
                    a2 = a1.toString();
                }
            }
        }
        else {
            a2 = this.resolveClassName(MemberResolver.javaToJvmName(a2));
            a2 = MemberResolver.jvmToJavaName(a2);
        }
        this.atClassObject2(a2);
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/Class";
    }
    
    protected void atClassObject2(final String a1) throws CompileError {
        final int v1 = this.bytecode.currentPc();
        this.bytecode.addLdc(a1);
        this.bytecode.addInvokestatic("java.lang.Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;");
        final int v2 = this.bytecode.currentPc();
        this.bytecode.addOpcode(167);
        final int v3 = this.bytecode.currentPc();
        this.bytecode.addIndex(0);
        this.bytecode.addExceptionHandler(v1, v2, this.bytecode.currentPc(), "java.lang.ClassNotFoundException");
        this.bytecode.growStack(1);
        this.bytecode.addInvokestatic("javassist.runtime.DotClass", "fail", "(Ljava/lang/ClassNotFoundException;)Ljava/lang/NoClassDefFoundError;");
        this.bytecode.addOpcode(191);
        this.bytecode.write16bit(v3, this.bytecode.currentPc() - v3 + 1);
    }
    
    public void atArrayRead(final ASTree a1, final ASTree a2) throws CompileError {
        this.arrayAccess(a1, a2);
        this.bytecode.addOpcode(getArrayReadOp(this.exprType, this.arrayDim));
    }
    
    protected void arrayAccess(final ASTree a1, final ASTree a2) throws CompileError {
        a1.accept(this);
        final int v1 = this.exprType;
        final int v2 = this.arrayDim;
        if (v2 == 0) {
            throw new CompileError("bad array access");
        }
        final String v3 = this.className;
        a2.accept(this);
        if (typePrecedence(this.exprType) != 3 || this.arrayDim > 0) {
            throw new CompileError("bad array index");
        }
        this.exprType = v1;
        this.arrayDim = v2 - 1;
        this.className = v3;
    }
    
    protected static int getArrayReadOp(final int a1, final int a2) {
        if (a2 > 0) {
            return 50;
        }
        switch (a1) {
            case 312: {
                return 49;
            }
            case 317: {
                return 48;
            }
            case 326: {
                return 47;
            }
            case 324: {
                return 46;
            }
            case 334: {
                return 53;
            }
            case 306: {
                return 52;
            }
            case 301:
            case 303: {
                return 51;
            }
            default: {
                return 50;
            }
        }
    }
    
    protected static int getArrayWriteOp(final int a1, final int a2) {
        if (a2 > 0) {
            return 83;
        }
        switch (a1) {
            case 312: {
                return 82;
            }
            case 317: {
                return 81;
            }
            case 326: {
                return 80;
            }
            case 324: {
                return 79;
            }
            case 334: {
                return 86;
            }
            case 306: {
                return 85;
            }
            case 301:
            case 303: {
                return 84;
            }
            default: {
                return 83;
            }
        }
    }
    
    private void atPlusPlus(final int v-4, ASTree v-3, final Expr v-2, final boolean v-1) throws CompileError {
        final boolean v0 = v-3 == null;
        if (v0) {
            v-3 = v-2.oprand2();
        }
        if (v-3 instanceof Variable) {
            final Declarator a2 = ((Variable)v-3).getDeclarator();
            final int type = a2.getType();
            this.exprType = type;
            final int a3 = type;
            this.arrayDim = a2.getArrayDim();
            final int a4 = this.getLocalVar(a2);
            if (this.arrayDim > 0) {
                badType(v-2);
            }
            if (a3 == 312) {
                this.bytecode.addDload(a4);
                if (v-1 && v0) {
                    this.bytecode.addOpcode(92);
                }
                this.bytecode.addDconst(1.0);
                this.bytecode.addOpcode((v-4 == 362) ? 99 : 103);
                if (v-1 && !v0) {
                    this.bytecode.addOpcode(92);
                }
                this.bytecode.addDstore(a4);
            }
            else if (a3 == 326) {
                this.bytecode.addLload(a4);
                if (v-1 && v0) {
                    this.bytecode.addOpcode(92);
                }
                this.bytecode.addLconst(1L);
                this.bytecode.addOpcode((v-4 == 362) ? 97 : 101);
                if (v-1 && !v0) {
                    this.bytecode.addOpcode(92);
                }
                this.bytecode.addLstore(a4);
            }
            else if (a3 == 317) {
                this.bytecode.addFload(a4);
                if (v-1 && v0) {
                    this.bytecode.addOpcode(89);
                }
                this.bytecode.addFconst(1.0f);
                this.bytecode.addOpcode((v-4 == 362) ? 98 : 102);
                if (v-1 && !v0) {
                    this.bytecode.addOpcode(89);
                }
                this.bytecode.addFstore(a4);
            }
            else if (a3 == 303 || a3 == 306 || a3 == 334 || a3 == 324) {
                if (v-1 && v0) {
                    this.bytecode.addIload(a4);
                }
                final int a5 = (v-4 == 362) ? 1 : -1;
                if (a4 > 255) {
                    this.bytecode.addOpcode(196);
                    this.bytecode.addOpcode(132);
                    this.bytecode.addIndex(a4);
                    this.bytecode.addIndex(a5);
                }
                else {
                    this.bytecode.addOpcode(132);
                    this.bytecode.add(a4);
                    this.bytecode.add(a5);
                }
                if (v-1 && !v0) {
                    this.bytecode.addIload(a4);
                }
            }
            else {
                badType(v-2);
            }
        }
        else {
            if (v-3 instanceof Expr) {
                final Expr v2 = (Expr)v-3;
                if (v2.getOperator() == 65) {
                    this.atArrayPlusPlus(v-4, v0, v2, v-1);
                    return;
                }
            }
            this.atFieldPlusPlus(v-4, v0, v-3, v-2, v-1);
        }
    }
    
    public void atArrayPlusPlus(final int a1, final boolean a2, final Expr a3, final boolean a4) throws CompileError {
        this.arrayAccess(a3.oprand1(), a3.oprand2());
        final int v1 = this.exprType;
        final int v2 = this.arrayDim;
        if (v2 > 0) {
            badType(a3);
        }
        this.bytecode.addOpcode(92);
        this.bytecode.addOpcode(getArrayReadOp(v1, this.arrayDim));
        final int v3 = is2word(v1, v2) ? 94 : 91;
        this.atPlusPlusCore(v3, a4, a1, a2, a3);
        this.bytecode.addOpcode(getArrayWriteOp(v1, v2));
    }
    
    protected void atPlusPlusCore(final int a1, final boolean a2, final int a3, final boolean a4, final Expr a5) throws CompileError {
        final int v1 = this.exprType;
        if (a2 && a4) {
            this.bytecode.addOpcode(a1);
        }
        if (v1 == 324 || v1 == 303 || v1 == 306 || v1 == 334) {
            this.bytecode.addIconst(1);
            this.bytecode.addOpcode((a3 == 362) ? 96 : 100);
            this.exprType = 324;
        }
        else if (v1 == 326) {
            this.bytecode.addLconst(1L);
            this.bytecode.addOpcode((a3 == 362) ? 97 : 101);
        }
        else if (v1 == 317) {
            this.bytecode.addFconst(1.0f);
            this.bytecode.addOpcode((a3 == 362) ? 98 : 102);
        }
        else if (v1 == 312) {
            this.bytecode.addDconst(1.0);
            this.bytecode.addOpcode((a3 == 362) ? 99 : 103);
        }
        else {
            badType(a5);
        }
        if (a2 && !a4) {
            this.bytecode.addOpcode(a1);
        }
    }
    
    protected abstract void atFieldPlusPlus(final int p0, final boolean p1, final ASTree p2, final Expr p3, final boolean p4) throws CompileError;
    
    @Override
    public abstract void atMember(final Member p0) throws CompileError;
    
    @Override
    public void atVariable(final Variable a1) throws CompileError {
        final Declarator v1 = a1.getDeclarator();
        this.exprType = v1.getType();
        this.arrayDim = v1.getArrayDim();
        this.className = v1.getClassName();
        final int v2 = this.getLocalVar(v1);
        if (this.arrayDim > 0) {
            this.bytecode.addAload(v2);
        }
        else {
            switch (this.exprType) {
                case 307: {
                    this.bytecode.addAload(v2);
                    break;
                }
                case 326: {
                    this.bytecode.addLload(v2);
                    break;
                }
                case 317: {
                    this.bytecode.addFload(v2);
                    break;
                }
                case 312: {
                    this.bytecode.addDload(v2);
                    break;
                }
                default: {
                    this.bytecode.addIload(v2);
                    break;
                }
            }
        }
    }
    
    @Override
    public void atKeyword(final Keyword a1) throws CompileError {
        this.arrayDim = 0;
        final int v1 = a1.get();
        switch (v1) {
            case 410: {
                this.bytecode.addIconst(1);
                this.exprType = 301;
                break;
            }
            case 411: {
                this.bytecode.addIconst(0);
                this.exprType = 301;
                break;
            }
            case 412: {
                this.bytecode.addOpcode(1);
                this.exprType = 412;
                break;
            }
            case 336:
            case 339: {
                if (this.inStaticMethod) {
                    throw new CompileError("not-available: " + ((v1 == 339) ? "this" : "super"));
                }
                this.bytecode.addAload(0);
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
        this.bytecode.addLdc(a1.get());
    }
    
    @Override
    public void atIntConst(final IntConst a1) throws CompileError {
        this.arrayDim = 0;
        final long v1 = a1.get();
        final int v2 = a1.getType();
        if (v2 == 402 || v2 == 401) {
            this.exprType = ((v2 == 402) ? 324 : 306);
            this.bytecode.addIconst((int)v1);
        }
        else {
            this.exprType = 326;
            this.bytecode.addLconst(v1);
        }
    }
    
    @Override
    public void atDoubleConst(final DoubleConst a1) throws CompileError {
        this.arrayDim = 0;
        if (a1.getType() == 405) {
            this.exprType = 312;
            this.bytecode.addDconst(a1.get());
        }
        else {
            this.exprType = 317;
            this.bytecode.addFconst((float)a1.get());
        }
    }
    
    static {
        binOp = new int[] { 43, 99, 98, 97, 96, 45, 103, 102, 101, 100, 42, 107, 106, 105, 104, 47, 111, 110, 109, 108, 37, 115, 114, 113, 112, 124, 0, 0, 129, 128, 94, 0, 0, 131, 130, 38, 0, 0, 127, 126, 364, 0, 0, 121, 120, 366, 0, 0, 123, 122, 370, 0, 0, 125, 124 };
        ifOp = new int[] { 358, 159, 160, 350, 160, 159, 357, 164, 163, 359, 162, 161, 60, 161, 162, 62, 163, 164 };
        ifOp2 = new int[] { 358, 153, 154, 350, 154, 153, 357, 158, 157, 359, 156, 155, 60, 155, 156, 62, 157, 158 };
        castOp = new int[] { 0, 144, 143, 142, 141, 0, 140, 139, 138, 137, 0, 136, 135, 134, 133, 0 };
    }
    
    protected abstract static class ReturnHook
    {
        ReturnHook next;
        
        protected abstract boolean doit(final Bytecode p0, final int p1);
        
        protected ReturnHook(final CodeGen a1) {
            super();
            this.next = a1.returnHooks;
            a1.returnHooks = this;
        }
        
        protected void remove(final CodeGen a1) {
            a1.returnHooks = this.next;
        }
    }
}
