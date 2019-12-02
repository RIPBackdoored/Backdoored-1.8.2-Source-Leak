package javassist.compiler.ast;

import javassist.compiler.*;

public class NewExpr extends ASTList implements TokenId
{
    protected boolean newArray;
    protected int arrayType;
    
    public NewExpr(final ASTList a1, final ASTList a2) {
        super(a1, new ASTList(a2));
        this.newArray = false;
        this.arrayType = 307;
    }
    
    public NewExpr(final int a1, final ASTList a2, final ArrayInit a3) {
        super(null, new ASTList(a2));
        this.newArray = true;
        this.arrayType = a1;
        if (a3 != null) {
            ASTList.append(this, a3);
        }
    }
    
    public static NewExpr makeObjectArray(final ASTList a1, final ASTList a2, final ArrayInit a3) {
        final NewExpr v1 = new NewExpr(a1, a2);
        v1.newArray = true;
        if (a3 != null) {
            ASTList.append(v1, a3);
        }
        return v1;
    }
    
    public boolean isArray() {
        return this.newArray;
    }
    
    public int getArrayType() {
        return this.arrayType;
    }
    
    public ASTList getClassName() {
        return (ASTList)this.getLeft();
    }
    
    public ASTList getArguments() {
        return (ASTList)this.getRight().getLeft();
    }
    
    public ASTList getArraySize() {
        return this.getArguments();
    }
    
    public ArrayInit getInitializer() {
        final ASTree v1 = this.getRight().getRight();
        if (v1 == null) {
            return null;
        }
        return (ArrayInit)v1.getLeft();
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atNewExpr(this);
    }
    
    @Override
    protected String getTag() {
        return this.newArray ? "new[]" : "new";
    }
}
