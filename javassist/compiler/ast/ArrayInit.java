package javassist.compiler.ast;

import javassist.compiler.*;

public class ArrayInit extends ASTList
{
    public ArrayInit(final ASTree a1) {
        super(a1);
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atArrayInit(this);
    }
    
    public String getTag() {
        return "array";
    }
}
