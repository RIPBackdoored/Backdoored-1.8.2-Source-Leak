package javassist.compiler.ast;

import javassist.compiler.*;

public class Pair extends ASTree
{
    protected ASTree left;
    protected ASTree right;
    
    public Pair(final ASTree a1, final ASTree a2) {
        super();
        this.left = a1;
        this.right = a2;
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atPair(this);
    }
    
    @Override
    public String toString() {
        final StringBuffer v1 = new StringBuffer();
        v1.append("(<Pair> ");
        v1.append((this.left == null) ? "<null>" : this.left.toString());
        v1.append(" . ");
        v1.append((this.right == null) ? "<null>" : this.right.toString());
        v1.append(')');
        return v1.toString();
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
        this.right = a1;
    }
}
