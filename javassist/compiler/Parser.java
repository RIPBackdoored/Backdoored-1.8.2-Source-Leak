package javassist.compiler;

import javassist.compiler.ast.*;

public final class Parser implements TokenId
{
    private Lex lex;
    private static final int[] binaryOpPrecedence;
    
    public Parser(final Lex a1) {
        super();
        this.lex = a1;
    }
    
    public boolean hasMore() {
        return this.lex.lookAhead() >= 0;
    }
    
    public ASTList parseMember(final SymbolTable a1) throws CompileError {
        final ASTList v1 = this.parseMember1(a1);
        if (v1 instanceof MethodDecl) {
            return this.parseMethod2(a1, (MethodDecl)v1);
        }
        return v1;
    }
    
    public ASTList parseMember1(final SymbolTable v-3) throws CompileError {
        final ASTList memberMods = this.parseMemberMods();
        boolean v0 = false;
        Declarator formalType = null;
        if (this.lex.lookAhead() == 400 && this.lex.lookAhead(1) == 40) {
            final Declarator a1 = new Declarator(344, 0);
            v0 = true;
        }
        else {
            formalType = this.parseFormalType(v-3);
        }
        if (this.lex.get() != 400) {
            throw new SyntaxError(this.lex);
        }
        String v2;
        if (v0) {
            v2 = "<init>";
        }
        else {
            v2 = this.lex.getString();
        }
        formalType.setVariable(new Symbol(v2));
        if (v0 || this.lex.lookAhead() == 40) {
            return this.parseMethod1(v-3, v0, memberMods, formalType);
        }
        return this.parseField(v-3, memberMods, formalType);
    }
    
    private FieldDecl parseField(final SymbolTable a1, final ASTList a2, final Declarator a3) throws CompileError {
        ASTree v1 = null;
        if (this.lex.lookAhead() == 61) {
            this.lex.get();
            v1 = this.parseExpression(a1);
        }
        final int v2 = this.lex.get();
        if (v2 == 59) {
            return new FieldDecl(a2, new ASTList(a3, new ASTList(v1)));
        }
        if (v2 == 44) {
            throw new CompileError("only one field can be declared in one declaration", this.lex);
        }
        throw new SyntaxError(this.lex);
    }
    
    private MethodDecl parseMethod1(final SymbolTable a3, final boolean a4, final ASTList v1, final Declarator v2) throws CompileError {
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        ASTList v3 = null;
        if (this.lex.lookAhead() != 41) {
            while (true) {
                v3 = ASTList.append(v3, this.parseFormalParam(a3));
                final int a5 = this.lex.lookAhead();
                if (a5 == 44) {
                    this.lex.get();
                }
                else {
                    if (a5 == 41) {
                        break;
                    }
                    continue;
                }
            }
        }
        this.lex.get();
        v2.addArrayDim(this.parseArrayDimension());
        if (a4 && v2.getArrayDim() > 0) {
            throw new SyntaxError(this.lex);
        }
        ASTList v4 = null;
        if (this.lex.lookAhead() == 341) {
            this.lex.get();
            while (true) {
                v4 = ASTList.append(v4, this.parseClassType(a3));
                if (this.lex.lookAhead() != 44) {
                    break;
                }
                this.lex.get();
            }
        }
        return new MethodDecl(v1, new ASTList(v2, ASTList.make(v3, v4, null)));
    }
    
    public MethodDecl parseMethod2(final SymbolTable a1, final MethodDecl a2) throws CompileError {
        Stmnt v1 = null;
        if (this.lex.lookAhead() == 59) {
            this.lex.get();
        }
        else {
            v1 = this.parseBlock(a1);
            if (v1 == null) {
                v1 = new Stmnt(66);
            }
        }
        a2.sublist(4).setHead(v1);
        return a2;
    }
    
    private ASTList parseMemberMods() {
        ASTList v2 = null;
        while (true) {
            final int v3 = this.lex.lookAhead();
            if (v3 != 300 && v3 != 315 && v3 != 332 && v3 != 331 && v3 != 330 && v3 != 338 && v3 != 335 && v3 != 345 && v3 != 342 && v3 != 347) {
                break;
            }
            v2 = new ASTList(new Keyword(this.lex.get()), v2);
        }
        return v2;
    }
    
