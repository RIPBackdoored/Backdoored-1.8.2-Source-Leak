package javassist.compiler.ast;

import javassist.compiler.*;

public class Variable extends Symbol
{
    protected Declarator declarator;
    
    public Variable(final String a1, final Declarator a2) {
        super(a1);
        this.declarator = a2;
    }
    
    public Declarator getDeclarator() {
        return this.declarator;
    }
    
    @Override
    public String toString() {
        return this.identifier + ":" + this.declarator.getType();
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atVariable(this);
    }
}
