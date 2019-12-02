package javassist.compiler.ast;

import javassist.compiler.*;

public class CondExpr extends ASTList
{
    public CondExpr(final ASTree a1, final ASTree a2, final ASTree a3) {
        super(a1, new ASTList(a2, new ASTList(a3)));
    }
    
    public ASTree condExpr() {
        return this.head();
    }
    
    public void setCond(final ASTree a1) {
        this.setHead(a1);
    }
    
    public ASTree thenExpr() {
        return this.tail().head();
    }
    
    public void setThen(final ASTree a1) {
        this.tail().setHead(a1);
    }
    
    public ASTree elseExpr() {
        return this.tail().tail().head();
    }
    
    public void setElse(final ASTree a1) {
        this.tail().tail().setHead(a1);
    }
    
    public String getTag() {
        return "?:";
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atCondExpr(this);
    }
}
