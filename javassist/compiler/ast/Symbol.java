package javassist.compiler.ast;

import javassist.compiler.*;

public class Symbol extends ASTree
{
    protected String identifier;
    
    public Symbol(final String a1) {
        super();
        this.identifier = a1;
    }
    
    public String get() {
        return this.identifier;
    }
    
    @Override
    public String toString() {
        return this.identifier;
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atSymbol(this);
    }
}
