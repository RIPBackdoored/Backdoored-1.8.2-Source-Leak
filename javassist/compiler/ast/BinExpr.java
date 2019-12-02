package javassist.compiler.ast;

import javassist.compiler.*;

public class BinExpr extends Expr
{
    private BinExpr(final int a1, final ASTree a2, final ASTList a3) {
        super(a1, a2, a3);
    }
    
    public static BinExpr makeBin(final int a1, final ASTree a2, final ASTree a3) {
        return new BinExpr(a1, a2, new ASTList(a3));
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atBinExpr(this);
    }
}