    private Declarator parseFormalType(final SymbolTable v-1) throws CompileError {
        final int v0 = this.lex.lookAhead();
        if (isBuiltinType(v0) || v0 == 344) {
            this.lex.get();
            final int a1 = this.parseArrayDimension();
            return new Declarator(v0, a1);
        }
        final ASTList v2 = this.parseClassType(v-1);
        final int v3 = this.parseArrayDimension();
        return new Declarator(v2, v3);
    }
    
    private static boolean isBuiltinType(final int a1) {
        return a1 == 301 || a1 == 303 || a1 == 306 || a1 == 334 || a1 == 324 || a1 == 326 || a1 == 317 || a1 == 312;
    }
    
    private Declarator parseFormalParam(final SymbolTable a1) throws CompileError {
        final Declarator v1 = this.parseFormalType(a1);
        if (this.lex.get() != 400) {
            throw new SyntaxError(this.lex);
        }
        final String v2 = this.lex.getString();
        v1.setVariable(new Symbol(v2));
        v1.addArrayDim(this.parseArrayDimension());
        a1.append(v2, v1);
        return v1;
    }
    
    public Stmnt parseStatement(final SymbolTable v2) throws CompileError {
        final int v3 = this.lex.lookAhead();
        if (v3 == 123) {
            return this.parseBlock(v2);
        }
        if (v3 == 59) {
            this.lex.get();
            return new Stmnt(66);
        }
        if (v3 == 400 && this.lex.lookAhead(1) == 58) {
            this.lex.get();
            final String a1 = this.lex.getString();
            this.lex.get();
            return Stmnt.make(76, new Symbol(a1), this.parseStatement(v2));
        }
        if (v3 == 320) {
            return this.parseIf(v2);
        }
        if (v3 == 346) {
            return this.parseWhile(v2);
        }
        if (v3 == 311) {
            return this.parseDo(v2);
        }
        if (v3 == 318) {
            return this.parseFor(v2);
        }
        if (v3 == 343) {
            return this.parseTry(v2);
        }
        if (v3 == 337) {
            return this.parseSwitch(v2);
        }
        if (v3 == 338) {
            return this.parseSynchronized(v2);
        }
        if (v3 == 333) {
            return this.parseReturn(v2);
        }
        if (v3 == 340) {
            return this.parseThrow(v2);
        }
        if (v3 == 302) {
            return this.parseBreak(v2);
        }
        if (v3 == 309) {
            return this.parseContinue(v2);
        }
        return this.parseDeclarationOrExpression(v2, false);
    }
    
    private Stmnt parseBlock(final SymbolTable v2) throws CompileError {
        if (this.lex.get() != 123) {
            throw new SyntaxError(this.lex);
        }
        Stmnt v3 = null;
        final SymbolTable v4 = new SymbolTable(v2);
        while (this.lex.lookAhead() != 125) {
            final Stmnt a1 = this.parseStatement(v4);
            if (a1 != null) {
                v3 = (Stmnt)ASTList.concat(v3, new Stmnt(66, a1));
            }
        }
        this.lex.get();
        if (v3 == null) {
            return new Stmnt(66);
        }
        return v3;
    }
    
    private Stmnt parseIf(final SymbolTable v2) throws CompileError {
        final int v3 = this.lex.get();
        final ASTree v4 = this.parseParExpression(v2);
        final Stmnt v5 = this.parseStatement(v2);
        Stmnt v6 = null;
        if (this.lex.lookAhead() == 313) {
            this.lex.get();
            final Stmnt a1 = this.parseStatement(v2);
        }
        else {
            v6 = null;
        }
        return new Stmnt(v3, v4, new ASTList(v5, new ASTList(v6)));
    }
    
    private Stmnt parseWhile(final SymbolTable a1) throws CompileError {
        final int v1 = this.lex.get();
        final ASTree v2 = this.parseParExpression(a1);
        final Stmnt v3 = this.parseStatement(a1);
        return new Stmnt(v1, v2, v3);
    }
    
