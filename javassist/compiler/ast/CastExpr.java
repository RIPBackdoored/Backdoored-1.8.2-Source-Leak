package javassist.compiler.ast;

import javassist.compiler.*;

public class CastExpr extends ASTList implements TokenId
{
    protected int castType;
    protected int arrayDim;
    
    public CastExpr(final ASTList a1, final int a2, final ASTree a3) {
        super(a1, new ASTList(a3));
        this.castType = 307;
        this.arrayDim = a2;
    }
    
    public CastExpr(final int a1, final int a2, final ASTree a3) {
        super(null, new ASTList(a3));
        this.castType = a1;
        this.arrayDim = a2;
    }
    
    public int getType() {
        return this.castType;
    }
    
    public int getArrayDim() {
        return this.arrayDim;
    }
    
    public ASTList getClassName() {
        return (ASTList)this.getLeft();
    }
    
    public ASTree getOprand() {
        return this.getRight().getLeft();
    }
    
    public void setOprand(final ASTree a1) {
        this.getRight().setLeft(a1);
    }
    
    public String getTag() {
        return "cast:" + this.castType + ":" + this.arrayDim;
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atCastExpr(this);
    }
}
