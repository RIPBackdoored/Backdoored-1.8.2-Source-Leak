package javassist.compiler.ast;

import javassist.compiler.*;

public class Keyword extends ASTree
{
    protected int tokenId;
    
    public Keyword(final int a1) {
        super();
        this.tokenId = a1;
    }
    
    public int get() {
        return this.tokenId;
    }
    
    @Override
    public String toString() {
        return "id:" + this.tokenId;
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atKeyword(this);
    }
}