    private Stmnt parseDo(final SymbolTable a1) throws CompileError {
        final int v1 = this.lex.get();
        final Stmnt v2 = this.parseStatement(a1);
        if (this.lex.get() != 346 || this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        final ASTree v3 = this.parseExpression(a1);
        if (this.lex.get() != 41 || this.lex.get() != 59) {
            throw new SyntaxError(this.lex);
        }
        return new Stmnt(v1, v3, v2);
    }
    
    private Stmnt parseFor(final SymbolTable v-2) throws CompileError {
        final int v2 = this.lex.get();
        final SymbolTable v3 = new SymbolTable(v-2);
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        Stmnt declarationOrExpression = null;
        if (this.lex.lookAhead() == 59) {
            this.lex.get();
            final Stmnt a1 = null;
        }
        else {
            declarationOrExpression = this.parseDeclarationOrExpression(v3, true);
        }
        ASTree v4;
        if (this.lex.lookAhead() == 59) {
            v4 = null;
        }
        else {
            v4 = this.parseExpression(v3);
        }
        if (this.lex.get() != 59) {
            throw new CompileError("; is missing", this.lex);
        }
        Stmnt v5;
        if (this.lex.lookAhead() == 41) {
            v5 = null;
        }
        else {
            v5 = this.parseExprList(v3);
        }
        if (this.lex.get() != 41) {
            throw new CompileError(") is missing", this.lex);
        }
        final Stmnt v6 = this.parseStatement(v3);
        return new Stmnt(v2, declarationOrExpression, new ASTList(v4, new ASTList(v5, v6)));
    }
    
    private Stmnt parseSwitch(final SymbolTable a1) throws CompileError {
        final int v1 = this.lex.get();
        final ASTree v2 = this.parseParExpression(a1);
        final Stmnt v3 = this.parseSwitchBlock(a1);
        return new Stmnt(v1, v2, v3);
    }
    
    private Stmnt parseSwitchBlock(final SymbolTable v-4) throws CompileError {
        if (this.lex.get() != 123) {
            throw new SyntaxError(this.lex);
        }
        final SymbolTable symbolTable = new SymbolTable(v-4);
        Stmnt stmntOrCase = this.parseStmntOrCase(symbolTable);
        if (stmntOrCase == null) {
            throw new CompileError("empty switch block", this.lex);
        }
        final int operator = stmntOrCase.getOperator();
        if (operator != 304 && operator != 310) {
            throw new CompileError("no case or default in a switch block", this.lex);
        }
        Stmnt v0 = new Stmnt(66, stmntOrCase);
        while (this.lex.lookAhead() != 125) {
            final Stmnt v2 = this.parseStmntOrCase(symbolTable);
            if (v2 != null) {
                final int a1 = v2.getOperator();
                if (a1 == 304 || a1 == 310) {
                    v0 = (Stmnt)ASTList.concat(v0, new Stmnt(66, v2));
                    stmntOrCase = v2;
                }
                else {
                    stmntOrCase = (Stmnt)ASTList.concat(stmntOrCase, new Stmnt(66, v2));
                }
            }
        }
        this.lex.get();
        return v0;
    }
    
    private Stmnt parseStmntOrCase(final SymbolTable v2) throws CompileError {
        final int v3 = this.lex.lookAhead();
        if (v3 != 304 && v3 != 310) {
            return this.parseStatement(v2);
        }
        this.lex.get();
        Stmnt v4 = null;
        if (v3 == 304) {
            final Stmnt a1 = new Stmnt(v3, this.parseExpression(v2));
        }
        else {
            v4 = new Stmnt(310);
        }
        if (this.lex.get() != 58) {
            throw new CompileError(": is missing", this.lex);
        }
        return v4;
    }
    
    private Stmnt parseSynchronized(final SymbolTable a1) throws CompileError {
        final int v1 = this.lex.get();
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        final ASTree v2 = this.parseExpression(a1);
        if (this.lex.get() != 41) {
            throw new SyntaxError(this.lex);
        }
        final Stmnt v3 = this.parseBlock(a1);
        return new Stmnt(v1, v2, v3);
    }
    
    private Stmnt parseTry(final SymbolTable v-3) throws CompileError {
        this.lex.get();
        final Stmnt block = this.parseBlock(v-3);
        ASTList append = null;
        while (this.lex.lookAhead() == 305) {
            this.lex.get();
            if (this.lex.get() != 40) {
                throw new SyntaxError(this.lex);
            }
            final SymbolTable a1 = new SymbolTable(v-3);
            final Declarator v1 = this.parseFormalParam(a1);
            if (v1.getArrayDim() > 0 || v1.getType() != 307) {
                throw new SyntaxError(this.lex);
            }
            if (this.lex.get() != 41) {
                throw new SyntaxError(this.lex);
            }
            final Stmnt v2 = this.parseBlock(a1);
            append = ASTList.append(append, new Pair(v1, v2));
        }
        Stmnt v3 = null;
        if (this.lex.lookAhead() == 316) {
            this.lex.get();
            v3 = this.parseBlock(v-3);
        }
        return Stmnt.make(343, block, append, v3);
    }
    
    private Stmnt parseReturn(final SymbolTable a1) throws CompileError {
        final int v1 = this.lex.get();
        final Stmnt v2 = new Stmnt(v1);
        if (this.lex.lookAhead() != 59) {
            v2.setLeft(this.parseExpression(a1));
        }
        if (this.lex.get() != 59) {
            throw new CompileError("; is missing", this.lex);
        }
        return v2;
    }
    
    private Stmnt parseThrow(final SymbolTable a1) throws CompileError {
        final int v1 = this.lex.get();
        final ASTree v2 = this.parseExpression(a1);
        if (this.lex.get() != 59) {
            throw new CompileError("; is missing", this.lex);
        }
        return new Stmnt(v1, v2);
    }
    
    private Stmnt parseBreak(final SymbolTable a1) throws CompileError {
        return this.parseContinue(a1);
    }
    
    private Stmnt parseContinue(final SymbolTable a1) throws CompileError {
        final int v1 = this.lex.get();
        final Stmnt v2 = new Stmnt(v1);
        int v3 = this.lex.get();
        if (v3 == 400) {
            v2.setLeft(new Symbol(this.lex.getString()));
            v3 = this.lex.get();
        }
        if (v3 != 59) {
            throw new CompileError("; is missing", this.lex);
        }
        return v2;
    }
    
    private Stmnt parseDeclarationOrExpression(final SymbolTable v-4, final boolean v-3) throws CompileError {
        int i;
        for (i = this.lex.lookAhead(); i == 315; i = this.lex.lookAhead()) {
            this.lex.get();
        }
        if (isBuiltinType(i)) {
            i = this.lex.get();
            final int a1 = this.parseArrayDimension();
            return this.parseDeclarators(v-4, new Declarator(i, a1));
        }
        if (i == 400) {
            final int nextIsClassType = this.nextIsClassType(0);
            if (nextIsClassType >= 0 && this.lex.lookAhead(nextIsClassType) == 400) {
                final ASTList a2 = this.parseClassType(v-4);
                final int v1 = this.parseArrayDimension();
                return this.parseDeclarators(v-4, new Declarator(a2, v1));
            }
        }
        Stmnt exprList;
        if (v-3) {
            exprList = this.parseExprList(v-4);
        }
        else {
            exprList = new Stmnt(69, this.parseExpression(v-4));
        }
        if (this.lex.get() != 59) {
            throw new CompileError("; is missing", this.lex);
        }
        return exprList;
    }
    
    private Stmnt parseExprList(final SymbolTable v2) throws CompileError {
        Stmnt v3 = null;
        while (true) {
            final Stmnt a1 = new Stmnt(69, this.parseExpression(v2));
            v3 = (Stmnt)ASTList.concat(v3, new Stmnt(66, a1));
            if (this.lex.lookAhead() != 44) {
                break;
            }
            this.lex.get();
        }
        return v3;
    }
    
    private Stmnt parseDeclarators(final SymbolTable v1, final Declarator v2) throws CompileError {
        Stmnt v3 = null;
        while (true) {
            v3 = (Stmnt)ASTList.concat(v3, new Stmnt(68, this.parseDeclarator(v1, v2)));
            final int a1 = this.lex.get();
            if (a1 == 59) {
                return v3;
            }
            if (a1 != 44) {
                throw new CompileError("; is missing", this.lex);
            }
        }
    }
    
    private Declarator parseDeclarator(final SymbolTable a1, final Declarator a2) throws CompileError {
        if (this.lex.get() != 400 || a2.getType() == 344) {
            throw new SyntaxError(this.lex);
        }
        final String v1 = this.lex.getString();
        final Symbol v2 = new Symbol(v1);
        final int v3 = this.parseArrayDimension();
        ASTree v4 = null;
        if (this.lex.lookAhead() == 61) {
            this.lex.get();
            v4 = this.parseInitializer(a1);
        }
        final Declarator v5 = a2.make(v2, v3, v4);
        a1.append(v1, v5);
        return v5;
    }
    
    private ASTree parseInitializer(final SymbolTable a1) throws CompileError {
        if (this.lex.lookAhead() == 123) {
            return this.parseArrayInitializer(a1);
        }
        return this.parseExpression(a1);
    }
    
    private ArrayInit parseArrayInitializer(final SymbolTable a1) throws CompileError {
        this.lex.get();
        ASTree v1 = this.parseExpression(a1);
        final ArrayInit v2 = new ArrayInit(v1);
        while (this.lex.lookAhead() == 44) {
            this.lex.get();
            v1 = this.parseExpression(a1);
            ASTList.append(v2, v1);
        }
        if (this.lex.get() != 125) {
            throw new SyntaxError(this.lex);
        }
        return v2;
    }
    
    private ASTree parseParExpression(final SymbolTable a1) throws CompileError {
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        final ASTree v1 = this.parseExpression(a1);
        if (this.lex.get() != 41) {
            throw new SyntaxError(this.lex);
        }
        return v1;
    }
    
    public ASTree parseExpression(final SymbolTable a1) throws CompileError {
        final ASTree v1 = this.parseConditionalExpr(a1);
        if (!isAssignOp(this.lex.lookAhead())) {
            return v1;
        }
        final int v2 = this.lex.get();
        final ASTree v3 = this.parseExpression(a1);
        return AssignExpr.makeAssign(v2, v1, v3);
    }
    
    private static boolean isAssignOp(final int a1) {
        return a1 == 61 || a1 == 351 || a1 == 352 || a1 == 353 || a1 == 354 || a1 == 355 || a1 == 356 || a1 == 360 || a1 == 361 || a1 == 365 || a1 == 367 || a1 == 371;
    }
    
    private ASTree parseConditionalExpr(final SymbolTable v-2) throws CompileError {
        final ASTree binaryExpr = this.parseBinaryExpr(v-2);
        if (this.lex.lookAhead() != 63) {
            return binaryExpr;
        }
        this.lex.get();
        final ASTree a1 = this.parseExpression(v-2);
        if (this.lex.get() != 58) {
            throw new CompileError(": is missing", this.lex);
        }
        final ASTree v1 = this.parseExpression(v-2);
        return new CondExpr(binaryExpr, a1, v1);
    }
    
    private ASTree parseBinaryExpr(final SymbolTable v-2) throws CompileError {
        ASTree v2 = this.parseUnaryExpr(v-2);
        while (true) {
            final int a1 = this.lex.lookAhead();
            final int v1 = this.getOpPrecedence(a1);
            if (v1 == 0) {
                break;
            }
            v2 = this.binaryExpr2(v-2, v2, v1);
        }
        return v2;
    }
    
    private ASTree parseInstanceOf(final SymbolTable v-3, final ASTree v-2) throws CompileError {
        final int lookAhead = this.lex.lookAhead();
        if (isBuiltinType(lookAhead)) {
            this.lex.get();
            final int a1 = this.parseArrayDimension();
            return new InstanceOfExpr(lookAhead, a1, v-2);
        }
        final ASTList a2 = this.parseClassType(v-3);
        final int v1 = this.parseArrayDimension();
        return new InstanceOfExpr(a2, v1, v-2);
    }
    
    private ASTree binaryExpr2(final SymbolTable v1, final ASTree v2, final int v3) throws CompileError {
        final int v4 = this.lex.get();
        if (v4 == 323) {
            return this.parseInstanceOf(v1, v2);
        }
        ASTree v5 = this.parseUnaryExpr(v1);
        while (true) {
            final int a1 = this.lex.lookAhead();
            final int a2 = this.getOpPrecedence(a1);
            if (a2 == 0 || v3 <= a2) {
                break;
            }
            v5 = this.binaryExpr2(v1, v5, a2);
        }
        return BinExpr.makeBin(v4, v2, v5);
    }
    
    private int getOpPrecedence(final int a1) {
        if (33 <= a1 && a1 <= 63) {
            return Parser.binaryOpPrecedence[a1 - 33];
        }
        if (a1 == 94) {
            return 7;
        }
        if (a1 == 124) {
            return 8;
        }
        if (a1 == 369) {
            return 9;
        }
        if (a1 == 368) {
            return 10;
        }
        if (a1 == 358 || a1 == 350) {
            return 5;
        }
        if (a1 == 357 || a1 == 359 || a1 == 323) {
            return 4;
        }
        if (a1 == 364 || a1 == 366 || a1 == 370) {
            return 3;
        }
        return 0;
    }
    
    private ASTree parseUnaryExpr(final SymbolTable v0) throws CompileError {
        switch (this.lex.lookAhead()) {
            case 33:
            case 43:
            case 45:
            case 126:
            case 362:
            case 363: {
                final int v = this.lex.get();
                if (v == 45) {
                    final int a1 = this.lex.lookAhead();
                    switch (a1) {
                        case 401:
                        case 402:
                        case 403: {
                            this.lex.get();
                            return new IntConst(-this.lex.getLong(), a1);
                        }
                        case 404:
                        case 405: {
                            this.lex.get();
                            return new DoubleConst(-this.lex.getDouble(), a1);
                        }
                    }
                }
                return Expr.make(v, this.parseUnaryExpr(v0));
            }
            case 40: {
                return this.parseCast(v0);
            }
            default: {
                return this.parsePostfix(v0);
            }
        }
    }
    
    private ASTree parseCast(final SymbolTable v-1) throws CompileError {
        final int v0 = this.lex.lookAhead(1);
        if (isBuiltinType(v0) && this.nextIsBuiltinCast()) {
            this.lex.get();
            this.lex.get();
            final int a1 = this.parseArrayDimension();
            if (this.lex.get() != 41) {
                throw new CompileError(") is missing", this.lex);
            }
            return new CastExpr(v0, a1, this.parseUnaryExpr(v-1));
        }
        else {
            if (v0 != 400 || !this.nextIsClassCast()) {
                return this.parsePostfix(v-1);
            }
            this.lex.get();
            final ASTList v2 = this.parseClassType(v-1);
            final int v3 = this.parseArrayDimension();
            if (this.lex.get() != 41) {
                throw new CompileError(") is missing", this.lex);
            }
            return new CastExpr(v2, v3, this.parseUnaryExpr(v-1));
        }
    }
    
    private boolean nextIsBuiltinCast() {
        int v2 = 2;
        int v3;
        while ((v3 = this.lex.lookAhead(v2++)) == 91) {
            if (this.lex.lookAhead(v2++) != 93) {
                return false;
            }
        }
        return this.lex.lookAhead(v2 - 1) == 41;
    }
    
    private boolean nextIsClassCast() {
        final int v1 = this.nextIsClassType(1);
        if (v1 < 0) {
            return false;
        }
        int v2 = this.lex.lookAhead(v1);
        if (v2 != 41) {
            return false;
        }
        v2 = this.lex.lookAhead(v1 + 1);
        return v2 == 40 || v2 == 412 || v2 == 406 || v2 == 400 || v2 == 339 || v2 == 336 || v2 == 328 || v2 == 410 || v2 == 411 || v2 == 403 || v2 == 402 || v2 == 401 || v2 == 405 || v2 == 404;
    }
    
    private int nextIsClassType(int a1) {
        while (this.lex.lookAhead(++a1) == 46) {
            if (this.lex.lookAhead(++a1) != 400) {
                return -1;
            }
        }
        int v1;
        while ((v1 = this.lex.lookAhead(a1++)) == 91) {
            if (this.lex.lookAhead(a1++) != 93) {
                return -1;
            }
        }
        return a1 - 1;
    }
    
    private int parseArrayDimension() throws CompileError {
        int v1 = 0;
        while (this.lex.lookAhead() == 91) {
            ++v1;
            this.lex.get();
            if (this.lex.get() != 93) {
                throw new CompileError("] is missing", this.lex);
            }
        }
        return v1;
    }
    
    private ASTList parseClassType(final SymbolTable a1) throws CompileError {
        ASTList v1 = null;
        while (this.lex.get() == 400) {
            v1 = ASTList.append(v1, new Symbol(this.lex.getString()));
            if (this.lex.lookAhead() != 46) {
                return v1;
            }
            this.lex.get();
        }
        throw new SyntaxError(this.lex);
    }
    
    private ASTree parsePostfix(final SymbolTable v-2) throws CompileError {
        final int lookAhead = this.lex.lookAhead();
        switch (lookAhead) {
            case 401:
            case 402:
            case 403: {
                this.lex.get();
                return new IntConst(this.lex.getLong(), lookAhead);
            }
            case 404:
            case 405: {
                this.lex.get();
                return new DoubleConst(this.lex.getDouble(), lookAhead);
            }
            default: {
                ASTree v2 = this.parsePrimaryExpr(v-2);
                while (true) {
                    switch (this.lex.lookAhead()) {
                        case 40: {
                            v2 = this.parseMethodCall(v-2, v2);
                            continue;
                        }
                        case 91: {
                            if (this.lex.lookAhead(1) == 93) {
                                final int a1 = this.parseArrayDimension();
                                if (this.lex.get() != 46 || this.lex.get() != 307) {
                                    throw new SyntaxError(this.lex);
                                }
                                v2 = this.parseDotClass(v2, a1);
                                continue;
                            }
                            else {
                                final ASTree v3 = this.parseArrayIndex(v-2);
                                if (v3 == null) {
                                    throw new SyntaxError(this.lex);
                                }
                                v2 = Expr.make(65, v2, v3);
                                continue;
                            }
                            break;
                        }
                        case 362:
                        case 363: {
                            final int v4 = this.lex.get();
                            v2 = Expr.make(v4, null, v2);
                            continue;
                        }
                        case 46: {
                            this.lex.get();
                            final int v4 = this.lex.get();
                            if (v4 == 307) {
                                v2 = this.parseDotClass(v2, 0);
                                continue;
                            }
                            if (v4 == 336) {
                                v2 = Expr.make(46, new Symbol(this.toClassName(v2)), new Keyword(v4));
                                continue;
                            }
                            if (v4 == 400) {
                                final String v5 = this.lex.getString();
                                v2 = Expr.make(46, v2, new Member(v5));
                                continue;
                            }
                            throw new CompileError("missing member name", this.lex);
                        }
                        case 35: {
                            this.lex.get();
                            final int v4 = this.lex.get();
                            if (v4 != 400) {
                                throw new CompileError("missing static member name", this.lex);
                            }
                            final String v5 = this.lex.getString();
                            v2 = Expr.make(35, new Symbol(this.toClassName(v2)), new Member(v5));
                            continue;
                        }
                        default: {
                            return v2;
                        }
                    }
                }
                break;
            }
        }
    }
    
    private ASTree parseDotClass(final ASTree v1, int v2) throws CompileError {
        String v3 = this.toClassName(v1);
        if (v2 > 0) {
            final StringBuffer a1 = new StringBuffer();
            while (v2-- > 0) {
                a1.append('[');
            }
            a1.append('L').append(v3.replace('.', '/')).append(';');
            v3 = a1.toString();
        }
        return Expr.make(46, new Symbol(v3), new Member("class"));
    }
    
    private ASTree parseDotClass(final int v-1, final int v0) throws CompileError {
        if (v0 > 0) {
            final String a1 = CodeGen.toJvmTypeName(v-1, v0);
            return Expr.make(46, new Symbol(a1), new Member("class"));
        }
        String v = null;
        switch (v-1) {
            case 301: {
                final String a2 = "java.lang.Boolean";
                break;
            }
            case 303: {
                v = "java.lang.Byte";
                break;
            }
            case 306: {
                v = "java.lang.Character";
                break;
            }
            case 334: {
                v = "java.lang.Short";
                break;
            }
            case 324: {
                v = "java.lang.Integer";
                break;
            }
            case 326: {
                v = "java.lang.Long";
                break;
            }
            case 317: {
                v = "java.lang.Float";
                break;
            }
            case 312: {
                v = "java.lang.Double";
                break;
            }
            case 344: {
                v = "java.lang.Void";
                break;
            }
            default: {
                throw new CompileError("invalid builtin type: " + v-1);
            }
        }
        return Expr.make(35, new Symbol(v), new Member("TYPE"));
    }
    
    private ASTree parseMethodCall(final SymbolTable v2, final ASTree v3) throws CompileError {
        if (v3 instanceof Keyword) {
            final int a1 = ((Keyword)v3).get();
            if (a1 != 339 && a1 != 336) {
                throw new SyntaxError(this.lex);
            }
        }
        else if (!(v3 instanceof Symbol)) {
            if (v3 instanceof Expr) {
                final int a2 = ((Expr)v3).getOperator();
                if (a2 != 46 && a2 != 35) {
                    throw new SyntaxError(this.lex);
                }
            }
        }
        return CallExpr.makeCall(v3, this.parseArgumentList(v2));
    }
    
    private String toClassName(final ASTree a1) throws CompileError {
        final StringBuffer v1 = new StringBuffer();
        this.toClassName(a1, v1);
        return v1.toString();
    }
    
    private void toClassName(final ASTree v1, final StringBuffer v2) throws CompileError {
        if (v1 instanceof Symbol) {
            v2.append(((Symbol)v1).get());
            return;
        }
        if (v1 instanceof Expr) {
            final Expr a1 = (Expr)v1;
            if (a1.getOperator() == 46) {
                this.toClassName(a1.oprand1(), v2);
                v2.append('.');
                this.toClassName(a1.oprand2(), v2);
                return;
            }
        }
        throw new CompileError("bad static member access", this.lex);
    }
    
    private ASTree parsePrimaryExpr(final SymbolTable v-2) throws CompileError {
        final int value;
        switch (value = this.lex.get()) {
            case 336:
            case 339:
            case 410:
            case 411:
            case 412: {
                return new Keyword(value);
            }
            case 400: {
                final String a1 = this.lex.getString();
                final Declarator v1 = v-2.lookup(a1);
                if (v1 == null) {
                    return new Member(a1);
                }
                return new Variable(a1, v1);
            }
            case 406: {
                return new StringL(this.lex.getString());
            }
            case 328: {
                return this.parseNew(v-2);
            }
            case 40: {
                final ASTree v2 = this.parseExpression(v-2);
                if (this.lex.get() == 41) {
                    return v2;
                }
                throw new CompileError(") is missing", this.lex);
            }
            default: {
                if (isBuiltinType(value) || value == 344) {
                    final int v3 = this.parseArrayDimension();
                    if (this.lex.get() == 46 && this.lex.get() == 307) {
                        return this.parseDotClass(value, v3);
                    }
                }
                throw new SyntaxError(this.lex);
            }
        }
    }
    
    private NewExpr parseNew(final SymbolTable v-3) throws CompileError {
        ArrayInit arrayInit = null;
        int n = this.lex.lookAhead();
        if (isBuiltinType(n)) {
            this.lex.get();
            final ASTList a1 = this.parseArraySize(v-3);
            if (this.lex.lookAhead() == 123) {
                arrayInit = this.parseArrayInitializer(v-3);
            }
            return new NewExpr(n, a1, arrayInit);
        }
        if (n == 400) {
            final ASTList v0 = this.parseClassType(v-3);
            n = this.lex.lookAhead();
            if (n == 40) {
                final ASTList v2 = this.parseArgumentList(v-3);
                return new NewExpr(v0, v2);
            }
            if (n == 91) {
                final ASTList v2 = this.parseArraySize(v-3);
                if (this.lex.lookAhead() == 123) {
                    arrayInit = this.parseArrayInitializer(v-3);
                }
                return NewExpr.makeObjectArray(v0, v2, arrayInit);
            }
        }
        throw new SyntaxError(this.lex);
    }
    
    private ASTList parseArraySize(final SymbolTable a1) throws CompileError {
        ASTList v1 = null;
        while (this.lex.lookAhead() == 91) {
            v1 = ASTList.append(v1, this.parseArrayIndex(a1));
        }
        return v1;
    }
    
    private ASTree parseArrayIndex(final SymbolTable v2) throws CompileError {
        this.lex.get();
        if (this.lex.lookAhead() == 93) {
            this.lex.get();
            return null;
        }
        final ASTree a1 = this.parseExpression(v2);
        if (this.lex.get() != 93) {
            throw new CompileError("] is missing", this.lex);
        }
        return a1;
    }
    
    private ASTList parseArgumentList(final SymbolTable a1) throws CompileError {
        if (this.lex.get() != 40) {
            throw new CompileError("( is missing", this.lex);
        }
        ASTList v1 = null;
        if (this.lex.lookAhead() != 41) {
            while (true) {
                v1 = ASTList.append(v1, this.parseExpression(a1));
                if (this.lex.lookAhead() != 44) {
                    break;
                }
                this.lex.get();
            }
        }
        if (this.lex.get() != 41) {
            throw new CompileError(") is missing", this.lex);
        }
        return v1;
    }
    
    static {
        binaryOpPrecedence = new int[] { 0, 0, 0, 0, 1, 6, 0, 0, 0, 1, 2, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 4, 0 };
    }
}
