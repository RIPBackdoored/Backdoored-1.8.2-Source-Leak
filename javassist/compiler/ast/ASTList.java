package javassist.compiler.ast;

import javassist.compiler.*;

public class ASTList extends ASTree
{
    private ASTree left;
    private ASTList right;
    
    public ASTList(final ASTree a1, final ASTList a2) {
        super();
        this.left = a1;
        this.right = a2;
    }
    
    public ASTList(final ASTree a1) {
        super();
        this.left = a1;
        this.right = null;
    }
    
    public static ASTList make(final ASTree a1, final ASTree a2, final ASTree a3) {
        return new ASTList(a1, new ASTList(a2, new ASTList(a3)));
    }
    
    @Override
    public ASTree getLeft() {
        return this.left;
    }
    
    @Override
    public ASTree getRight() {
        return this.right;
    }
    
    @Override
    public void setLeft(final ASTree a1) {
        this.left = a1;
    }
    
    @Override
    public void setRight(final ASTree a1) {
        this.right = (ASTList)a1;
    }
    
    public ASTree head() {
        return this.left;
    }
    
    public void setHead(final ASTree a1) {
        this.left = a1;
    }
    
    public ASTList tail() {
        return this.right;
    }
    
    public void setTail(final ASTList a1) {
        this.right = a1;
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atASTList(this);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("(<");
        sb.append(this.getTag());
        sb.append('>');
        for (ASTList v0 = this; v0 != null; v0 = v0.right) {
            sb.append(' ');
            final ASTree v2 = v0.left;
            sb.append((v2 == null) ? "<null>" : v2.toString());
        }
        sb.append(')');
        return sb.toString();
    }
    
    public int length() {
        return length(this);
    }
    
    public static int length(ASTList a1) {
        if (a1 == null) {
            return 0;
        }
        int v1;
        for (v1 = 0; a1 != null; a1 = a1.right, ++v1) {}
        return v1;
    }
    
    public ASTList sublist(int a1) {
        ASTList v1 = this;
        while (a1-- > 0) {
            v1 = v1.right;
        }
        return v1;
    }
    
    public boolean subst(final ASTree v1, final ASTree v2) {
        for (ASTList a1 = this; a1 != null; a1 = a1.right) {
            if (a1.left == v2) {
                a1.left = v1;
                return true;
            }
        }
        return false;
    }
    
    public static ASTList append(final ASTList a1, final ASTree a2) {
        return concat(a1, new ASTList(a2));
    }
    
    public static ASTList concat(final ASTList a2, final ASTList v1) {
        if (a2 == null) {
            return v1;
        }
        ASTList a3;
        for (a3 = a2; a3.right != null; a3 = a3.right) {}
        a3.right = v1;
        return a2;
    }
}
