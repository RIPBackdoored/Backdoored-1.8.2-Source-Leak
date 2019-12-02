package javassist.compiler.ast;

import javassist.compiler.*;

public class Expr extends ASTList implements TokenId
{
    protected int operatorId;
    
    Expr(final int a1, final ASTree a2, final ASTList a3) {
        super(a2, a3);
        this.operatorId = a1;
    }
    
    Expr(final int a1, final ASTree a2) {
        super(a2);
        this.operatorId = a1;
    }
    
    public static Expr make(final int a1, final ASTree a2, final ASTree a3) {
        return new Expr(a1, a2, new ASTList(a3));
    }
    
    public static Expr make(final int a1, final ASTree a2) {
        return new Expr(a1, a2);
    }
    
    public int getOperator() {
        return this.operatorId;
    }
    
    public void setOperator(final int a1) {
        this.operatorId = a1;
    }
    
    public ASTree oprand1() {
        return this.getLeft();
    }
    
    public void setOprand1(final ASTree a1) {
        this.setLeft(a1);
    }
    
    public ASTree oprand2() {
        return this.getRight().getLeft();
    }
    
    public void setOprand2(final ASTree a1) {
        this.getRight().setLeft(a1);
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atExpr(this);
    }
    
    public String getName() {
        final int v1 = this.operatorId;
        if (v1 < 128) {
            return String.valueOf((char)v1);
        }
        if (350 <= v1 && v1 <= 371) {
            return Expr.opNames[v1 - 350];
        }
        if (v1 == 323) {
            return "instanceof";
        }
        return String.valueOf(v1);
    }
    
    @Override
    protected String getTag() {
        return "op:" + this.getName();
    }
}
