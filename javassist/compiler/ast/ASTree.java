package javassist.compiler.ast;

import java.io.*;
import javassist.compiler.*;

public abstract class ASTree implements Serializable
{
    public ASTree() {
        super();
    }
    
    public ASTree getLeft() {
        return null;
    }
    
    public ASTree getRight() {
        return null;
    }
    
    public void setLeft(final ASTree a1) {
    }
    
    public void setRight(final ASTree a1) {
    }
    
    public abstract void accept(final Visitor p0) throws CompileError;
    
    @Override
    public String toString() {
        final StringBuffer v1 = new StringBuffer();
        v1.append('<');
        v1.append(this.getTag());
        v1.append('>');
        return v1.toString();
    }
    
    protected String getTag() {
        final String v1 = this.getClass().getName();
        return v1.substring(v1.lastIndexOf(46) + 1);
    }
}
